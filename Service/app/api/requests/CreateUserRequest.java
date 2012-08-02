package api.requests;

import java.util.Map;

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
    public Map<String, String> attributes;
	public CreateUserRequest (UserJSON u, String backend, Map<String, String> attributes) {
		this.user = u;
		this.backend = backend;
		this.attributes = attributes;
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
