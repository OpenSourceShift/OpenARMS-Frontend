package api.requests;

import api.entities.PollJSON;
import api.responses.ReadPollResponse;
import api.responses.Response;

public class ReadPollByTokenRequest extends Request {
	public String token;
	public ReadPollByTokenRequest (String token) {
		this.token = token;
	}

	@Override
	public String getURL() {
		return "/poll/token/" + token;
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
