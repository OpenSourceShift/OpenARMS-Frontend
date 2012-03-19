package api.responses;

import api.entities.PollInstanceJSON;

public class ReadPollInstanceResponse extends Response {
	public PollInstanceJSON pollinstance;
	public ReadPollInstanceResponse() {
	}

	public ReadPollInstanceResponse(PollInstanceJSON json) {
		this.pollinstance = json;
	}
}
