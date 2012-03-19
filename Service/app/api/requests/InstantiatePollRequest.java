package api.requests;

import models.PollInstance;
import api.entities.PollInstanceJSON;
import api.responses.CreatePollInstanceResponse;

public class InstantiatePollRequest extends Request {
	public static final Class EXPECTED_RESPONSE = CreatePollInstanceResponse.class;
	public PollInstanceJSON pollInstance;
	public InstantiatePollRequest(PollInstance p) {
		this.pollInstance = p.toJson();
	}
	@Override
	public String getURL() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
