package controllers.crud;
 
import controllers.CRUD;
import controllers.Secure;
import play.Logger;
import play.Play;
import play.libs.Crypto;

/**
 * Controller class for a securing administration interface.
 * @author OpenARMS Service team
 */
public class Security extends Secure.Security {
	
	protected static final int BLOCKSIZE = 16;
	protected static final char PADDING = ' ';
	
	/**
	 * Used to authenticate the administrator.
	 * @return boolean true when authenticated or false otherwise
	 */
    static boolean authenticate(String username, String password) {
    	String adminUsername = Play.configuration.getProperty("crud.admin_username");
        String adminPassword = Play.configuration.getProperty("crud.admin_password");

        String paddedPassword = pad(password);
        String passwordEncrypted = Crypto.encryptAES(paddedPassword).substring(0, 32);
        String passwordHashed = Crypto.passwordHash(passwordEncrypted);
        
        Logger.debug("Authenticating towards the CRUD interface given password '%s', which hashes to '%s', which should match '%s'.", password, passwordHashed, adminPassword);

        return username.equals(adminUsername) && passwordHashed.equals(adminPassword);
    }
    
    private static String pad(String input) {
    	for(int i = 0; i < input.length() % BLOCKSIZE; i++) {
    		input += PADDING;
    	}
    	return input;
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