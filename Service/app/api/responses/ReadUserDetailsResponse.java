package api.responses;

import java.util.List;

import api.entities.PollInstanceJSON;
import api.entities.PollJSON;
import api.entities.UserJSON;

public class ReadUserDetailsResponse extends Response {
	public UserJSON user;
	public List<PollJSON> polls;

	public ReadUserDetailsResponse() {
	}

	public ReadUserDetailsResponse(UserJSON json, List<PollJSON> polls) {
		this.user = json;
		this.polls = polls;
	}
}
