package api.requests;

import api.entities.UserJSON;
import api.requests.Request.Method;
import api.responses.CreateUserResponse;

public class CreateUserRequest extends Request {
	public static final Class EXPECTED_RESPONSE = CreateUserResponse.class;
	public UserJSON user;
	public CreateUserRequest (UserJSON u) {
		this.method = Method.POST;
		this.user = u;
	}
	
	@Override
	public String getURL() {
		return "/user";
	}
}
