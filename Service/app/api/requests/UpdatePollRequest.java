package api.requests;

import api.entities.PollJSON;
import api.responses.Response;
import api.responses.UpdatePollResponse;

/**
 * A request for the service: 
 */
public class UpdatePollRequest extends Request {
	public PollJSON poll;
	public UpdatePollRequest (PollJSON p) {
		this.poll = p;
	}
	@Override
	public String getURL() {
		return "/poll/" + poll.id;
	}
	@Override
	public Class<? extends Response> getExpectedResponseClass() {
		return UpdatePollResponse.class;
	}
	@Override
	public Method getHttpMethod() {
		return Method.PUT;
	}
}