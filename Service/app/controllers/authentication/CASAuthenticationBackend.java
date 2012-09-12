package controllers.authentication;

import java.net.URL;

import org.jasig.cas.client.util.XmlUtils;
import org.jasig.cas.client.validation.Assertion;
import org.jasig.cas.client.validation.Cas10TicketValidator;
import org.jasig.cas.client.validation.Cas20ProxyTicketValidator;
import org.jasig.cas.client.validation.Cas20ServiceTicketValidator;
import org.jasig.cas.client.validation.Saml11TicketValidator;
import org.jasig.cas.client.validation.TicketValidationException;
import org.jasig.cas.client.validation.TicketValidator;

import models.AuthenticationBinding;
import models.CASAuthenticationBinding;
import models.SimpleAuthenticationBinding;
import models.User;
import notifiers.MailNotifier;
import play.Logger;
import play.Play;
import play.mvc.Http;
import play.mvc.Http.Header;
import play.mvc.Scope.Params;
import api.requests.AuthenticateUserRequest;
import api.requests.GenerateAuthChallengeRequest;
import api.requests.GenerateCASAuthChallengeRequest;
import api.requests.CASAuthenticateUserRequest;
import api.requests.GenerateSimpleAuthChallengeRequest;
import api.requests.SimpleAuthenticateUserRequest;
import api.responses.GenerateAuthChallengeResponse;
import api.responses.GenerateCASAuthChallengeResponse;
import api.responses.GenerateSimpleAuthChallengeResponse;

public class CASAuthenticationBackend extends AuthenticationBackend {

	public static Class<? extends GenerateAuthChallengeRequest> getChallengeRequestClass() throws Exception {
		return GenerateCASAuthChallengeRequest.class;
	}
	
	public static Class<? extends GenerateAuthChallengeResponse> getChallengeResponseClass() throws Exception {
		return GenerateCASAuthChallengeResponse.class;
	}
	
	public static Class<? extends AuthenticateUserRequest> getAuthenticateUserRequestClass() throws Exception {
		return CASAuthenticateUserRequest.class;
	}

	/**
	 * Method that authenticates the user to access the system.
	 * @return true if user authenticated and false otherwise
	 * @throws Exception 
	 */
	public static User authenticate(AuthenticateUserRequest req) throws Exception {
		if(!(req instanceof CASAuthenticateUserRequest)) {
			throw new Exception("This authenticate user request is not supported by this backend.");
		} else {
			CASAuthenticateUserRequest request = (CASAuthenticateUserRequest) req;
			Logger.debug("I got a CAS Authentication request - with ticket = %s", request.ticket);

			String serverUrl = (String) Play.configuration.get("openarms.authentication.cas.serverUrl");
			if(request.service == null) {
				throw new RuntimeException("The CAS challenge request needs a service URL.");
			}

			TicketValidator validator = new DTUCas20ProxyTicketValidator(serverUrl);
			Assertion assertion = validator.validate(request.ticket, request.service.toExternalForm());
			
			String email = (String) assertion.getPrincipal().getAttributes().get("email");
			String name = (String) assertion.getPrincipal().getAttributes().get("name");
			String identifier = (String) assertion.getPrincipal().getAttributes().get("identifier");
			
			// Logger.debug("email = %s, name = %s, identifier = %s", email, name, identifier);
			
			CASAuthenticationBinding binding = (CASAuthenticationBinding) CASAuthenticationBinding.find("externalIdentifier", identifier).first();
			
			User user;
			if(binding == null || binding.user == null) {
				user = (User) User.find("email", email).first();
				// Check if there's a user for this.
				if(user == null) {
					// User is unknown to the system.
					user = new User();
					user.email = email;
					user.name = name;
					user.save();
					if(binding == null) {
						CASAuthenticationBinding authenticationBinding = new CASAuthenticationBinding();
						authenticationBinding.service = serverUrl;
						authenticationBinding.externalIdentifier = identifier;
						authenticationBinding.user = user;
						authenticationBinding.save();
						user.authenticationBinding = authenticationBinding;
					} else {
						user.authenticationBinding = binding;
					}
				} else {
					throw new RuntimeException("Sorry, a user with this email already exists on the system.");
				}
			} else {
				user = binding.user;
				// Update if changed.
				user.email = email;
				user.name = name;
			}
			
			user.save();
			
			
			Logger.debug("User = %s", user.toString());
			
    		user.secret = AuthenticationBackend.generateSecret();
	    	Logger.debug("Generated a new authentication secret: %s", user.secret);
	    	user.save();
	    	return user;
		}
	}
	
	public static GenerateAuthChallengeResponse generateChallenge(GenerateAuthChallengeRequest req) throws Exception {
		if(req instanceof GenerateCASAuthChallengeRequest) {
			GenerateCASAuthChallengeRequest request = (GenerateCASAuthChallengeRequest) req;
			// openarms.authentication.cas.serverUrl
			Logger.debug("Generating a cas authentication challenge.");
			GenerateCASAuthChallengeResponse response = new GenerateCASAuthChallengeResponse();

			String serverUrl = (String) Play.configuration.get("openarms.authentication.cas.serverUrl");
			if(request.service == null) {
				throw new RuntimeException("The CAS challenge request needs a service URL.");
			}
			String redirectUrl = org.jasig.cas.client.util.CommonUtils.constructRedirectUrl(serverUrl+"/login", "service", request.service.toExternalForm(), false, false);
			
			response.redirect = new URL(redirectUrl);
			return response;
		} else {
			throw new Exception("Can only generate authentication challenges based on GenerateCASAuthChallengeRequests, got "+(req==null?"null":req.getClass().getCanonicalName()));
		}
	}
}
