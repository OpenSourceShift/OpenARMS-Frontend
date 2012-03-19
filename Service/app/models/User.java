package models;

import javax.persistence.*;

import play.data.validation.Password;
import play.db.jpa.*;
import play.*;
import java.util.*;

import api.entities.Jsonable;
import api.entities.UserJSON;

/**
 * Model class for a user.
 * @author OpenARMS Service team
 */
@Entity
public class User extends Model implements Jsonable {
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

	 /**
     * Turn this User into a UserJSON.
     * @return UserJSON object that represents this user
     */
    public UserJSON toJson() {
    	return toJson(this);
    }

    /**
     * Turn a User into a UserJSON
     * @param user the user
     * @return UserJSON object that represents the user
     */
    public static UserJSON toJson(User user) {
    	UserJSON result = new UserJSON();
    	result.name = user.name;
    	result.email = user.email;
    	result.secret = user.secret;
    	result.backend = user.userAuth.getClass().toString();
		return result;
    }
    
    /**
     * Turn a UserJSON into a user
     * @param json UserJSON object
     * @return User object that represents a user.
     */
    public static User fromJson(UserJSON json) {
    	User result = new User();
    	result.name = json.name;
    	result.email = json.email;
    	result.secret = json.secret;
    	if (json.backend.equals(Play.configuration.getProperty("simple_backend")))
    		result.userAuth = new SimpleUserAuthBinding();
    	else
    		result.userAuth = null;
		return result;
    }
}
