package api.requests;

import org.apache.commons.lang.NotImplementedException;

import api.responses.GenerateAuthChallengeResponse;
import api.responses.Response;

/**
 * A request for the service: 
 */
public class GenerateAuthChallengeRequest extends Request {
	public String backend;
	
	public GenerateAuthChallengeRequest(String backend) {
		this.backend = backend;
	}
	
	@Override
	public String getURL() {
		return "/user/authenticate/challange";
	}
	
	@Override
	public Method getHttpMethod() {
		return Method.POST;
	}

	@Override
	public Class<? extends Response> getExpectedResponseClass() {
		throw new NotImplementedException("Cannot generate a response to an abstract class.");
	}
	
}
