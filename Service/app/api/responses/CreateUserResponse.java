package api.responses;

import api.entities.PollJSON;
import api.entities.UserJSON;
import models.Poll;
import models.User;

public class CreateUserResponse extends Response {
	public UserJSON user;
	public CreateUserResponse() {
	}
	public CreateUserResponse(User u) {
		this(u.toJson());
	}
	public CreateUserResponse(UserJSON json) {
		this.user = json;
	}
}
