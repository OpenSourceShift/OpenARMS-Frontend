package api.requests;

import api.entities.VoteJSON;
import api.responses.ReadVoteResponse;
import api.responses.Response;

/**
 * A request for the service: 
 */
public class ReadVoteRequest extends Request {
	public Long vote_id;
	public ReadVoteRequest (Long l) {
		this.vote_id = l;
	}
	
	@Override
	public String getURL() {
		return "/vote/" + vote_id;
	}

	@Override
	public Class<? extends Response> getExpectedResponseClass() {
		return ReadVoteResponse.class;
	}

	@Override
	public Method getHttpMethod() {
		return Method.GET;
	}

}
