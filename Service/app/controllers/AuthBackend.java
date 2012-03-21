package controllers;

import controllers.APIController.UnauthorizedException;
import api.entities.UserJSON;
import models.User;
import play.*;
import play.mvc.*;
import play.mvc.Http.Header;

/**
 * Abstract controller which specifies authentication method.
 * @author OpenARMS Service team
 */
public abstract class AuthBackend {
	/**
	 * Method that authorize the user to access the system.
	 * @return true if user authorized and false otherwise
	 */
	public static User authenticate(User user) {
		//TODO: This will be modified
		return null;
	}
	/**
	 * Method that resets the password of the user and sends it to user via email.
	 */
	abstract public void resetPassword();
	
	public static User getCurrentUser() {
		User u = null;		
		Header header = Http.Request.current().headers.get("authorization");
	    if (header != null) {
	    	String user = Http.Request.current().user;
	    	String secret = Http.Request.current().password;
	    	Logger.debug("getCurrentUser() called with user = %s and secret = %s.", user, secret);
	    	
	    	u = User.findById(Long.parseLong(user));
	    	if (u != null && u.secret != null && u.secret.equals(secret)) {
	    		return u;
	    	} else {
	    		return null;
	    	}
	    } else {
	    	Logger.debug("getCurrentUser() called but HTTP authorization header not set.");
	    	return null;
	    }
	}
	
	public static void requireUser(User user) throws UnauthorizedException {
		User currentUser = AuthBackend.getCurrentUser();
		if(user != null) {
			if(currentUser == null) {
				throw new UnauthorizedException("This action requires authentication. Please use the /user/authenticate to get your user secret.");
			} else {
				if(!user.equals(currentUser)) {
					throw new UnauthorizedException("This action requires authentication.");
				}
			}
		}
	}
}
