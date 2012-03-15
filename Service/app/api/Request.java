package api;

import models.Poll;

public class Request {
	public static class CreatePollRequest extends Request {
		public Poll poll;
		public CreatePollRequest(Poll p) {
			this.poll = p;
		}
	}
	public void render() {
		
	}
}
