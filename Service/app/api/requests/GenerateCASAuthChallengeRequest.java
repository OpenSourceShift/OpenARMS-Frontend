package api.requests;

import java.net.URL;

import api.responses.GenerateCASAuthChallengeResponse;
import api.responses.Response;

public class GenerateCASAuthChallengeRequest extends GenerateAuthChallengeRequest {
	public URL service;
	
	public GenerateCASAuthChallengeRequest() {
		super(CASAuthenticateUserRequest.BACKEND);
	}

	@Override
	public Class<? extends Response> getExpectedResponseClass() {
		return GenerateCASAuthChallengeResponse.class;
	}
}
