package controllers.authentication;

import java.net.MalformedURLException;
import java.net.URL;

import play.Logger;
import play.libs.Crypto;
import play.mvc.Router;
import play.mvc.Http.StatusCode;
import play.mvc.Router.Route;
import api.requests.AuthenticateUserRequest;
import api.requests.CASAuthenticateUserRequest;
import api.requests.GenerateCASAuthChallengeRequest;
import api.responses.AuthenticateUserResponse;
import api.responses.GenerateCASAuthChallengeResponse;
import controllers.APIClient;
import controllers.Application;
import controllers.BaseController;

public class CASAuthentication extends BaseAuthentication {

	public static void authenticate(String ticket) {
		URL serviceUrl = null;
		try {
			String service = Router.getFullUrl("authentication.CASAuthentication.authenticate");
			serviceUrl = new URL(service);
		} catch (MalformedURLException e) {
			error(new RuntimeException("Couldn't convert the service to a URL.", e));
		}
		
		if(ticket == null || ticket.isEmpty()) {
			GenerateCASAuthChallengeRequest request = new GenerateCASAuthChallengeRequest();
			request.service = serviceUrl;
			GenerateCASAuthChallengeResponse response = (GenerateCASAuthChallengeResponse) APIClient.send(request);
			redirect(response.redirect.toExternalForm());
		} else {
			CASAuthenticateUserRequest request = new CASAuthenticateUserRequest(ticket);
			request.service = serviceUrl;
			AuthenticateUserResponse authenticateResponse = (AuthenticateUserResponse) APIClient.send(request, false);
			if(StatusCode.success(authenticateResponse.statusCode) && authenticateResponse.user != null && authenticateResponse.user.id != null && authenticateResponse.user.secret != null) {
				session.put("user_id", authenticateResponse.user.id);
				session.put("user_secret", Crypto.encryptAES(authenticateResponse.user.secret));
				Application.index();
			} else {
				APIClient.deauthenticate();
			}
		}
	}
}
