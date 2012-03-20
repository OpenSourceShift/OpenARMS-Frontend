package api.requests;

import api.entities.VoteJSON;
import api.responses.ReadVoteResponse;
import api.responses.Response;

public class ReadVoteRequest extends Request {
	public VoteJSON vote;
	public ReadVoteRequest (VoteJSON v) {
		this.vote = v;
	}
	
	@Override
	public String getURL() {
		return "/vote/" + vote.id;
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
