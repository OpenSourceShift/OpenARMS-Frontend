package models;

import java.util.List;
import java.util.Random;

import javax.persistence.*;

import play.data.validation.Required;
import play.db.jpa.Model;

/**
 * Abstract class to identify different user's authentication method.
 * @author OpenARMS Service team
 */
@Entity
public abstract class AuthenticationBinding extends Model {
	/**
	 * Identifier of the user.
	 */
	@OneToOne(fetch=FetchType.LAZY)
	public User user;
}
