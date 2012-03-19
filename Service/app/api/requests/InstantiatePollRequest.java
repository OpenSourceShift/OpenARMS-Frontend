package api.requests;

import api.entities.PollInstanceJSON;
import api.responses.CreatePollInstanceResponse;

public class InstantiatePollRequest extends Request {
	public static final Class EXPECTED_RESPONSE = CreatePollInstanceResponse.class;
	public PollInstanceJSON pollInstance;
	public InstantiatePollRequest(PollInstanceJSON p) {
		this.method = Method.POST;
		this.pollInstance = p;
	}
	@Override
	public String getURL() {
		return "/poll/" + pollInstance.id +"/instantiate";
	}
	
}
