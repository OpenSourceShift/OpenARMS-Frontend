package api.requests;

import api.entities.PollJSON;
import api.responses.ReadPollResponse;

public class ReadPollRequest extends Request {
	public static final Class EXPECTED_RESPONSE = ReadPollResponse.class;
	public PollJSON poll;
	public ReadPollRequest (PollJSON p) {
		this.method = Method.GET;
		this.poll = p;
	}

	@Override
	public String getURL() {
		return "/poll/" + poll.id;
	}
}
