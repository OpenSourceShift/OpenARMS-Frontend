package api.requests;

import api.responses.GenerateAuthChallengeResponse;
import api.responses.Response;

/**
 * A request for the service: 
 */
public class GenerateAuthChallengeRequest extends Request {
	public String backend;
	
	public GenerateAuthChallengeRequest() {
	}
	@Override
	public String getURL() {
		return "/user/authenticate";
	}
	@Override
	public Class<? extends Response> getExpectedResponseClass() {
		return GenerateAuthChallengeResponse.class;
	}
	@Override
	public Method getHttpMethod() {
		return Method.GET;
	}
	
}
