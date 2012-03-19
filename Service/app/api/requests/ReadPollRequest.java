package api.requests;

import api.entities.PollJSON;

public class ReadPollRequest extends Request {
	public static final Class EXPECTED_RESPONSE = ReadPollResponse.class;
	public PollJSON poll;
	public ReadPollRequest (PollJSON p) {
		this.poll = p;
	}

	@Override
	public String getURL() {
		return "/poll/" + poll.id;
	}
}
