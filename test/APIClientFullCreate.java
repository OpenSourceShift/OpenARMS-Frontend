import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;

import org.junit.Test;

import play.libs.Crypto;
import play.mvc.Controller;
import play.mvc.Http.StatusCode;
import play.test.Fixtures;
import play.test.UnitTest;
import api.requests.*;
import api.responses.*;
import api.entities.*;
import controllers.APIClient;
import controllers.BaseController;
import controllers.authentication.SimpleAuthentication;

public class APIClientFullCreate extends UnitTest {
	
	public static void failIfNotSuccessful(Response response) {
    	if(!StatusCode.success(response.statusCode)) {
    		if(response.error_message == null) {
    			response.error_message = "No error message from service.";
    		}
    		fail("did not get the HTTP-OK status-code from the service, got "+response.statusCode+": "+response.error_message);
		}
	}
	
	@Test
	public void testCreateFullConfig() throws Exception {

    	Random r = new Random();
		// Create user
		UserJSON u = new UserJSON();
		u.name = "openarms";
		u.email = "test@test.com";
		Map<String, String> attributes = new HashMap<String, String>();
		attributes.put("password", "1234");

		try {
			CreateUserResponse userresponse = (CreateUserResponse) APIClient.send(new CreateUserRequest(u, "controllers.SimpleAuthenticationBackend", attributes));
	    	assertNotNull(userresponse.user.id);
	    	assertEquals(userresponse.user.name, u.name);
		} catch(Exception e) {
			if(e.getCause().getMessage().equals("User already exists in the system")) {
				// This is not a problem.
			}
		}
		
    	// Authenticate
		APIClient apiClient = new APIClient();
		boolean authenticated = SimpleAuthentication.authenticateSimple(u.email, attributes.get("password"));
		assertTrue(authenticated);
    
    	PollJSON pj1 = new PollJSON();

    	pj1.question = "This is the first question.";
    	//pj1.admin = userresponse.user.id;
    	pj1.admin = APIClient.getCurrentUserId();
    	pj1.multipleAllowed = r.nextBoolean();
    	pj1.loginRequired = false;
    	pj1.reference = "myreference";

    	CreatePollResponse pollresponse = (CreatePollResponse) apiClient.sendRequest(new CreatePollRequest(pj1));
    	failIfNotSuccessful(pollresponse);
    	assertEquals(pj1.question, pollresponse.poll.question);
    	assertNotNull(pollresponse.poll.id);
    	
    	CreateChoiceResponse choiceresponse = null;
    	for(int i = 0; i < 4; i++) {
        	ChoiceJSON c = new ChoiceJSON();
        	c.text = "choice text "+i;
        	c.poll_id = pollresponse.poll.id;
        	c.correct = (i == 0 ? true : false);
        	choiceresponse = (CreateChoiceResponse) APIClient.send(new CreateChoiceRequest(c));
        	failIfNotSuccessful(choiceresponse);
        	assertNotNull(choiceresponse.choice.id);
        	assertEquals(choiceresponse.choice.text, c.text);
    	}
    	
    	PollInstanceJSON p = new PollInstanceJSON();
    	p.poll_id = pollresponse.poll.id;
    	p.start = new Date(0);
    	p.end = new Date();
    	CreatePollInstanceResponse piresponse = (CreatePollInstanceResponse) APIClient.send(new CreatePollInstanceRequest(p));
    	failIfNotSuccessful(piresponse);
    	assertNotNull(piresponse.pollinstance.id);
    	assertEquals(piresponse.pollinstance.poll_id, p.poll_id);
    	
    	boolean failed = false;
    	try {
    		SimpleAuthentication.authenticateSimple(u.email, "fail");
    	} catch(Exception e) {
    		assertEquals("Password didn't match.", e.getCause().getMessage());
    		failed = true;
    	}
    	assertTrue(failed);
    	
    	// We have to deauthenticate to not trigger the "You can't vote twice for the same Choice." exception.
    	APIClient.deauthenticate();
    	
    	for(int i=0; i<20; i++) {
        	VoteJSON v = new VoteJSON();
        	v.choiceid =  1+(long)(Math.random()*3);
        	v.pollInstanceid = piresponse.pollinstance.id;
        	
        	CreateVoteResponse vresponse = (CreateVoteResponse) APIClient.send(new CreateVoteRequest(v));
        	failIfNotSuccessful(vresponse);
        	assertNotNull(vresponse.vote.id);
        	assertEquals(vresponse.vote.choiceid, v.choiceid);
    	}	
    	
    	for(int i=0; i<20; i++) {
        	VoteJSON v = new VoteJSON();
        	v.choiceid =  1+(long)(Math.random()*3);
        	v.pollInstanceid = piresponse.pollinstance.id;
        	
        	VoteOnPollInstanceResponse vresponse = (VoteOnPollInstanceResponse) APIClient.send(new VoteOnPollInstanceRequest(v));
        	failIfNotSuccessful(vresponse);
        	assertNotNull(vresponse.vote.id);
        	assertEquals(vresponse.vote.choiceid, v.choiceid);
    	}
	}
}
