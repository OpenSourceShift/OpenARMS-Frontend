package api.requests;

import api.entities.UserJSON;
import api.responses.ReadUserDetailsResponse;
import api.responses.Response;

/**
 * A request for the service: 
 */
public class ReadUserDetailsRequest extends Request {
	public Long user_id;
	public ReadUserDetailsRequest (Long l) {
		this.user_id = l;
	}
	
	@Override
	public String getURL() {
		return "/user/" + user_id + "/details";
	}

	@Override
	public Class<? extends Response> getExpectedResponseClass() {
		return ReadUserDetailsResponse.class;
	}

	@Override
	public Method getHttpMethod() {
		return Method.GET;
	}
}
