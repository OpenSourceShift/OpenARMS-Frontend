package models;

import play.data.validation.Password;
import play.db.jpa.*;
import play.*;

import javax.persistence.*;

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
	public String authenticate(User user, String password) {
		String secret = null;
		if (user.equals(this.user) && password.equals(this.password))
			secret = generateSecret();
		return secret;
	}
}
