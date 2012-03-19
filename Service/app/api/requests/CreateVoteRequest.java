package api.requests;
import api.entities.VoteJSON;
import api.requests.Request.Method;
import api.responses.CreateVoteResponse;

public class CreateVoteRequest extends Request {
	public static final Class EXPECTED_RESPONSE = CreateVoteResponse.class;
	public VoteJSON vote;
	public CreateVoteRequest(VoteJSON v) {
		this.method = Method.POST;
		this.vote = v;
	}
	
	@Override
	public String getURL() {
		return "/vote";
	}
}