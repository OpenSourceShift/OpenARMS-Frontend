package api.requests;

import api.entities.PollJSON;
import api.requests.Request.Method;
import api.responses.ClonePollResponse;
import api.responses.Response;

public class ClonePollRequest extends Request {
	public PollJSON poll;
	public ClonePollRequest (PollJSON p) {
		this.poll = p;
	}
		
	@Override
	public String getURL() {
		return "/poll/" + poll.id + "/clone";
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
