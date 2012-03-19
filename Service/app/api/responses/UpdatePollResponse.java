package api.responses;

import models.Poll;
import models.PollInstance;
import api.entities.PollInstanceJSON;
import api.entities.PollJSON;

public class UpdatePollResponse extends Response {
	public PollJSON poll;
	public UpdatePollResponse() {
	}

	public UpdatePollResponse(PollJSON json) {
		this.poll = json;
	}
}