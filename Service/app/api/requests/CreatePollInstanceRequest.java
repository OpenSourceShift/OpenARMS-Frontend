package api.requests;

import models.PollInstance;
import api.entities.PollInstanceJSON;
import api.responses.CreatePollInstanceResponse;

public class CreatePollInstanceRequest extends Request {
	 
	public static final Class EXPECTED_RESPONSE = CreatePollInstanceResponse.class;
	public PollInstance pollInstance;
	public CreatePollInstanceRequest(PollInstanceJSON p) {
	this.pollInstance.fromJson(p);
	}	
	
	@Override
	public String getURL() {
		// TODO Auto-generated method stub
		return null;
	}

}
