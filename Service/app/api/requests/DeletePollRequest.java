package api.requests;

import api.responses.EmptyResponse;
import api.responses.Response;

/**
 * A request for the service: Deletes a poll
 */
public class DeletePollRequest extends Request {
	
	public Long poll_id;
	public DeletePollRequest (Long l) {
		this.poll_id = l;
	}
	
	@Override
	public String getURL() {
		return "/poll/" + poll_id;
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
