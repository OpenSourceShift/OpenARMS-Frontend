package api.responses;

import api.entities.UserJSON;

public class ReadUserResponse extends Response {
	public UserJSON user;
	public ReadUserResponse() {
	}

	public ReadUserResponse(UserJSON json) {
		this.user = json;
	}
}
