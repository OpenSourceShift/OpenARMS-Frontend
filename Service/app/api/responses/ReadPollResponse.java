package api.responses;

import models.Poll;
import models.PollInstance;
import api.entities.PollInstanceJSON;
import api.entities.PollJSON;

public class ReadPollResponse extends Response {
	public PollJSON poll;
	public ReadPollResponse() {
	}
	public ReadPollResponse(Poll p) {
		this(p.toJson());
	}
	public ReadPollResponse(PollJSON json) {
		this.poll = json;
	}
}