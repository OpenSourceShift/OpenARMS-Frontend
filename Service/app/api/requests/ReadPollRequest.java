package api.requests;

import api.Request;

/*
GET /poll/123

 */



/* static? */
public class ReadPollRequest extends Request {
	public Long id;
	public String token;
	public ReadPollRequest(Long i) {
		this.id = i;
	}
	
	public ReadPollRequest(String t) {
		this.token = t;
	}

	@Override
	public String getURL() {
		// TODO Auto-generated method stub
		return null;
	}
}
