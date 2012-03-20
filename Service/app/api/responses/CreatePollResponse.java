package api.responses;

import api.entities.PollJSON;

public class CreatePollResponse extends Response {
	public PollJSON poll;
	public CreatePollResponse() {
	}

	public CreatePollResponse(PollJSON json) {
		this.poll = json;
	}
}