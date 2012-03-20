package api.responses;

import api.entities.VoteJSON;


public class CreateVoteResponse extends Response {
	public VoteJSON vote;
	public CreateVoteResponse() {
	}

	public CreateVoteResponse(VoteJSON json) {
		this.vote = json;
	}
}
