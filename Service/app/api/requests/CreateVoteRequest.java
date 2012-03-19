package api.requests;
import models.Vote;
import api.entities.VoteJSON;
import api.responses.CreateVoteResponse;

public class CreateVoteRequest extends Request {
	public static final Class EXPECTED_RESPONSE = CreateVoteResponse.class;
	public Vote vote;
	public CreateVoteRequest(VoteJSON v)
	{
		this.vote.fromJson(v);
	}
	
	
	@Override
	public String getURL() {
		// TODO Auto-generated method stub
		return null;
	}
}