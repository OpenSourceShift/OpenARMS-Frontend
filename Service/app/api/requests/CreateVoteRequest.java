package api.requests;
import api.entities.VoteJSON;
import api.requests.Request.Method;
import api.responses.CreateVoteResponse;
import api.responses.Response;

/**
 * A request for the service: Creates a vote
 */
public class CreateVoteRequest extends Request {
	public VoteJSON vote;
	public CreateVoteRequest(VoteJSON v) {
		this.vote = v;
	}
	
	@Override
	public String getURL() {
		return "/vote";
	}

	@Override
	public Class<? extends Response> getExpectedResponseClass() {
		return CreateVoteResponse.class;
	}

	@Override
	public Method getHttpMethod() {
		return Method.POST;
	}
}