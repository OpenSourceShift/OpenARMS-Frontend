package controllers;
 
import play.Play;

/**
 * Controller class for a securing administration interface.
 * @author OpenARMS Service team
 */
public class Security extends Secure.Security {
	/**
	 * Used to authenticate the administrator.
	 * @return boolean true when authenticated or false otherwise
	 */
    static boolean authenticate(String username, String password) {
    	String admin_user = Play.configuration.getProperty("admin_user");
        String admin_pass = Play.configuration.getProperty("admin_pass");
        return username.equals(admin_user) && password.equals(admin_pass);
    }
    /**
	 * Used when disconnected/logged out from the administration interface.
	 */
    static void onDisconnected() {
        CRUD.index();
    }
    /**
	 * Used when authenticated/logged in to the administration interface.
	 */
    static void onAuthenticated() {
        CRUD.index();
    }
}