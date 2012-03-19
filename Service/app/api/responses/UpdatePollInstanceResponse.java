package api.responses;

import api.entities.PollInstanceJSON;

public class UpdatePollInstanceResponse extends Response {
	public PollInstanceJSON pollinstance;
	public UpdatePollInstanceResponse() {
	}

	public UpdatePollInstanceResponse(PollInstanceJSON json) {
		this.pollinstance = json;
	}
}
