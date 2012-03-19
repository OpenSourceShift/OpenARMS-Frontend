package api.requests;
import api.entities.UserJSON;
import api.requests.Request.Method;
import api.responses.DeleteUserResponse;
import api.responses.Response;

public class DeleteUserRequest extends Request {
	public UserJSON user;
	public DeleteUserRequest (UserJSON u) {
		this.user = u;
	}
	@Override
	public String getURL() {
		return "/user/" + user.id;
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
