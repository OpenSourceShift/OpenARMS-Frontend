package api.requests;

import api.entities.VoteJSON;
import api.requests.Request.Method;
import api.responses.DeleteVoteResponse;
import api.responses.Response;

public class DeleteVoteRequest extends Request {
	public static final Class EXPECTED_RESPONSE = DeleteVoteResponse.class;
	public Long vote_id;
	public DeleteVoteRequest (Long l) {
		this.vote_id = l;
	}
	@Override
	public String getURL() {
		return "/vote/" + vote_id;
	}
	@Override
	public Class<? extends Response> getExpectedResponseClass() {
		return DeleteVoteResponse.class;
	}
	@Override
	public Method getHttpMethod() {
		return Method.DELETE;
	}
}
