package api.requests;

import api.entities.UserJSON;


public class DeleteUserRequest extends Request {
	public static final Class EXPECTED_RESPONSE = DeleteUserResponse.class;
	public UserJSON user;
	public DeleteUserRequest (UserJSON u) {
		this.user = u;
	}
	@Override
	public String getURL() {
		// TODO Auto-generated method stub
		return "/user/" + user.id;
	}
}
