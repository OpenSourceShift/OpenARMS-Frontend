package controllers.authentication;

import java.net.URL;

import org.jasig.cas.client.validation.Assertion;
import org.jasig.cas.client.validation.Cas10TicketValidator;
import org.jasig.cas.client.validation.Cas20ServiceTicketValidator;

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

			Cas20ServiceTicketValidator validator = new Cas20ServiceTicketValidator(serverUrl);
			Assertion assertion = validator.validate(request.ticket, request.service.toExternalForm());
			
			Logger.debug("Assertion.getAttributes().get('noreduperson') = %s", assertion.getPrincipal().getAttributes().get("noreduperson"));
			
			/*
			Cas10TicketValidator validator = new Cas10TicketValidator(serverUrl);
			Assertion assertion = validator.validate(request.ticket, request.service.toExternalForm());
			Logger.debug("Assertion.getPrincipal().getAttributes().values().toString() = %s", assertion.getPrincipal().getAttributes().values().toString());
			Logger.debug("Assertion.getPrincipal().getName() = %s", assertion.getPrincipal().getName());
			Logger.debug("Assertion.getAttributes().get('user') = %s", assertion.getAttributes().get("user"));
			Logger.debug("Assertion.getAttributes() = %s", assertion.getAttributes().toString());
			Logger.debug("Assertion.getPrincipal().getAttributes() = %s", assertion.getPrincipal().getAttributes().toString());
			*/
			
		    // Find correct user in the DB
			return null;
			/*
		    User user = (User)User.find("email", request.email).first();
		    return authenticate(user, request.password);
		    */
		}
	}
	
	public static User authenticate(User user, String passwordHash) throws Exception {
		return null;
		/*
	    if (user == null) {
			throw new Exception("No user with this email.");
	    } else {
	    	Logger.debug("authenticate() found user: %s", user.toString());
	    	if(!(user.authenticationBinding instanceof SimpleAuthenticationBinding)) {
	    		throw new Exception("This user cannot authenticate using the SimpleAuthBackend.");
	    	} else {
		    	SimpleAuthenticationBinding authBinding = (SimpleAuthenticationBinding)user.authenticationBinding;
		    	if(authBinding.checkPassword(passwordHash)) {
		    		user.secret = AuthenticationBackend.generateSecret();
			    	Logger.debug("Generated a new authentication secret: %s", user.secret);
			    	user.save();
			    	return user;
		    	} else {
		    		throw new Exception("Password didn't match.");
		    	}
	    	}
		}
		*/
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
