package models;

import play.data.validation.Password;
import play.db.jpa.*;
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
public class SimpleUserAuthBinding extends UserAuthBinding {
	/**
	 * User's password.
	 */
	@Password
	public String password;

	/**
	 * Method to authenticate the user.
	 * @param user user's identifier
	 * @param password password that we want to check
	 * @return newly generated secret to keep user logged in the system 
	 */
	public String authenticate(String password) {
		String secret = null;
		if (password.equals(this.password))
			secret = generateSecret();
		return secret;
	}
	
	/**
	 * Method to generate and to change password for the user.
	 */
	public void generatePassword() {
		String SECRET_CHARSET = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		Random random = new Random();
		StringBuilder strBuild = new StringBuilder();
		for (int i=0; i<8; i++)
			strBuild.append(SECRET_CHARSET.charAt(random.nextInt(SECRET_CHARSET.length()-1)));
		this.password = strBuild.toString();
	}
}
