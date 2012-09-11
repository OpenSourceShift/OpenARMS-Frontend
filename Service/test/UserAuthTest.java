import java.util.List;

import models.SimpleAuthenticationBinding;
import models.User;

import org.junit.Test;

import controllers.UserController;
import controllers.authentication.SimpleAuthenticationBackend;

import play.test.Fixtures;
import play.test.UnitTest;

/**
 * User authentication test.
 * @author OpenARMS Service team
 */
public class UserAuthTest extends UnitTest {
	/**
	 * Authentication test.
	 * @throws Exception 
	 */
	@Test
    public void authenticationTest() throws Exception {
		//Fixtures.deleteAllModels();
		//Fixtures.loadModels("data.yml");
		
		// Insert user to DB
		User u = new User();
		u.name = "test";
		u.email = "avas@dfsdf.com";
		u.secret = null;
		u.authenticationBinding = null;
		u.save();
		// Insert simple authentication to DB
		SimpleAuthenticationBinding s = new SimpleAuthenticationBinding();
		s.user = u;
		s.setPassword("");
		s.save();
		// Bind authentication method with user in DB
		u.authenticationBinding = s;
		u.save();
		
		// Request user from DB
		List<User> users = User.findAll();
    	assertEquals(users.size(), 1);
    	User user = users.get(0);
    	// Try to authenticate
    	User secret = SimpleAuthenticationBackend.authenticate(user, "secret");
    	// Check if authenticated
    	assertNotNull(secret);
    }
}