package api.requests;
import api.entities.UserJSON;
import api.requests.Request.Method;
import api.responses.DeleteUserResponse;
import api.responses.Response;

/**
 * A request for the service: Deletes a user
 */
public class DeleteUserRequest extends Request {
	public Long user_id;
	public DeleteUserRequest (Long l) {
		this.user_id = l;
	}
	@Override
	public String getURL() {
		return "/user/" + user_id;
	}
	@Override
	public Class<? extends Response> getExpectedResponseClass() {
		return DeleteUserResponse.class;
	}
	@Override
	public Method getHttpMethod() {
		return Method.DELETE;
	}
}
