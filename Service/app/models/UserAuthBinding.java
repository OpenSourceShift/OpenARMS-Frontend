package models;

import java.util.List;
import java.util.Random;

import javax.persistence.*;
import play.db.jpa.Model;

/**
 * Abstract class to identify different user's authentication method.
 * @author OpenARMS Service team
 */
@Entity
public abstract class UserAuthBinding extends Model{
	/**
	 * Identifier of the user.
	 */
	@OneToOne
	public User user;
	
	/**
	 * This is secret generator, which generates long number converted to string.
	 * Need to decide the format and size of secret.
	 * @return secret converted to string
	 */
	public String generateSecret() {
		String SECRET_CHARSET = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		Random random = new Random();
		StringBuilder strBuild = null;
		//List<User> users = User.findAll();
		do {
			strBuild = new StringBuilder();
			for (int i=0; i<50; i++)
				strBuild.append(SECRET_CHARSET.charAt(random.nextInt(SECRET_CHARSET.length()-1)));
			//for (User user : users) {
			//	if ((strBuild != null) && (strBuild.toString().equals(user.secret)))
			//		strBuild = null;
			//}
		} while (strBuild == null);
		return strBuild.toString();
	} 
}