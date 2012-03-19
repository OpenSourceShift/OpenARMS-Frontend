package api.requests;


public class DeleteUserRequest extends Request {
	public static final Class EXPECTED_RESPONSE = DeleteUserResponse.class;
	public UserJSON user;
	/* ? */
}
