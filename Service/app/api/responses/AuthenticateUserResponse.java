package api.responses;

import api.entities.UserJSON;

public class AuthenticateUserResponse extends Response {
	public UserJSON user;
	public AuthenticateUserResponse() {
	}

	public AuthenticateUserResponse(UserJSON json) {
		this.user = json;
	}
}
