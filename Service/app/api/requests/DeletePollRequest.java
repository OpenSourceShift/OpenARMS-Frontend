package api.requests;

import api.entities.PollJSON;

public class DeletePollRequest extends Request {

	public static final Class EXPECTED_RESPONSE = DeletePollResponse.class;
	
	public PollJSON poll;
	public DeletePollRequest (PollJSON p) {
		this.poll = p;
	}
	
	@Override
	public String getURL() {
		return "/poll/" + poll.id;
	}
}
