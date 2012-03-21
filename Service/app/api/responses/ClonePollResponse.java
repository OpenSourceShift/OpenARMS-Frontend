package api.responses;

import api.entities.PollJSON;
import api.entities.PollJSON;

public class ClonePollResponse extends CreatePollResponse {
	public PollJSON poll;
	public ClonePollResponse() {
	}

	public ClonePollResponse(PollJSON json) {
		this.poll = json;
	}
}