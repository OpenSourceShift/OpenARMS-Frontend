package api.requests;

import api.entities.PollInstanceJSON;
import api.responses.ReadPollInstanceResponse;
import api.responses.Response;

/**
 * A request for the service: 
 */
public class ReadPollInstanceByTokenRequest extends Request {
	public String pollInstance_token;
	public ReadPollInstanceByTokenRequest (String token) {
		this.pollInstance_token = token;
	}
	
	@Override
	public String getURL() {
		return "/pollinstance/" + pollInstance_token;
	}

	@Override
	public Class<? extends Response> getExpectedResponseClass() {
		return ReadPollInstanceResponse.class;
	}

	@Override
	public Method getHttpMethod() {
		return Method.GET;
	}
	
}
