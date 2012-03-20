package api.responses;

import api.entities.PollInstanceJSON;

public class CreatePollInstanceResponse extends Response {
	public PollInstanceJSON pollinstance;
	public CreatePollInstanceResponse() {
	}

	public CreatePollInstanceResponse(PollInstanceJSON json) {
		this.pollinstance = json;
	}
}
