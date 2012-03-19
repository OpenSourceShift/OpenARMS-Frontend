package api.responses;

import api.entities.VoteJSON;


public class ReadVoteResponse extends Response {
	public VoteJSON vote;
	public ReadVoteResponse() {
	}

	public ReadVoteResponse(VoteJSON json) {
		this.vote = json;
	}
}
