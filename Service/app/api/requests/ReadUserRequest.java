package api.requests;

import api.entities.UserJSON;
import api.responses.ReadUserResponse;
import api.responses.Response;

public class ReadUserRequest extends Request {
	public UserJSON user;
	public ReadUserRequest (UserJSON u) {
		this.user = u;
	}
	
	@Override
	public String getURL() {
		return "/user/" + user.id;
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
