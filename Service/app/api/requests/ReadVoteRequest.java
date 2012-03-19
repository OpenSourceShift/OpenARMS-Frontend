package api.requests;

import api.entities.VoteJSON;

public class ReadVoteRequest extends Request {
	public static final Class EXPECTED_RESPONSE = ReadVoteResponse.class;
	public VoteJSON vote;
	public ReadVoteRequest (VoteJSON v) {
		this.vote = v;
	}
	
	@Override
	public String getURL() {
		return "/vote/" + vote.id;
	}

}
