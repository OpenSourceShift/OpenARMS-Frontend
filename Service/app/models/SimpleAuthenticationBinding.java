package models;

import play.data.validation.Password;
import play.data.validation.Required;
import play.db.jpa.*;
import play.libs.Crypto;
import play.libs.Crypto.HashType;
import play.mvc.results.Unauthorized;
import play.*;

import javax.persistence.*;

import notifiers.MailNotifier;

import java.util.List;
import java.util.Random;

/**
 * Class that specifies simple user authentication method.
 * @author OpenARMS Service team
 */
@Entity
public class SimpleAuthenticationBinding extends AuthenticationBinding {
	
	/**
	 * User's password.
	 */
	@Required
	public String passwordHash;
	
	public void setPassword(String password) {
		passwordHash = Crypto.passwordHash(password, HashType.SHA256);
	}
	
	/**
	 * Checks if the password matches the current user.
	 * @param password This can either be the paintext password or the SHA256 hash of the password.
	 * @return True if the password matches that of the binding, false otherwise.
	 */
	public boolean checkPassword(String password) {
		if(passwordHash == null) {
			return false;
		} else if(password == null) {
			return false;
		} else {
			return passwordHash.equals(password) || Crypto.passwordHash(password, HashType.SHA256).equals(passwordHash);
		}
	}
	
	/**
	 * Method to generate and to change password for the user.
	 * @return The new password.
	 */
	public String generatePassword() {
		String SECRET_CHARSET = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		Random random = new Random();
		StringBuilder strBuild = new StringBuilder();
		for (int i=0; i<8; i++) {
			strBuild.append(SECRET_CHARSET.charAt(random.nextInt(SECRET_CHARSET.length()-1)));
		}
		
		String password = strBuild.toString();
		setPassword(password);
		return password;
	}
}
