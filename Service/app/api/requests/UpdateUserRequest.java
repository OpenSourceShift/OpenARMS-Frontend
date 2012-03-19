package api.requests;

import api.entities.UserJSON;
import api.responses.UpdateUserResponse;

public class UpdateUserRequest extends Request {
	public static final Class EXPECTED_RESPONSE = UpdateUserResponse.class;
	public UserJSON user;
	public UpdateUserRequest (UserJSON u) {
		this.user = u;
	}
	
	@Override
	public String getURL() {
		return "/user/" + user.id;
	}

}
