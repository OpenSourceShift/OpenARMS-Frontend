package api.requests;

import api.entities.PollJSON;
import api.responses.ClonePollResponse;

public class ClonePollRequest extends Request {
	public static final Class EXPECTED_RESPONSE = ClonePollResponse.class;
	public PollJSON poll;
	public ClonePollRequest (PollJSON p) {
		this.poll = p;
	}
	
	public ClonePollRequest () {
	}
		
	@Override
	public String getURL() {
		return "/poll/" + poll.id + "/clone";
	}
	
	
	
}
