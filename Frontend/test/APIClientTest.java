import org.junit.Test;
import play.test.FunctionalTest;
import api.requests.*;
import api.responses.*;
import api.entities.*;
import controllers.APIClient;

public class APIClientTest extends FunctionalTest {

    @Test
    public void testThatTheAPIClientWorks() throws Exception {
    	// Can we request a Poll from the service?
    	PollJSON p = new PollJSON();
    	p.question = "Test question ...";
		CreatePollResponse response = (CreatePollResponse)APIClient.send(new CreatePollRequest(p));
		assertNotNull(response.poll.id);
		assertEquals(response.poll.question, p.question);
    }
    
    @Test
    public void testCreateChoice() throws Exception {
    	ChoiceJSON c = new ChoiceJSON();
    	c.text = "choice text";
    	CreateChoiceResponse response = (CreateChoiceResponse) APIClient.send(new CreateChoiceRequest(c));
    	assertNotNull(response.choice.id);
    	assertEquals(response.choice.text, c.text);
    }
    
    @Test
    public void testCreatePollInstance() throws Exception {
    	PollInstanceJSON p = new PollInstanceJSON();
    	p.poll_id = (long) 1;
    	CreatePollInstanceResponse response = (CreatePollInstanceResponse) APIClient.send(new CreatePollInstanceRequest(p));
    	assertNotNull(response.pollinstance.id);
    	assertEquals(response.pollinstance.poll_id, p.poll_id);
    }
    
    @Test
    public void testCreatePoll() throws Exception {
    	PollJSON p = new PollJSON();
    	p.question = "poll question";
    	CreatePollResponse response = (CreatePollResponse) APIClient.send(new CreatePollRequest(p));
    	assertNotNull(response.poll.id);
    	assertEquals(response.poll.question, p.question);
    }
    
    @Test
    public void testCreateUser() throws Exception {
    	UserJSON u = new UserJSON();
    	u.name = "user name";
    	CreateUserResponse response = (CreateUserResponse) APIClient.send(new CreateUserRequest(u));
    	assertNotNull(response.user.id);
    	assertEquals(response.user.name, u.name);
    }
    
    @Test
    public void testCreateVote() throws Exception {
    	VoteJSON v = new VoteJSON();
    	v.choiceid = (long) 1;
    	v.pollInstanceid = (long) 1;
    	CreateVoteResponse response = (CreateVoteResponse) APIClient.send(new CreateVoteRequest(v));
    	assertNotNull(response.vote.id);
    	assertEquals(response.vote.choiceid, v.choiceid);
    }
}