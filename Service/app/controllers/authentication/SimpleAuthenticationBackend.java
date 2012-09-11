package controllers.authentication;

import java.util.List;

import api.entities.UserJSON;
import api.helpers.GsonHelper;
import api.requests.SimpleAuthenticateUserRequest;
import api.requests.AuthenticateUserRequest;
import api.requests.GenerateAuthChallengeRequest;
import api.requests.GenerateSimpleAuthChallengeRequest;
import api.responses.CreateUserResponse;
import api.responses.EmptyResponse;
import api.responses.GenerateAuthChallengeResponse;
import api.responses.GenerateSimpleAuthChallengeResponse;

import notifiers.MailNotifier;

import models.SimpleAuthenticationBinding;
import models.User;
import models.AuthenticationBinding;
import play.*;
import play.mvc.*;
import play.mvc.Http.*;

/**
 * Controller which specifies simple authentication method.
 * @author OpenARMS Service team
 */
public class SimpleAuthenticationBackend extends AuthenticationBackend {
	
	public static Class<? extends GenerateAuthChallengeRequest> getChallengeRequestClass() throws Exception {
		return GenerateSimpleAuthChallengeRequest.class;
	}
	
	public static Class<? extends GenerateAuthChallengeResponse> getChallengeResponseClass() throws Exception {
		return GenerateSimpleAuthChallengeResponse.class;
	}
	
	public static Class<? extends AuthenticateUserRequest> getAuthenticateUserRequestClass() throws Exception {
		return SimpleAuthenticateUserRequest.class;
	}
	
	/**
	 * Method that authenticates the user to access the system.
	 * @return true if user authenticated and false otherwise
	 * @throws Exception 
	 */
	public static User authenticate(AuthenticateUserRequest req) throws Exception {
		if(!(req instanceof SimpleAuthenticateUserRequest)) {
			throw new Exception("This authenticate user request is not supported by this backend.");
		} else {
			SimpleAuthenticateUserRequest request = (SimpleAuthenticateUserRequest) req;
		    // Find correct user in the DB
		    User user = (User)User.find("email", request.email).first();
		    return authenticate(user, request.password);
		}
	}
	
	public static User authenticate(User user, String passwordHash) throws Exception {
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
	}
	
	/**
	 * Method that resets the password of the user and sends it to user via email.
	 */
	public static void resetPassword() {
		User user = null;
		Header header = Http.Request.current().headers.get("passreset");
		if (header != null) {
			user = (User)User.find("name", Http.Request.current().user).fetch().get(0);
			if (user != null) {
				String password = ((SimpleAuthenticationBinding)user.authenticationBinding).generatePassword();
				MailNotifier.sendPassword(user, password);
			}
		}
	}
	
	public static GenerateAuthChallengeResponse generateChallenge(GenerateAuthChallengeRequest req) throws Exception {
		if(req instanceof GenerateSimpleAuthChallengeRequest) {
			System.out.println("Generating a simple authentication challenge.");
			return new GenerateSimpleAuthChallengeResponse();
		} else {
			throw new Exception("Can only generate authentication challenges based on GenerateSimpleAuthChallengeRequests");
		}
	}
}
