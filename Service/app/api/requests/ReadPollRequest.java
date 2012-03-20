package api.requests;

import api.entities.PollJSON;
import api.responses.ReadPollResponse;
import api.responses.Response;

/**
 * A request for the service: 
 */
public class ReadPollRequest extends Request {
	public Long id;
	public ReadPollRequest (Long id) {
		this.id = id;
	}

	@Override
	public String getURL() {
		return "/poll/" + id;
	}

	@Override
	public Class<? extends Response> getExpectedResponseClass() {
		return ReadPollResponse.class;
	}

	@Override
	public Method getHttpMethod() {
		return Method.GET;
	}
}
