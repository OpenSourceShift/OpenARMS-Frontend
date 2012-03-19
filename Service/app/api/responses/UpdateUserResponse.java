package api.responses;

import api.entities.PollJSON;
import api.entities.UserJSON;
import models.Poll;
import models.User;

public class UpdateUserResponse extends Response {
	public UserJSON user;
	public UpdateUserResponse() {
	}
	public UpdateUserResponse(User u) {
		this(u.toJson());
	}
	public UpdateUserResponse(UserJSON json) {
		this.user = json;
	}
}
