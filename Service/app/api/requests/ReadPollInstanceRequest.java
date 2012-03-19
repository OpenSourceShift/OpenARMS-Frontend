package api.requests;

import api.entities.PollInstanceJSON;
import api.responses.ReadPollInstanceResponse;
import api.responses.Response;

public class ReadPollInstanceRequest extends Request {
	public static final Class EXPECTED_RESPONSE = ReadPollInstanceResponse.class;
	public PollInstanceJSON pollInstance;
	public ReadPollInstanceRequest (PollInstanceJSON p) {
		this.pollInstance = p;
	}
	
	@Override
	public String getURL() {
		return "/pollinstance/" + pollInstance.id;
	}

	@Override
	public Class<? extends Response> getExpectedResponseClass() {
		return ReadPollInstanceResponse.class;
	}

	@Override
	public Method getHttpMethod() {
		return Method.GET;
	}
	
}
