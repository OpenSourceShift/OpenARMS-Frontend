import java.util.Random;

import org.junit.Test;

import api.responses.CreateUserResponse;
import api.responses.EmptyResponse;


public class UserTests extends BaseTest {

	@Test
	public void ensureUser() throws Exception {
		Long userId = ensureAuthenticatedUser();
		assertNotNull("Could not ensure a user.", userId);
	}

	@Test
	public void create() throws Exception {
		String email = "random"+Integer.toHexString(random.nextInt())+"@openarms.dk";
		// Create it.
		CreateUserResponse response = createUser(email, BaseTest.PASSWORD);
		assertTrue(response.error_message, response.success());
		assertNotNull(response.user);
	}
	
	@Test
	public void createEmptyEmail() throws Exception {
		try {
			CreateUserResponse response = createUser("", BaseTest.PASSWORD);
			assertFalse("The response was successful, but it should have failed.", response.success());
			assertNull(response.user);
		} catch(RuntimeException e) {
			// We excepted some errors.
			e.printStackTrace();
		}
	}
	
	@Test
	public void createDuplicate() throws Exception {
		String email = "random"+Integer.toHexString(random.nextInt())+"@openarms.dk";
		
		// Create it.
		CreateUserResponse response1 = createUser(email, BaseTest.PASSWORD);
		assertTrue(response1.error_message, response1.success());
		assertNotNull(response1.user);

		try {
			CreateUserResponse response2 = createUser(email, BaseTest.PASSWORD);
			assertFalse("The response was successful, but it should have failed.", response2.success());
			assertNull(response2.user);
		} catch(RuntimeException e) {
			// We excepted some errors.
			e.printStackTrace();
		}
	}
}
