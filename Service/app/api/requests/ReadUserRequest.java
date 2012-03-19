package api.requests;

public class ReadUserRequest extends Request {
	public static final Class EXPECTED_RESPONSE = ReadUserResponse.class;
	public UserJSON user;
	/* ? */
	
	@Override
	public String getURL() {
		return "/user/" + user.id;
	}
}
