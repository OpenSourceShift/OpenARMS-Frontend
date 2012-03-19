package api.responses;

import api.entities.PollJSON;

public class UpdatePollResponse extends Response {
	public PollJSON poll;
	public UpdatePollResponse() {
	}

	public UpdatePollResponse(PollJSON json) {
		this.poll = json;
	}
}