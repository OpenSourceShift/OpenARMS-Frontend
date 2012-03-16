package api;

import com.google.gson.JsonArray;

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
	}

	public static class CreateChoiceResponse extends Response {
		public ChoiceJSON choice;
		public CreateChoiceResponse(Choice c) {
			this.choice = c.toJson();
		}
	}
	
	public static class CheckAdminkeyResponse extends Response {
		public boolean bool;
		public CheckAdminkeyResponse(boolean b)
		{
			this.bool = b;
		}
	}
	
	public static class JoinPollResponse extends Response {
		public String question;
		public JsonArray answersArray;
		public String duration;
		
		public JoinPollResponse(String q, JsonArray a, String d)
		{
			this.question = q;
			this.answersArray = a;
			this.duration = d;
		}
	}
	
	public static class GetResultsResponse extends Response {
		public String question;
		public JsonArray answersArray;
		
		public GetResultsResponse(String q, JsonArray a)
		{
			this.question = q;
			this.answersArray = a;
		}
	}
	
	public static class GetPollResponse extends Response {
		public String question;
		public JsonArray answersArray;
		public String duration;
		public long id;
		public String token;
		
		public GetPollResponse(String q, JsonArray a, String d, long i, String t)
		{
			this.question = q;
			this.answersArray = a;
			this.duration = d;
			this.id = i;
			this.token = t;
		}
	}
	
	public String error_message = null;
	public Response() {
		
	}
	public Response(String error_message) {
		this.error_message = error_message;
	}
}
