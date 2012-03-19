package api.requests;

import api.entities.PollJSON;
import api.responses.ReadPollResponse;
import api.responses.Response;

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

	@Override
	public Class<? extends Response> getExpectedResponseClass() {
		return ReadPollResponse.class;
	}

	@Override
	public Method getHttpMethod() {
		return Method.GET;
	}
}
