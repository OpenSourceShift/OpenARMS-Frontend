package models;

import javax.persistence.*;

import play.data.validation.Password;
import play.db.jpa.*;
import play.*;
import java.util.*;

/**
 * Model class for a user.
 * @author OpenARMS Service team
 */
@Entity
public class User extends Model {
	/**
	 * Name of the user.
	 */
	public String name;
	/**
	 * An email of the user .
	 */
	public String email;
	/**
	 * Unique code to keep user logged in the system.
	 */
	public String secret;
	/**
	 * Identifier of the authentication method.
	 */
	@OneToOne
	public UserAuthBinding userAuth;
}
