package api.requests;

import api.entities.PollJSON;
import api.requests.Request.Method;
import api.responses.DeletePollResponse;
import api.responses.Response;

public class DeletePollRequest extends Request {
	
	public PollJSON poll;
	public DeletePollRequest (PollJSON p) {
		this.poll = p;
	}
	
	@Override
	public String getURL() {
		return "/poll/" + poll.id;
	}

	@Override
	public Class<? extends Response> getExpectedResponseClass() {
		return DeletePollResponse.class;
	}

	@Override
	public Method getHttpMethod() {
		return Method.DELETE;
	}
}
