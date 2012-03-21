package api.requests;

import api.entities.UserJSON;
import api.requests.Request.Method;
import api.responses.CreateUserResponse;
import api.responses.Response;

/**
 * A request for the service: Creates a user
 */
public class CreateUserRequest extends Request {
	public UserJSON user;
	public CreateUserRequest (UserJSON u) {
		this.user = u;
	}
	
	@Override
	public String getURL() {
		return "/user";
	}

	@Override
	public Class<? extends Response> getExpectedResponseClass() {
		return CreateUserResponse.class;
	}

	@Override
	public Method getHttpMethod() {
		return Method.POST;
	}
}
