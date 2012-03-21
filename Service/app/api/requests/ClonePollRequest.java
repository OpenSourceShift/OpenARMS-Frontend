package api.requests;

import api.entities.PollJSON;
import api.requests.Request.Method;
import api.responses.ClonePollResponse;
import api.responses.Response;

/**
 * A request for the service: Do a clone of a poll, i.e. clone all properties of the poll, except the votes.
 */
public class ClonePollRequest extends Request {
	public Long poll_id;
	public ClonePollRequest (Long p) {
		this.poll_id = p;
	}
		
	@Override
	public String getURL() {
		return "/poll/" + poll_id + "/clone";
	}

	@Override
	public Class<? extends Response> getExpectedResponseClass() {
		return ClonePollResponse.class;
	}

	@Override
	public Method getHttpMethod() {
		return Method.POST;
	}
	
	
	
}
