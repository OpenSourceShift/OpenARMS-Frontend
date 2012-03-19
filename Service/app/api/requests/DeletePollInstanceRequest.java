package api.requests;

import api.entities.PollInstanceJSON;
import api.requests.Request.Method;
import api.responses.DeletePollInstanceResponse;

public class DeletePollInstanceRequest extends Request {
	public static final Class EXPECTED_RESPONSE = DeletePollInstanceResponse.class;

	public PollInstanceJSON pollInstance;
	
	public DeletePollInstanceRequest (PollInstanceJSON p) {
		this.method = Method.DELETE;
		this.pollInstance = p;
	}
	
	@Override
	public String getURL() {
		return "/pollinstance/" + pollInstance.id;
	}
}
