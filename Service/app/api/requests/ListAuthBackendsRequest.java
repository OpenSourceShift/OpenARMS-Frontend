package api.requests;

import api.responses.ListAuthBackendsResponse;

public class ListAuthBackendsRequest extends Request {
	public static final Class EXPECTED_RESPONSE = ListAuthBackendsResponse.class;
	
	public ListAuthBackendsRequest() {
		this.method = Method.GET;
	}
	@Override
	public String getURL() {
		return "/user/authenticate";
	}
}
