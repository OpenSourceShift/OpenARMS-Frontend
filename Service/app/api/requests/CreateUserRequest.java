package api.requests;

import api.entities.UserJSON;

public class CreateUserRequest extends Request {
	public static final Class EXPECTED_RESPONSE = CreateUserResponse.class;
	public UserJSON user;
	public CreateUserRequest (UserJSON u) {
		this.user = u;
	}
	
	@Override
	public String getURL() {
		return "/user";
	}
}
