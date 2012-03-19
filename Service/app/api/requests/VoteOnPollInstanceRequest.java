package api.requests;

import api.entities.VoteJSON;

public class VoteOnPollInstanceRequest extends Request {
	public static final Class EXPECTED_RESPONSE = VoteOnPollInstanceResponse.class;
	public VoteJSON vote;
	public VoteOnPollInstanceRequest (VoteJSON v) {
		this.vote = v;
	}
	
	@Override
	public String getURL() {
		return "/pollinstance/" + vote.pollInstanceid + "/vote";
	}

}
