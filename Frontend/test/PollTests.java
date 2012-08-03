import java.util.Random;

import org.junit.Test;

import controllers.APIClient;

import api.entities.PollJSON;
import api.requests.CreatePollRequest;
import api.responses.CreatePollResponse;
import api.responses.CreateUserResponse;
import api.responses.EmptyResponse;


public class PollTests extends BaseTest {

	@Test
	public void create() throws Exception {
    	Random r = new Random();
    	
		// Create it.
		CreateUserResponse response1 = createUser();
		assertTrue(response1.error_message, response1.success());
		assertNotNull(response1.user);

		super.authenticateUser();
		
    	PollJSON p1 = new PollJSON();
    	p1.question = "This is the first question.";
    	p1.admin = response1.user.id;
    	p1.multipleAllowed = r.nextBoolean();
    	p1.reference = "myreference";

    	CreatePollResponse response2 = (CreatePollResponse) APIClient.getInstance().sendRequest(new CreatePollRequest(p1));
    	ensureSuccessful(response2);
    	assertNotNull(response2.poll);
    	assertEquals(p1.question, response2.poll.question);
    	assertNotNull(response2.poll.id);
		
		// Delete the user if its there.
		deleteUserIfCreated();
	}

	@Test
	public void createWithoutAuthentication() throws Exception {
    	Random r = new Random();
    	
		// Create it.
		CreateUserResponse response1 = createUser();
		assertTrue(response1.error_message, response1.success());
		assertNotNull(response1.user);
    	
    	PollJSON p1 = new PollJSON();
    	p1.question = "This is the first question.";
    	p1.admin = response1.user.id;
    	p1.multipleAllowed = r.nextBoolean();
    	p1.reference = "myreference";

    	CreatePollResponse response2 = (CreatePollResponse) APIClient.getInstance().sendRequest(new CreatePollRequest(p1));
    	ensureSuccessful(response2);
    	assertNotNull(response2.poll);
    	assertEquals(p1.question, response2.poll.question);
    	assertNotNull(response2.poll.id);
		
		// Delete the user if its there.
		deleteUserIfCreated();
	}
}
