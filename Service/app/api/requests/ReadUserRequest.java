package api.requests;

import api.entities.UserJSON;

public class ReadUserRequest extends Request {
	public static final Class EXPECTED_RESPONSE = ReadUserResponse.class;
	public UserJSON user;
	public ReadUserRequest (UserJSON u) {
		this.user = u;
	}
	
	@Override
	public String getURL() {
		return "/user/" + user.id;
	}
}
