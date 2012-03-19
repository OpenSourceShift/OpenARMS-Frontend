package api.requests;

import api.entities.PollJSON;
import api.requests.Request.Method;
import api.responses.ClonePollResponse;

public class ClonePollRequest extends Request {
	public static final Class EXPECTED_RESPONSE = ClonePollResponse.class;
	public PollJSON poll;
	public ClonePollRequest (PollJSON p) {
		this.method = Method.POST;
		this.poll = p;
	}
		
	@Override
	public String getURL() {
		return "/poll/" + poll.id + "/clone";
	}
	
	
	
}
