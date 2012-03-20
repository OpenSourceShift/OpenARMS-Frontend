package api.requests;

import api.entities.PollInstanceJSON;
import api.responses.ReadPollInstanceResponse;
import api.responses.Response;

/**
 * A request for the service: 
 */
public class ReadPollInstanceRequest extends Request {
	public Long pollInstance_id;
	public ReadPollInstanceRequest (Long l) {
		this.pollInstance_id = l;
	}
	
	@Override
	public String getURL() {
		return "/pollinstance/" + pollInstance_id;
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
