import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import org.junit.Test;

import play.Logger;
import play.mvc.results.Redirect;

import controllers.APIClient;

import api.entities.PollInstanceJSON;
import api.entities.PollJSON;
import api.requests.CreatePollInstanceRequest;
import api.requests.CreatePollRequest;
import api.responses.CreatePollInstanceResponse;
import api.responses.CreatePollResponse;
import api.responses.CreateUserResponse;
import api.responses.EmptyResponse;


public class PollInstanceTests extends BaseTest {

	@Test
	public void create() throws Exception {
		Long pollId = ensurePoll();
		
    	PollInstanceJSON pi1 = new PollInstanceJSON();
    	pi1.poll_id = pollId;
    	
    	Calendar start = Calendar.getInstance();
    	pi1.start = start.getTime();
    	
    	Calendar end = Calendar.getInstance();
    	end.set(Calendar.YEAR, start.get(Calendar.YEAR) + 1);
    	pi1.end = end.getTime();

    	CreatePollInstanceResponse response = (CreatePollInstanceResponse) APIClient.getInstance().sendRequest(new CreatePollInstanceRequest(pi1));
    	assertSuccessful(response);
    	assertNotNull(response.pollinstance.id);
    	assertEquals(pi1.poll_id, response.pollinstance.poll_id);
	}
	
	@Test
	public void createWithoutAuthentication() throws Exception {
		Long pollId = ensurePoll();
		client.deauthenticate();
		
    	PollInstanceJSON pi1 = new PollInstanceJSON();
    	pi1.poll_id = pollId;
    	
    	Calendar start = Calendar.getInstance();
    	pi1.start = start.getTime();
    	
    	Calendar end = Calendar.getInstance();
    	end.set(Calendar.YEAR, start.get(Calendar.YEAR) + 1);
    	pi1.end = end.getTime();

    	try {
        	CreatePollInstanceResponse response = (CreatePollInstanceResponse) APIClient.getInstance().sendRequest(new CreatePollInstanceRequest(pi1));
        	assertUnSuccessful(response);
        	assertNull(response.pollinstance.id);
    	} catch(RuntimeException e) {
    		assertEquals(e.getClass(), Redirect.class);
    	}
	}
}
