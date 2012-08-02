package api.requests;

import api.entities.UserJSON;
import api.responses.AuthenticateUserResponse;
import api.responses.EmptyResponse;
import api.responses.Response;

/**
 * A request for the service: Authenticate against an authentication backend.
 */
public class SimpleAuthenticateUserRequest extends AuthenticateUserRequest {
	public String password;
	public SimpleAuthenticateUserRequest(String email, String backend) {
		super(email, backend);
	}
}
