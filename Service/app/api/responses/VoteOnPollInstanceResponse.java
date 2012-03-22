package api.responses;

import api.entities.PollJSON;



public class VoteOnPollInstanceResponse extends CreateVoteResponse {
	public PollJSON poll;
	public VoteOnPollInstanceResponse() {
	}

	public VoteOnPollInstanceResponse(PollJSON json) {
		this.poll = json;
	}
}
