package api;

import java.util.List;

import api.Response.CreatePollResponse;
import api.entities.ChoiceJSON;
import api.entities.PollJSON;
import api.helpers.GsonSkip;

import models.Choice;
import models.Poll;
import models.Vote;

public abstract class Request {
	public static enum Method {
		GET,
		POST,
		PUT,
		DELETE
	}

	@GsonSkip(classes={Request.class})
	public static final Class EXPECTED_RESPONSE = null;
	@GsonSkip(classes={Request.class})
	public Method method;
	
	/* Poll specific communication */
	public static class CreatePollRequest extends Request {
		public static final Class EXPECTED_RESPONSE = CreatePollResponse.class;
		
		public PollJSON poll;
		public CreatePollRequest(PollJSON p) {
			// Reset any id ...
			this.poll.id = null;
			for(ChoiceJSON choice: this.poll.choices) {
				choice.id = null;
			}
		}
	}
	
	public static class GetPollRequest extends Request {
		public Long id;
		public String token;
		public GetPollRequest(Long i) {
			this.id = i;
		}
		
		public GetPollRequest(String t) {
			this.token = t;
		}
	}
	
	/* Choice specific communication */
	public static class CreateChoiceRequest extends Request {
		public ChoiceJSON choice;
		public CreateChoiceRequest(Choice c) {
			this.choice = c.toJson();
		}
	}
	
	/* Requests that are deprecated in the API */
	public static class CheckAdminkeyRequest extends Request {
		public String token;
		public String adminkey;
		public CheckAdminkeyRequest(String t, String ak) {
			this.token = t;
			this.adminkey = ak;
		}
	}
	
	public static class ActivatePollRequest extends Request {
		public String token;
		public String adminkey;
		public int duration;
		public ActivatePollRequest(String t, String ak, int d)	{
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
	
	public static class GetResultsRequest extends Request {
		public String token;
		public String adminkey;
		public GetResultsRequest (String t, String ak) {
			this.token = t;
			this.adminkey = ak;
		}
	}
	
	public void render() {
	}
}
