package api.requests;

import api.entities.UserJSON;
import api.responses.ReadUserResponse;
import api.responses.Response;

/**
 * A request for the service: 
 */
public class ReadUserRequest extends Request {
	public Long user_id;
	public ReadUserRequest (Long l) {
		this.user_id = l;
	}
	
	@Override
	public String getURL() {
		return "/user/" + user_id;
	}

	@Override
	public Class<? extends Response> getExpectedResponseClass() {
		return ReadUserResponse.class;
	}

	@Override
	public Method getHttpMethod() {
		return Method.GET;
	}
}
