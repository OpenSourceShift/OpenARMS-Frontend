package controllers;

import java.util.List;

import api.entities.UserJSON;

import notifiers.MailNotifier;

import models.SimpleUserAuthBinding;
import models.User;
import play.*;
import play.mvc.*;
import play.mvc.Http.*;

/**
 * Controller which specifies simple authentication method.
 * @author OpenARMS Service team
 */
public class SimpleAuthBackend extends AuthBackend {
	/**
	 * Method that authenticates the user to access the system.
	 * @return true if user authenticated and false otherwise
	 */
	public static boolean authenticate(User user) {
	    boolean authenticated = false;
	    // Find correct user in the DB
	    User u = (User)User.find("name",user.name).first();
	    if (u != null) {
	    	SimpleUserAuthBinding auth = (SimpleUserAuthBinding)u.userAuth;
	    	u.secret = auth.authenticate(((SimpleUserAuthBinding)user.userAuth).password);
	    	if (user.secret != null)
				authenticated = true;
		}
	    // Send the response in case user has not been authenticated
	    if (!authenticated) {
	    	Http.Response.current().status = 401;
	    	Http.Response.current().setHeader("Content-Length", "0");
	    	Http.Response.current().setHeader("WWW-Authenticate", "Basic");
	    }
	    return authenticated; 
	}
	
	/**
	 * Method that resets the password of the user and sends it to user via email.
	 */
	@Override
	public void resetPassword() {
		User user = null;
		Header header = Http.Request.current().headers.get("passreset");
		if (header != null) {
			user = (User)User.find("name", Http.Request.current().user).fetch().get(0);
			if (user != null) {
				((SimpleUserAuthBinding)user.userAuth).generatePassword();
				MailNotifier.sendPassword(user);
			}
		}
	}
	
	
}
