package api.requests;

import api.entities.PollInstanceJSON;

public class ClosePollInstanceRequest extends Request {
	public static final Class EXPECTED_RESPONSE = ClosePollInstanceResponse.class;
	public PollInstanceJSON pollInstance;
	public ClosePollInstanceRequest (PollInstanceJSON p) {
		this.pollInstance = p;
	}
	@Override
	public String getURL() {
		return "/pollinstance/" + pollInstance.id + "/close";
	}
}
