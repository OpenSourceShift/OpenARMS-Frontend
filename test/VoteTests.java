import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import org.junit.Test;

import play.Logger;
import play.mvc.results.Redirect;

import controllers.APIClient;

import api.entities.PollInstanceJSON;
import api.entities.PollJSON;
import api.entities.VoteJSON;
import api.requests.CreatePollInstanceRequest;
import api.requests.CreatePollRequest;
import api.requests.CreateVoteRequest;
import api.requests.ReadPollInstanceRequest;
import api.requests.ReadPollRequest;
import api.responses.CreatePollInstanceResponse;
import api.responses.CreatePollResponse;
import api.responses.CreateUserResponse;
import api.responses.CreateVoteResponse;
import api.responses.EmptyResponse;
import api.responses.ReadPollInstanceResponse;
import api.responses.ReadPollResponse;


public class VoteTests extends BaseTest {

	@Test
	public void create() throws Exception {
		Long pollInstanceId = ensurePollInstance();
		
		ReadPollInstanceResponse response1 = (ReadPollInstanceResponse) APIClient.getInstance().sendRequest(new ReadPollInstanceRequest(pollInstanceId));
		assertSuccessful(response1);
		PollInstanceJSON pi = response1.pollinstance;
		
		ReadPollResponse response2 = (ReadPollResponse) APIClient.getInstance().sendRequest(new ReadPollRequest(pi.poll_id));
		assertSuccessful(response2);
		PollJSON p = response2.poll;

		client.deauthenticate();
		for(int c = 0; c < 20; c++) {
	    	VoteJSON v = new VoteJSON();
	    	v.pollInstanceid = pollInstanceId;
	    	v.choiceid = p.choices.get(random.nextInt(p.choices.size())).id;
	    	
	    	CreateVoteResponse response = (CreateVoteResponse) APIClient.getInstance().sendRequest(new CreateVoteRequest(v));
	    	assertSuccessful(response);
	    	assertNotNull(response.vote);
		}
	}
	
	/*
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
	*/
}
