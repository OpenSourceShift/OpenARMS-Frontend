package models;

import play.data.validation.Password;
import play.data.validation.Required;
import play.db.jpa.*;
import play.libs.Crypto;
import play.libs.Crypto.HashType;
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
	
	public boolean checkPassword(String password) {
		return Crypto.passwordHash(password, HashType.SHA256).equals(passwordHash);
	}
	
	/**
	 * Method to generate and to change password for the user.
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
