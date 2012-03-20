package api.requests;

import api.entities.PollInstanceJSON;
import api.requests.Request.Method;
import api.responses.DeletePollInstanceResponse;
import api.responses.Response;

public class DeletePollInstanceRequest extends Request {

	public Long pollInstance_id;
	
	public DeletePollInstanceRequest (Long l) {
		this.pollInstance_id = l;
	}
	
	@Override
	public String getURL() {
		return "/pollinstance/" + pollInstance_id;
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
