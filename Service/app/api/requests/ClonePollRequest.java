package api.requests;

import api.entities.PollJSON;
import api.requests.Request.Method;
import api.responses.ClonePollResponse;
import api.responses.Response;

/**
 * A request for the service: Do a clone of a poll, i.e. clone all properties of the poll, except the votes.
 */
public class ClonePollRequest extends Request {
	public String pollId;
	public ClonePollRequest (String s) {
		this.pollId = s;
	}
		
	@Override
	public String getURL() {
		return "/poll/" + pollId + "/clone";
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
