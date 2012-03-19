package api.requests;

import api.responses.GenerateAuthChallangeResponse;

public class GenerateAuthChallangeRequest extends Request {
	public static final Class EXPECTED_RESPONSE = GenerateAuthChallangeResponse.class;
	public String backend;
	
	public GenerateAuthChallangeRequest() {
		this.method = Method.GET;
	}
	@Override
	public String getURL() {
		return "/user/authenticate";
	}
	
}
