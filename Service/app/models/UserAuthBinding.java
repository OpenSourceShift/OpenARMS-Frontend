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
		char[] setOfChars = {'0','1','2','3','4','5','6','7','8','9',
							 'a','b','c','d','e','f','g','h','i','j','k','l','m',
							 'n','o','p','q','r','s','t','u','v','w','x','y','z',
							 'A','B','C','D','E','F','G','H','I','J','K','L','M',
							 'N','O','P','Q','R','S','T','U','V','W','X','Y','Z',
							 ';','$','_','-','!','?','#',':','*','='};
		
		Random random = new Random();
		StringBuilder strBuild = null;
		List<User> users = User.findAll();
		do {
			strBuild = new StringBuilder();
			for (int i=0; i<50; i++)
				strBuild.append(setOfChars[random.nextInt(setOfChars.length-1)]);
			for (User user : users) {
				if ((strBuild != null) && (strBuild.toString().equals(user.secret)))
					strBuild = null;
			}
		} while (strBuild == null);
		return strBuild.toString();
	} 
}