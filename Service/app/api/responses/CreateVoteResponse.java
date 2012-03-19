package api.responses;

import models.Vote;
import api.entities.VoteJSON;


public class CreateVoteResponse extends Response {
	public VoteJSON vote;
	public CreateVoteResponse (Vote v) {
		this.vote = v.toJson();
	}
}
