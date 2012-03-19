package api.requests;

public class ReadPollInstanceRequest extends Request {
	public Long id;
	public ReadPollInstanceRequest (long i) {
		this.id = i;
	}
	@Override
	public String getURL() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
