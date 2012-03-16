package api;

import api.entities.ChoiceJSON;
import api.entities.PollJSON;
import models.Choice;
import models.Poll;

public class Response {
	public static class CreatePollResponse extends Response {
		public PollJSON poll;
		public CreatePollResponse(PollJSON p) {
			this.poll = p;
		}
		public CreatePollResponse(Poll p) {
			this.poll = new PollJSON(p);
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
