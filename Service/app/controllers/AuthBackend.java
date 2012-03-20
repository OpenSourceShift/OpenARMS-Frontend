package controllers;

import api.entities.UserJSON;
import models.User;
import play.*;
import play.mvc.*;
import play.mvc.Http.Header;

/**
 * Abstract controller which specifies authentication method.
 * @author OpenARMS Service team
 */
public abstract class AuthBackend extends Controller {
	/**
	 * Method that authorize the user to access the system.
	 * @return true if user authorized and false otherwise
	 */
	public static boolean authenticate(User user) {
		//TODO: This will be modified
		return false;
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
	    	
	    	u = User.findById(Long.parseLong(user));
	    	if (u.secret.equals(secret)) {
	    		return u;
	    	} else {
	    		return null;
	    	}
	    }
		return null;
	}
}
