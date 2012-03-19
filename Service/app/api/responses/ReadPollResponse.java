package api.responses;


import com.google.gson.JsonArray;

public class ReadPollResponse extends Response {
	public String question;
	public JsonArray answersArray;
	public String duration;
	public long id;
	public String token;
	
	public ReadPollResponse(String q, JsonArray a, String d, long i, String t)
	{
		this.question = q;
		this.answersArray = a;
		this.duration = d;
		this.id = i;
		this.token = t;
	}
}