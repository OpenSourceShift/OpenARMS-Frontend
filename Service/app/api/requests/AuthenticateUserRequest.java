package api.requests;

import models.User;
import api.entities.UserJSON;
import api.responses.AuthenticateUserResponse;

public class AuthenticateUserRequest extends Request {
	public static final Class EXPECTED_RESPONSE = AuthenticateUserResponse.class;
	public UserJSON user;
	public AuthenticateUserRequest (UserJSON u) {
		this.user = u;
	}
	
	public AuthenticateUserRequest () {
	}
		
	@Override
	public String getURL() {
		return "/user/authenticate";
	}
	
}
