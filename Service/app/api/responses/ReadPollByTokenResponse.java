package api.responses;

import api.entities.PollJSON;

public class ReadPollByTokenResponse extends Response {
	public PollJSON poll;
	public ReadPollByTokenResponse() {
	}

	public ReadPollByTokenResponse(PollJSON json) {
		this.poll = json;
	}
}
