package api.requests;

import api.entities.VoteJSON;
import api.responses.Response;
import api.responses.UpdateVoteResponse;

/**
 * A request for the service: 
 */
public class UpdateVoteRequest extends Request {
	public VoteJSON vote;
	public UpdateVoteRequest (VoteJSON v) {
		this.vote = v;
	}
	
	@Override
	public String getURL() {
		return "/vote/" + vote.id;
	}

	@Override
	public Class<? extends Response> getExpectedResponseClass() {
		return UpdateVoteResponse.class;
	}

	@Override
	public Method getHttpMethod() {
		return Method.PUT;
	}

}
