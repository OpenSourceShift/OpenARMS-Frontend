package api.requests;

import api.entities.PollInstanceJSON;
import api.responses.ReadPollInstanceResponse;
import api.responses.Response;

/**
 * A request for the service: 
 */
public class ReadPollInstanceByTokenRequest extends Request {
	public String token;
	public ReadPollInstanceByTokenRequest (String token) {
		this.token = token;
	}
	
	@Override
	public String getURL() {
		return "/pollinstance/token/" + token;
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
