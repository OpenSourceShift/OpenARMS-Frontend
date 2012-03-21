package api.requests;

import api.entities.PollInstanceJSON;
import api.requests.Request.Method;
import api.responses.ClosePollInstanceResponse;
import api.responses.Response;

/**
 * A request for the service: Closes the poll instance by setting its end to now.
 */
public class ClosePollInstanceRequest extends Request {
	public PollInstanceJSON pollInstance;
	public ClosePollInstanceRequest (PollInstanceJSON p) {
		this.pollInstance = p;
	}
	@Override
	public String getURL() {
		return "/pollinstance/" + pollInstance.id + "/close";
	}
	@Override
	public Class<? extends Response> getExpectedResponseClass() {
		return ClosePollInstanceResponse.class;
	}
	@Override
	public Method getHttpMethod() {
		return Method.POST;
	}
}
