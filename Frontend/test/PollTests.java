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
		ensureAuthenticatedUser();
		
    	PollJSON p1 = new PollJSON();
    	p1.question = "This is the first question.";
    	p1.admin = client.getCurrentUserId();
    	p1.loginRequired = false;
    	p1.multipleAllowed = random.nextBoolean();
    	p1.reference = "myreference";

    	CreatePollResponse response2 = (CreatePollResponse) APIClient.getInstance().sendRequest(new CreatePollRequest(p1));
    	ensureSuccessful(response2);
    	assertNotNull(response2.poll);
    	assertEquals(p1.question, response2.poll.question);
    	assertNotNull(response2.poll.id);
	}

	@Test
	public void createWithoutAuthentication() throws Exception {
		ensureAuthenticatedUser();
		Long validUserId = client.getCurrentUserId();
		client.deauthenticate();
    	
    	PollJSON p1 = new PollJSON();
    	p1.question = "This is the first question.";
    	p1.admin = validUserId;
    	p1.loginRequired = false;
    	p1.multipleAllowed = random.nextBoolean();
    	p1.reference = "myreference";

    	try {
    		CreatePollResponse response2 = (CreatePollResponse) APIClient.getInstance().sendRequest(new CreatePollRequest(p1));
        	ensureSuccessful(response2);
        	assertNotNull(response2.poll);
        	assertEquals(p1.question, response2.poll.question);
        	assertNotNull(response2.poll.id);
    	} catch(RuntimeException e) {
    		assertEquals(e.getCause().getMessage(), "You have to be authorized to create a poll.");
    	}
	}
}
