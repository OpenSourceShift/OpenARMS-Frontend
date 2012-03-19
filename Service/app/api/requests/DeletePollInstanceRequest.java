package api.requests;

import api.entities.PollInstanceJSON;
import api.requests.Request.Method;
import api.responses.DeletePollInstanceResponse;
import api.responses.Response;

public class DeletePollInstanceRequest extends Request {

	public PollInstanceJSON pollInstance;
	
	public DeletePollInstanceRequest (PollInstanceJSON p) {
		this.pollInstance = p;
	}
	
	@Override
	public String getURL() {
		return "/pollinstance/" + pollInstance.id;
	}

	@Override
	public Class<? extends Response> getExpectedResponseClass() {
		return DeletePollInstanceResponse.class;
	}

	@Override
	public Method getHttpMethod() {
		return Method.DELETE;
	}
}
