package api.requests;

import api.entities.UserJSON;

public class AuthenticateUserRequest extends Request {
	public static final Class EXPECTED_RESPONSE = AuthenticateUserResponse.class;
	public UserJSON user;
	public AuthenticateUserRequest (UserJSON u) {
		this.user = u;
	}
	
	@Override
	public String getURL() {
		return "/user/authenticate";
	}
	
}
