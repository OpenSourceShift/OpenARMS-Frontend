package api.requests;

import api.responses.EmptyResponse;
import api.responses.Response;

/**
 * A request for the service: Deletes a poll instance
 */
public class DeletePollInstanceRequest extends Request {

	public Long pollInstance_id;
	
	public DeletePollInstanceRequest (Long l) {
		this.pollInstance_id = l;
	}
	
	@Override
	public String getURL() {
		return "/pollinstance/" + pollInstance_id;
	}

	@Override
	public Class<? extends Response> getExpectedResponseClass() {
		return EmptyResponse.class;
	}

	@Override
	public Method getHttpMethod() {
		return Method.DELETE;
	}
}
