import org.junit.Test;

import play.test.UnitTest;
import api.entities.PollInstanceJSON;
import api.entities.PollJSON;
import api.requests.CreatePollInstanceRequest;
import api.requests.CreatePollRequest;
import api.responses.CreatePollInstanceResponse;
import api.responses.CreatePollResponse;
import controllers.APIClient;
import controllers.authentication.SimpleAuthentication;


public class CreateTestData extends UnitTest {
	
	@Test
	public void whatever() {
		// test! data
		try {
			APIClient.loadServiceData("data.yml");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			boolean authenticated = SimpleAuthentication.authenticateSimple("spam@creen.dk", "openarms");
			assertTrue(authenticated);
		} catch (Exception e) {}

		APIClient apiClient = new APIClient();
		
		PollJSON pj1 = new PollJSON();
		pj1.question = "This is the first question.";
		try {
			CreatePollResponse cpr1 = (CreatePollResponse) apiClient.sendRequest(new CreatePollRequest(pj1));
			PollInstanceJSON pij1 = new PollInstanceJSON();
			pij1.poll_id = cpr1.poll.id;
			CreatePollInstanceResponse cpir1 = (CreatePollInstanceResponse) apiClient.sendRequest(new CreatePollInstanceRequest(pij1));
			
			PollInstanceJSON pij2 = new PollInstanceJSON();
			pij2.poll_id = cpr1.poll.id;
			CreatePollInstanceResponse cpir2 = (CreatePollInstanceResponse) apiClient.sendRequest(new CreatePollInstanceRequest(pij2));
		} catch (Exception e) {}
		
		PollJSON pj2 = new PollJSON();
		pj2.question = "This is a new question: 2";
		try {
			apiClient.sendRequest(new CreatePollRequest(pj2));
		} catch (Exception e) {}
		
		PollJSON pj3 = new PollJSON();
		pj3.question = "This is a new question: 3";
		try {
			apiClient.sendRequest(new CreatePollRequest(pj3));
		} catch (Exception e) {}
	}
}
