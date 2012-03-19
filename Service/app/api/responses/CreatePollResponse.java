package api.responses;

import models.Poll;
import api.entities.PollJSON;

public class CreatePollResponse extends Response {
	public PollJSON poll;
	public CreatePollResponse(Poll p) {
		this.poll = p.toJson();
	}
}