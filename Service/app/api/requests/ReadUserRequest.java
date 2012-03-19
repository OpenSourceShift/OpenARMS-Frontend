package api.requests;

public class ReadUserRequest extends Request {
	public Long id;
	public ReadUserRequest (long i) {
		this.id = i;
	}
	
	@Override
	public String getURL() {
		// TODO Auto-generated method stub
		return null;
	}
}
