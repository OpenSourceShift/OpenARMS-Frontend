import org.junit.*;

import controllers.APIClient;

import api.Request.CreatePollRequest;
import api.Response.CreatePollResponse;
import api.entities.PollJSON;
import play.test.*;
import play.mvc.*;
import play.mvc.Http.*;

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
    
}