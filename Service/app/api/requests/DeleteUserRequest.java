package api.requests;
import api.helpers.GsonSkip;
import api.responses.EmptyResponse;
import api.responses.Response;

/**
 * A request for the service: Deletes a user
 */
public class DeleteUserRequest extends Request {

	@GsonSkip
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
		return EmptyResponse.class;
	}
	@Override
	public Method getHttpMethod() {
		return Method.DELETE;
	}
}
