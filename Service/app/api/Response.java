package api;

import api.entities.ChoiceJSON;
import models.Choice;
import models.Poll;

public class Response {
	public static class CreatePollResponse extends Response {
		public Poll poll;
		public CreatePollResponse(Poll p) {
			this.poll = p;
		}
	}

	public static class CreateChoiceResponse extends Response {
		public ChoiceJSON choice;
		public CreateChoiceResponse(Choice c) {
			this.choice = new ChoiceJSON(c);
		}
	}
	
	public String error_message = null;
	public Response() {
		
	}
	public Response(String error_message) {
		this.error_message = error_message;
	}
}
