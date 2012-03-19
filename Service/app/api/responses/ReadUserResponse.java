package api.responses;

import api.entities.PollJSON;
import api.entities.UserJSON;
import models.Poll;
import models.User;

public class ReadUserResponse extends Response {
	public UserJSON user;
	public ReadUserResponse() {
	}

	public ReadUserResponse(UserJSON json) {
		this.user = json;
	}
}
