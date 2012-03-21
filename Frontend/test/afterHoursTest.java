import java.util.List;

import org.junit.Test;

import api.entities.PollJSON;
import api.requests.CreatePollRequest;
import api.requests.ReadUserDetailsRequest;
import api.requests.UpdatePollRequest;
import api.responses.CreatePollResponse;
import api.responses.ReadUserDetailsResponse;
import api.responses.Response;
import api.responses.UpdatePollResponse;

import controllers.APIClient;

import play.mvc.Controller;
import play.mvc.Http.StatusCode;
import play.test.UnitTest;


public class afterHoursTest extends UnitTest {
	
	public static void failIfNotSuccessful(Response response) {
    	if(!StatusCode.success(response.statusCode)) {
    		if(response.error_message == null) {
    			response.error_message = "No error message from service.";
    		}
    		fail("did not get the HTTP-OK status-code from the service, got "+response.statusCode+": "+response.error_message);
		}
	}
	
	@Test
	public void testStuff() throws Exception {
		APIClient.loadServiceData("data.yml");
		APIClient apiClient = new APIClient();
		boolean authenticated = apiClient.authenticateSimple("spam@creen.dk", "openarms");
		assertTrue(authenticated);
		
    	PollJSON pj1 = new PollJSON();
    	pj1.question = "This is the first question.";
    	CreatePollResponse response1 = (CreatePollResponse) apiClient.sendRequest(new CreatePollRequest(pj1));
    	failIfNotSuccessful(response1);
    	assertEquals(pj1.question, response1.poll.question);
    	assertNotNull(response1.poll.id);
    	
    	PollJSON pj2 = new PollJSON();
    	pj2.id = (long) response1.poll.id;
    	pj2.question = "This is a new question: 2";
    	UpdatePollResponse response2 = (UpdatePollResponse) apiClient.sendRequest(new UpdatePollRequest(pj2));
    	failIfNotSuccessful(response2);
    	assertEquals(pj2.question, response2.poll.question);
    	
    	PollJSON pj3 = new PollJSON();
    	pj3.id = (long) response1.poll.id;
    	pj3.question = "This is a new question: 3";
    	UpdatePollResponse response3 = (UpdatePollResponse) apiClient.sendRequest(new UpdatePollRequest(pj3));
    	failIfNotSuccessful(response3);
    	assertEquals(pj3.question, response3.poll.question);
    	
    	ReadUserDetailsResponse responseUser = (ReadUserDetailsResponse) apiClient.sendRequest(new ReadUserDetailsRequest(controllers.LoginUser.getCurrentUserId()));
    	List<PollJSON> pollsJson = responseUser.polls;
    	for (PollJSON poll : pollsJson) {
    		System.out.println(poll);
    	}
    	
		
	}
	
	
}
