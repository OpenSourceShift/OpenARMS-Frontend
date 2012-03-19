package api.requests;

import api.entities.VoteJSON;

public class DeleteVoteRequest extends Request {
	public static final Class EXPECTED_RESPONSE = DeleteVoteResponse.class;
	public VoteJSON vote;
	public DeleteVoteRequest (VoteJSON v) {
		this.vote = v;
	}
	@Override
	public String getURL() {
		return "/vote/" + vote.id;
	}
}
