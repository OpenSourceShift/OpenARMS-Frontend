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
    public String backend;
	public CreateUserRequest (UserJSON u, String backend) {
		this.user = u;
		this.backend = backend;
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
