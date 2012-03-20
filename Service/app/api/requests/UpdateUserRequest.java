package api.requests;

import api.entities.UserJSON;
import api.responses.Response;
import api.responses.UpdateUserResponse;

public class UpdateUserRequest extends Request {
	public UserJSON user;
	public UpdateUserRequest (UserJSON u) {
		this.user = u;
	}
	
	@Override
	public String getURL() {
		return "/user/" + user.id;
	}

	@Override
	public Class<? extends Response> getExpectedResponseClass() {
		return UpdateUserResponse.class;
	}

	@Override
	public Method getHttpMethod() {
		return Method.PUT;
	}

}
