package api.requests;

import api.entities.VoteJSON;

public class UpdateVoteRequest extends Request {
	public static final Class EXPECTED_RESPONSE = UpdateVoteResponse.class;
	public VoteJSON vote;
	public UpdateVoteRequest (VoteJSON v) {
		this.vote = v;
	}
	
	@Override
	public String getURL() {
		return "/vote/" + vote.id;
	}

}
