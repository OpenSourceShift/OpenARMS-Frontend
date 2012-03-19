package api.requests;

import api.entities.UserJSON;
import api.responses.AuthenticateUserResponse;

public abstract class AuthenticateUserRequest extends Request {
	public static final Class EXPECTED_RESPONSE = AuthenticateUserResponse.class;
	public UserJSON user;
	public String backend;
	public AuthenticateUserRequest (UserJSON u, String backend) {
		this.method = Method.POST;
		this.user = u;
		this.backend = backend;
	}
		
	@Override
	public String getURL() {
		return "/user/authenticate";
	}
	
}
