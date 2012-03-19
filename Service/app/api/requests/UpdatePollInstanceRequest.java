package api.requests;

import api.entities.PollInstanceJSON;
import api.responses.UpdatePollInstanceResponse;

public class UpdatePollInstanceRequest extends Request {
	public static final Class EXPECTED_RESPONSE = UpdatePollInstanceResponse.class;
	public PollInstanceJSON pollInstance;
	public UpdatePollInstanceRequest (PollInstanceJSON p) {
		this.method = Method.PUT;
		this.pollInstance = p;
	}
	
	
	@Override
	public String getURL() {
		return "/pollinstance/" + pollInstance.id;
	}

}
