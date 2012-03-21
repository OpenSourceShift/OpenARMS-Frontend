package api.requests;

import api.entities.PollInstanceJSON;
import api.responses.Response;
import api.responses.UpdatePollInstanceResponse;

/**
 * A request for the service: 
 */
public class UpdatePollInstanceRequest extends Request {
	public PollInstanceJSON pollInstance;
	public UpdatePollInstanceRequest (PollInstanceJSON p) {
		this.pollInstance = p;
	}
	
	
	@Override
	public String getURL() {
		return "/pollinstance/" + pollInstance.id;
	}

	@Override
	public Class<? extends Response> getExpectedResponseClass() {
		return UpdatePollInstanceResponse.class;
	}

	@Override
	public Method getHttpMethod() {
		return Method.PUT;
	}

}
