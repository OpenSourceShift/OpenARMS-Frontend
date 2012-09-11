package api.requests;

import api.responses.GenerateAuthChallengeResponse;
import api.responses.Response;

/**
 * A request for the service: 
 */
public class GenerateSimpleAuthChallengeRequest extends GenerateAuthChallengeRequest {

	public GenerateSimpleAuthChallengeRequest() {
		super(SimpleAuthenticateUserRequest.BACKEND);
	}
}
