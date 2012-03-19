package api.requests;

import api.entities.PollInstanceJSON;

public class UpdatePollInstanceRequest extends Request {
	public static final Class EXPECTED_RESPONSE = UpdatePollInstanceResponse.class;
	public PollInstanceJSON pollInstance;
	public UpdatePollInstanceRequest (PollInstanceJSON p) {
		this.pollInstance = p;
	}
	
	
	@Override
	public String getURL() {
		return "/pollinstance/" + pollInstance.id;
	}

}
