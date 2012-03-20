package api.requests;

import api.entities.VoteJSON;
import api.responses.Response;
import api.responses.VoteOnPollInstanceResponse;

/**
 * A request for the service: 
 */
public class VoteOnPollInstanceRequest extends Request {
	public VoteJSON vote;
	public VoteOnPollInstanceRequest (VoteJSON v) {
		this.vote = v;
	}
	
	@Override
	public String getURL() {
		return "/pollinstance/" + vote.pollInstanceid + "/vote";
	}

	@Override
	public Class<? extends Response> getExpectedResponseClass() {
		return VoteOnPollInstanceResponse.class;
	}

	@Override
	public Method getHttpMethod() {
		return Method.POST;
	}

}
