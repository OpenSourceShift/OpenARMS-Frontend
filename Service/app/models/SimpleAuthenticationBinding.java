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
public class SimpleAuthenticationBinding extends UserAuthBinding {
	/**
	 * User's password.
	 */
	@Password
	public String password;
	
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
