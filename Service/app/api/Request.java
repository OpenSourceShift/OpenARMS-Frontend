package api;

import java.util.List;

import api.entities.ChoiceJSON;
import api.entities.PollJSON;

import models.Choice;
import models.Poll;
import models.Vote;
import models.helpers.GsonSkip;

public class Request {
	/* Poll specific communication */
	public static class CreatePollRequest extends Request {
		public PollJSON poll;
		public CreatePollRequest(Poll p) {
			this.poll = new PollJSON(p);
			// Reset any id ...
			this.poll.id = null;
			for(ChoiceJSON choice: this.poll.choices) {
				choice.id = null;
			}
		}
	}
	
	/* Choice specific communication */
	public static class CreateChoiceRequest extends Request {
		public ChoiceJSON choice;
		public CreateChoiceRequest(Choice c) {
			this.choice = new ChoiceJSON(c);
		}
	}
	
	/* Requests that are deprecated in the API */
	public static class CheckAdminkey extends Request {
		public String token;
		public String adminkey;
		public CheckAdminkey(String t, String ak) {
			this.token = t;
			this.adminkey = ak;
		}
	}
	
	public static class getQuestion extends Request {
		public String token;
		public getQuestion(String t) {
			this.token = t;
		}
	}
	
	public static class activatePoll extends Request {
		public String token;
		public String adminkey;
		public int duration;
		public activatePoll(String t, String ak, int d)
		{
			this.token = t;
			this.adminkey = ak;
			this.duration = d;
		}
	}
	
	public static class vote extends Request {
		public Vote vote;
		public vote(Vote v) {
			this.vote = v;
		}
	}
	
	public static class getResults extends Request {
		public String token;
		public String adminkey;
		public getResults (String t, String ak) {
			this.token = t;
			this.adminkey = ak;
		}
	}
	
	public void render() {
		
	}
}
