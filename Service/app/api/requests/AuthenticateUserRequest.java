package api.requests;

import api.entities.UserJSON;
import api.responses.AuthenticateUserResponse;

/**
 * A request for the service: Authenticate against an authentication backend.
 */
public abstract class AuthenticateUserRequest extends Request {
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
	
}
