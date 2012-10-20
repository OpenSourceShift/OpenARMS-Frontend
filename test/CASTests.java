import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Test;

import play.Logger;
import play.mvc.Http.Request;
import play.mvc.Router;
import play.mvc.Router.Route;

import controllers.APIClient;

import api.requests.AuthenticateUserRequest;
import api.requests.GenerateAuthChallengeRequest;
import api.requests.GenerateCASAuthChallengeRequest;
import api.responses.GenerateCASAuthChallengeResponse;


public class CASTests extends BaseTest {

	@Test
	public void fetchChallenge() throws MalformedURLException {
		GenerateCASAuthChallengeRequest req = new GenerateCASAuthChallengeRequest();
		req.service = new URL(Router.getFullUrl("Application.index"));
		GenerateCASAuthChallengeResponse res = (GenerateCASAuthChallengeResponse) APIClient.send(req);
		assertTrue(res.success());
		
	}
}
