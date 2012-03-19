package api.requests;

import api.entities.PollInstanceJSON;
import api.responses.ReadPollInstanceResponse;

public class ReadPollInstanceRequest extends Request {
	public static final Class EXPECTED_RESPONSE = ReadPollInstanceResponse.class;
	public PollInstanceJSON pollInstance;
	public ReadPollInstanceRequest (PollInstanceJSON p) {
		this.method = Method.GET;
		this.pollInstance = p;
	}
	
	@Override
	public String getURL() {
		return "/pollinstance/" + pollInstance.id;
	}
	
}
