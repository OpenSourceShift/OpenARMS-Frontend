package api.requests;

public class ReadVoteRequest extends Request {

	public Long id;
	public ReadVoteRequest (long i) {
		this.id = i;
	}
	
	@Override
	public String getURL() {
		// TODO Auto-generated method stub
		return null;
	}

}
