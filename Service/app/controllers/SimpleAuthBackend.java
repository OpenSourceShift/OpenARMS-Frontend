package controllers;

import java.util.List;

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
	 * Method that authorize the user to access the system.
	 * @return true if user authorized and false otherwise
	 */
	public boolean authorize() {
	    boolean authorized = false;
	    User user = null;
	    // Check the header of the request
	    Header header = Http.Request.current().headers.get("authorization");
	    if (header != null) {
	    	if (Http.Request.current().password != null) {
	    		// Get the user specified within the http request
	    		user = (User)User.find("name", Http.Request.current().user).fetch().get(0);
	    		if (user != null) {
	    			// Check the authentication method
	    			if (user.userAuth instanceof SimpleUserAuthBinding) {
	    				SimpleUserAuthBinding auth = (SimpleUserAuthBinding)user.userAuth;
	    				// Authenticate
	    				user.secret = auth.authenticate(user, Http.Request.current().password);
	    				authorized = true;
	    			}
	    		}
	    	}
	    }
	    // Send the response in case user has not been authenticated
	    if (!authorized) {
	    	Http.Response.current().status = 401;
	    	Http.Response.current().setHeader("Content-Length", "0");
	    	Http.Response.current().setHeader("WWW-Authenticate", "Basic");
	    }
	    return authorized; 
	}
}
