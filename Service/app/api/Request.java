package api;

import models.Poll;
import models.Vote;

public class Request {
	public static class CreatePollRequest extends Request {
		public Poll poll;
		public CreatePollRequest(Poll p) {
			this.poll = p;
		}
	}
	
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
