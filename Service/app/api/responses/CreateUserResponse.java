package api.responses;

import api.entities.UserJSON;

public class CreateUserResponse extends Response {
	public UserJSON user;
	public CreateUserResponse() {
	}

	public CreateUserResponse(UserJSON json) {
		this.user = json;
	}
}
