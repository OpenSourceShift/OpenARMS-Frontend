package api.requests;

public class UpdateUserRequest extends Request {
	public static final Class EXPECTED_RESPONSE = UpdateUserResponse.class;
	public UserJSON user;
	/* ? */
	
	
	@Override
	public String getURL() {
		return "/user/" + user.id;
	}

}
