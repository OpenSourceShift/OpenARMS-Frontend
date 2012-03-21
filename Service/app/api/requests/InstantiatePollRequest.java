package api.requests;

import api.entities.PollInstanceJSON;
import api.responses.CreatePollInstanceResponse;
import api.responses.Response;

/**
 * A request for the service: 
 */
public class InstantiatePollRequest extends Request {
	public static final Class EXPECTED_RESPONSE = CreatePollInstanceResponse.class;
	public PollInstanceJSON pollInstance;
	public InstantiatePollRequest(PollInstanceJSON p) {
		this.pollInstance = p;
	}
	@Override
	public String getURL() {
		return "/poll/" + pollInstance.id +"/instantiate";
	}
	@Override
	public Class<? extends Response> getExpectedResponseClass() {
		return CreatePollInstanceResponse.class;
	}
	@Override
	public Method getHttpMethod() {
		return Method.POST;
	}
	
}
