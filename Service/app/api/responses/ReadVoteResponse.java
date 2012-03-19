package api.responses;

import models.Poll;
import models.PollInstance;
import models.Vote;
import api.entities.PollInstanceJSON;
import api.entities.PollJSON;
import api.entities.VoteJSON;


public class ReadVoteResponse extends Response {
	public VoteJSON vote;
	public ReadVoteResponse() {
	}
	public ReadVoteResponse(Vote v) {
		this(v.toJson());
	}
	public ReadVoteResponse(VoteJSON json) {
		this.vote = json;
	}
}
