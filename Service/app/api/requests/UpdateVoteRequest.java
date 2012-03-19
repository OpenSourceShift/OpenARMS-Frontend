package api.requests;

import api.entities.VoteJSON;
import api.responses.UpdateVoteResponse;

public class UpdateVoteRequest extends Request {
	public static final Class EXPECTED_RESPONSE = UpdateVoteResponse.class;
	public VoteJSON vote;
	public UpdateVoteRequest (VoteJSON v) {
		this.method = Method.PUT;
		this.vote = v;
	}
	
	@Override
	public String getURL() {
		return "/vote/" + vote.id;
	}

}
