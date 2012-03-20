package api.requests;

import api.entities.PollInstanceJSON;
import api.requests.Request.Method;
import api.responses.CreateChoiceResponse;
import api.responses.CreatePollInstanceResponse;
import api.responses.Response;

public class CreatePollInstanceRequest extends Request {
	 
	public PollInstanceJSON pollInstance;
	public CreatePollInstanceRequest(PollInstanceJSON p) {
		this.pollInstance = p;
	}
	
	@Override
	public String getURL() {
		return "/pollinstance";
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
