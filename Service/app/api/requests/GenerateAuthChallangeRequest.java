package api.requests;

import api.responses.GenerateAuthChallangeResponse;
import api.responses.Response;

public class GenerateAuthChallangeRequest extends Request {
	public String backend;
	
	public GenerateAuthChallangeRequest() {
	}
	@Override
	public String getURL() {
		return "/user/authenticate";
	}
	@Override
	public Class<? extends Response> getExpectedResponseClass() {
		return GenerateAuthChallangeResponse.class;
	}
	@Override
	public Method getHttpMethod() {
		return Method.GET;
	}
	
}
