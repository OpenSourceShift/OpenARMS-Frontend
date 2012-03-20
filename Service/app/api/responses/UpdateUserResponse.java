package api.responses;

import api.entities.UserJSON;

public class UpdateUserResponse extends Response {
	public UserJSON user;
	public UpdateUserResponse() {
	}

	public UpdateUserResponse(UserJSON json) {
		this.user = json;
	}
}
