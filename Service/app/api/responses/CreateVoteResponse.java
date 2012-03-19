package api.responses;

import models.Poll;
import models.PollInstance;
import models.Vote;
import api.entities.PollInstanceJSON;
import api.entities.PollJSON;
import api.entities.VoteJSON;


public class CreateVoteResponse extends Response {
	public VoteJSON vote;
	public CreateVoteResponse() {
	}

	public CreateVoteResponse(VoteJSON json) {
		this.vote = json;
	}
}
