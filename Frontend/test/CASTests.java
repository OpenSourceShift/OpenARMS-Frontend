import org.junit.Test;

import controllers.APIClient;

import api.requests.AuthenticateUserRequest;
import api.requests.GenerateAuthChallengeRequest;
import api.requests.GenerateCASAuthChallengeRequest;
import api.responses.GenerateCASAuthChallengeResponse;


public class CASTests extends BaseTest {

	@Test
	public void fetchChallenge() {
		GenerateCASAuthChallengeRequest req = new GenerateCASAuthChallengeRequest();
		GenerateCASAuthChallengeResponse res = (GenerateCASAuthChallengeResponse) APIClient.send(req);
		assertTrue(res.success());
		
	}
}
