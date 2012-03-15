package api;

import models.Poll;

public class Response {
	public static class CreateUserRequest extends Response {
		public Poll poll;
		public CreateUserRequest(Poll p) {
			this.poll = p;
		}
	}
	public void render() {
		
	}
	public String error_message = null;
	public Response() {
		
	}
	public Response(String error_message) {
		this.error_message = error_message;
	}
}
