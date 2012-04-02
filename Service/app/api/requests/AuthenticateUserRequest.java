package api.requests;

import api.entities.UserJSON;
import api.responses.AuthenticateUserResponse;
import api.responses.EmptyResponse;
import api.responses.Response;

/**
 * A request for the service: Authenticate against an authentication backend.
 */
public class AuthenticateUserRequest extends Request {
	public UserJSON user;
	public String backend;
	public AuthenticateUserRequest (UserJSON u, String backend) {
		this.user = u;
		this.backend = backend;
	}
	
	@Override
	public String getURL() {
		return "/user/authenticate";
	}

	@Override
	public Method getHttpMethod() {
		return Method.POST;
	}

	@Override
	public Class<? extends Response> getExpectedResponseClass() {
		return AuthenticateUserResponse.class;
	}
	
}
