import org.junit.Test;

import play.test.FunctionalTest;
import play.test.UnitTest;
import api.requests.*;
import api.responses.*;
import api.entities.*;
import controllers.APIClient;

public class APIClientTest extends UnitTest {

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