import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.junit.Test;

import play.mvc.Http.StatusCode;
import play.test.UnitTest;
import api.entities.ChoiceJSON;
import api.entities.PollInstanceJSON;
import api.entities.PollJSON;
import api.entities.UserJSON;
import api.entities.VoteJSON;
import api.requests.ClonePollRequest;
import api.requests.CreateChoiceRequest;
import api.requests.CreatePollInstanceRequest;
import api.requests.CreatePollRequest;
import api.requests.CreateUserRequest;
import api.requests.CreateVoteRequest;
import api.requests.DeletePollRequest;
import api.requests.ReadPollByTokenRequest;
import api.requests.ReadPollRequest;
import api.requests.UpdatePollRequest;
import api.responses.ClonePollResponse;
import api.responses.CreateChoiceResponse;
import api.responses.CreatePollInstanceResponse;
import api.responses.CreatePollResponse;
import api.responses.CreateUserResponse;
import api.responses.CreateVoteResponse;
import api.responses.EmptyResponse;
import api.responses.ReadPollByTokenResponse;
import api.responses.ReadPollResponse;
import api.responses.Response;
import api.responses.UpdatePollResponse;
import controllers.APIClient;
import controllers.authentication.SimpleAuthenticationFrontend;

public class APIClientTest extends UnitTest {
	
	public static void failIfNotSuccessful(Response response) {
    	if(!StatusCode.success(response.statusCode)) {
    		if(response.error_message == null) {
    			response.error_message = "No error message from service.";
    		}
    		fail("did not get the HTTP-OK status-code from the service, got "+response.statusCode+": "+response.error_message);
		}
	}
	
	public static void failIfSuccessful(Response response) {
    	if(!StatusCode.success(response.statusCode)) {
    		if(response.error_message == null) {
    			response.error_message = "No error message from service.";
    		}
    		fail("did not get the HTTP-OK status-code from the service, got "+response.statusCode+": "+response.error_message);
		}
	}

	@Test
	public void TestVoting() throws Exception {
		//apiClient.deauthenticate();
		boolean authenticated = SimpleAuthenticationFrontend.authenticateSimple("test@test.com", "1234");
		assertTrue(authenticated);

    	VoteJSON v = new VoteJSON();
    	v.choiceid =  1+(long)(Math.random()*3);
    	v.pollInstanceid = (long) 1;
    	CreateVoteResponse vresponse = (CreateVoteResponse) APIClient.send(new CreateVoteRequest(v));
    	
    	failIfSuccessful(vresponse);
	}

	@Test
    public void testLogin() throws Exception {
		APIClient.loadServiceData("data.yml");
		boolean authenticated = SimpleAuthenticationFrontend.authenticateSimple("spam@creen.dk", "openarms");
		assertTrue(authenticated);
		
		APIClient apiClient = new APIClient();
		
    	PollJSON pj1 = new PollJSON();
    	pj1.question = "This is the first question.";
    	CreatePollResponse response1 = (CreatePollResponse) apiClient.sendRequest(new CreatePollRequest(pj1));
    	failIfNotSuccessful(response1);
    	assertEquals(pj1.question, response1.poll.question);
    	assertNotNull(response1.poll.id);
    	
    	PollJSON pj2 = new PollJSON();
    	pj2.id = (long) response1.poll.id;
    	pj2.question = "This is a new question: "+Math.random();
    	UpdatePollResponse response2 = (UpdatePollResponse) apiClient.sendRequest(new UpdatePollRequest(pj2));
    	failIfNotSuccessful(response2);
    	assertEquals(pj2.question, response2.poll.question);
    }
    
    @Test
    public void testCreateChoice() throws Exception {
    	ChoiceJSON c = new ChoiceJSON();
    	c.text = "choice text";
    	CreateChoiceResponse response = (CreateChoiceResponse) APIClient.send(new CreateChoiceRequest(c));
    	failIfNotSuccessful(response);
    	assertNotNull(response.choice.id);
    	assertEquals(response.choice.text, c.text);
    }
    
    @Test
    public void testCreatePollInstance() throws Exception {
    	PollInstanceJSON p = new PollInstanceJSON();
    	p.poll_id = (long) 1;
    	CreatePollInstanceResponse response = (CreatePollInstanceResponse) APIClient.send(new CreatePollInstanceRequest(p));
    	failIfNotSuccessful(response);
    	assertNotNull(response.pollinstance.id);
    	assertEquals(response.pollinstance.poll_id, p.poll_id);
    }

	@Test
    public void testCreateUser() throws Exception {
    	// WORKING
		UserJSON u = new UserJSON();
		u.name = "openarms";
		u.email = "avas@dfsdf.com";
		u.secret = null;
		Map<String, String> attributes = new HashMap<String, String>();
		attributes.put("password", "1234");

		CreateUserResponse response = (CreateUserResponse) APIClient.send(new CreateUserRequest(u, "controllers.SimpleAuthenticationBackend", attributes));

    	failIfNotSuccessful(response);
    	assertNotNull(response.user.id);
    	assertEquals(response.user.name, u.name);
    }

    @Test
    public void testCreatePoll() throws Exception {
    	//APIClient apiClient = new APIClient();
		//apiClient.setAuthentication((long) 1, "openarms");
		
    	PollJSON p = new PollJSON();
    	ChoiceJSON c = new ChoiceJSON();
    	ChoiceJSON c2 = new ChoiceJSON();
    	c2.text = "Todelete";
    	c.text = "Todelete2";
    	p.question = "Delete";
    	p.reference = "Delete";
    	p.choices = new LinkedList<ChoiceJSON>();
    	p.choices.add(c);
    	p.choices.add(c2);
    	CreatePollResponse response = (CreatePollResponse) APIClient.send(new CreatePollRequest(p));
    	failIfNotSuccessful(response);
    	assertNotNull(response.poll.id);
    	assertEquals(response.poll.question, p.question);
    }
	
    @Test
    public void testRetrievePoll() throws Exception {
    	// IS NEEDED TO CREATE POLL IN ADMIN TO WORKING TEST PROPERLY !!! :)
    	Long id = (long) 1;
    	ReadPollResponse response =  (ReadPollResponse) APIClient.send(new ReadPollRequest(id));
    	failIfNotSuccessful(response);
    	assertNotNull(response.poll.id);
    	assertEquals(response.poll.id,id);
    }
    
    @Test
    public void testRetrievePollbyToken() throws Exception {
    	// IS NEEDED TO CREATE POLL IN ADMIN TO WORKING TEST PROPERLY !!! :)
    	String token = "7897";
    	ReadPollByTokenResponse response = (ReadPollByTokenResponse) APIClient.send(new ReadPollByTokenRequest(token));
    	failIfNotSuccessful(response);
    	assertNotNull(response.poll.token);
    	assertEquals(response.poll.token,token);
    }

    @Test
    public void testEditPoll() throws Exception {
    	PollJSON p = new PollJSON();
    	ChoiceJSON c = new ChoiceJSON();
    	ChoiceJSON c2 = new ChoiceJSON();
    	c2.text = "TestChoice3";
    	c.text = "TestChoice4";
    	p.question = "poll question edited";
    	p.reference = "Testeditred";
    	p.choices = new LinkedList<ChoiceJSON>();
    	p.choices.add(c);
    	p.choices.add(c2);
    	p.id = (long) 1;
    	UpdatePollResponse response =  (UpdatePollResponse) APIClient.send(new UpdatePollRequest(p));
    	failIfNotSuccessful(response);
    	assertNotNull(response.poll.token);
    	assertEquals(response.poll.id,p.id);
    }

    @Test
    public void testClonePoll() throws Exception {
    	Long id = (long) 2;
    	ClonePollResponse response =  (ClonePollResponse) APIClient.send(new ClonePollRequest(id));
    	failIfNotSuccessful(response);
    	assertNotNull(response.poll.token);
    	assertNotSame(response.poll.id,id);
    }
    
    @Test
    public void testDeletePoll() throws Exception {
    	Long id = (long) 9;
    	EmptyResponse response =  (EmptyResponse) APIClient.send(new DeletePollRequest(id));
    	failIfNotSuccessful(response);
    }

    @Test
    public void testCreateVote() throws Exception {
    	VoteJSON v = new VoteJSON();
    	v.choiceid = (long) 1;
    	v.pollInstanceid = (long) 1;
    	CreateVoteResponse response = (CreateVoteResponse) APIClient.send(new CreateVoteRequest(v));
    	failIfNotSuccessful(response);
    	assertNotNull(response.vote.id);
    	assertEquals(response.vote.choiceid, v.choiceid);
    }
}