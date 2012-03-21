package api.requests;

import api.responses.ListAuthBackendsResponse;
import api.responses.Response;

/**
 * A request for the service: 
 */
public class ListAuthBackendsRequest extends Request {
	public static final Class EXPECTED_RESPONSE = ListAuthBackendsResponse.class;
	
	public ListAuthBackendsRequest() {
	}
	@Override
	public String getURL() {
		return "/user/authenticate";
	}
	@Override
	public Class<? extends Response> getExpectedResponseClass() {
		return ListAuthBackendsResponse.class;
	}
	@Override
	public Method getHttpMethod() {
		return Method.GET;
	}
}
