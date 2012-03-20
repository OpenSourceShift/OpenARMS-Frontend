package controllers;

import play.*;
import play.mvc.*;

/**
 * Abstract controller which specifies authentication method.
 * @author OpenARMS Service team
 */
public abstract class AuthBackend extends Controller {
	/**
	 * Method that authorize the user to access the system.
	 * @return true if user authorized and false otherwise
	 */
	abstract public boolean authorize();
	/**
	 * Method that resets the password of the user and sends it to user via email.
	 */
	abstract public void resetPassword();
}
