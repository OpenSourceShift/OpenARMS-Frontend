package api.requests;

import api.entities.PollJSON;

public class UpdatePollRequest extends Request {
	public static final Class EXPECTED_RESPONSE = UpdatePollResponse.class;
	public PollJSON poll;
	public UpdatePollRequest (PollJSON p) {
		this.poll = p;
	}
	@Override
	public String getURL() {
		return "/poll/" + poll.id;
	}
	
}
