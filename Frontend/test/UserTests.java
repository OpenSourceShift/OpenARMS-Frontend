import org.junit.Test;

import api.responses.CreateUserResponse;
import api.responses.EmptyResponse;


public class UserTests extends BaseTest {

	@Test
	public void create() throws Exception {
		// Create it.
		CreateUserResponse response2 = createUser();
		assertTrue(response2.error_message, response2.success());
		assertNotNull(response2.user);
		
		// Delete the user if its there.
		deleteUserIfCreated();
	}
	
	@Test
	public void createEmptyEmail() throws Exception {
		CreateUserResponse response = createUser("", "");
		assertFalse("The response was successful, but it should have failed.", response.success());
		assertNull(response.user);
		
		// Delete the user if its there.
		deleteUserIfCreated();
	}
	
	@Test
	public void createDuplicate() throws Exception {
		CreateUserResponse response1 = createUser();
		assertTrue(response1.error_message, response1.success());
		assertNotNull(response1.user);
		
		CreateUserResponse response2 = createUser();
		assertFalse("The response was successful, but it should have failed.", response2.success());
		assertNull(response2.user);
		
		// Delete the user if its there.
		deleteUserIfCreated();
	}
}
