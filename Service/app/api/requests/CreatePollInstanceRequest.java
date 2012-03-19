package api.requests;

import api.entities.PollInstanceJSON;
import api.requests.Request.Method;
import api.responses.CreatePollInstanceResponse;

public class CreatePollInstanceRequest extends Request {
	 
	public static final Class EXPECTED_RESPONSE = CreatePollInstanceResponse.class;
	public PollInstanceJSON pollInstance;
	public CreatePollInstanceRequest(PollInstanceJSON p) {
		this.method = Method.POST;
		this.pollInstance = p;
	}
	
	@Override
	public String getURL() {
		return "/pollinstance";
	}

}
