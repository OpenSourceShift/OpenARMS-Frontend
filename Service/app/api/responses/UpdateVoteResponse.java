package api.responses;

import api.entities.VoteJSON;


public class UpdateVoteResponse extends Response {
	public VoteJSON vote;
	public UpdateVoteResponse() {
	}

	public UpdateVoteResponse(VoteJSON json) {
		this.vote = json;
	}
}
