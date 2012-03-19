package api.requests;

import api.entities.PollJSON;
import api.responses.UpdatePollResponse;

public class UpdatePollRequest extends Request {
	public static final Class EXPECTED_RESPONSE = UpdatePollResponse.class;
	public PollJSON poll;
	public UpdatePollRequest (PollJSON p) {
		this.method = Method.PUT;
		this.poll = p;
	}
	@Override
	public String getURL() {
		return "/poll/" + poll.id;
	}
}