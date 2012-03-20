package controllers;
import java.text.DecimalFormat;
import java.util.Arrays;

import play.mvc.Controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonParseException;

public class JoinPoll extends Controller {
	public static void index(String id) throws JsonParseException {
		/*
		if (request.url.contains("joinpoll")) {
			redirect("/" + id);
		}
		try {
			GetPollResponse response = (GetPollResponse) APIClient.send(new GetPollRequest(id));
			String token = response.token;
			long pollid = response.id;
			String question = response.question;
			JsonArray answersArray = response.answersArray;
			String duration = response.duration;
			render(id, token, pollid, question, answersArray, duration);
		} catch (Exception e) {
			// It failed
			// TODO: Tell the user!
			nopoll(id);
		}
		*/
	}

	public static void submit(String token, String questionID, String answer) throws JsonParseException {
		/*
		validation.required(token);
		validation.match(token, "^\\d+$");
		validation.required(questionID);
		validation.match(questionID, "^\\d+$");
		validation.required(answer);

		if (validation.hasErrors()) {
			params.flash();
			validation.keep();
			index(token);
			return;
		}

		//Vote v = new Vote();
		//v.token = Integer.parseInt(token);
		//v.questionID = Integer.parseInt(questionID);
		//v.answers = new String[] { answer };
		//v.rensponderID = request.remoteAddress + session.getId();
		
		//RestClient.getInstance().vote(v);
		//APIClient.getInstance().send(new api.Request.vote(v));
		success(token);
		*/
	}

	public static void success(String token) {
		/*
		String question = null;
		JsonArray answersArray = null;
		String duration = null;

		try {
			GetPollResponse response = (GetPollResponse) APIClient.getInstance().send(new GetPollRequest(token));
			question = response.question;
			answersArray = response.answersArray;
			duration = response.duration;
			
		} catch (Exception e) {
			try {
				// Most like the poll has been inactivated, so we need the results instead
				GetResultsResponse response = (GetResultsResponse) APIClient.getInstance().send(new GetResultsRequest(token, null));
				question = response.question;
				answersArray = response.answersArray;
				duration = "0";
			} catch (Exception e2) {
			}
		}
		
		String durationString = "00:00";
		// Parse the duration and turn it into minutes and seconds
		int dur = Integer.parseInt(duration);
		int m = (int) Math.floor(dur / 60);
		int s = dur - m * 60;

		// Add leading zeros and make the string.
		char[] zeros = new char[2];
		Arrays.fill(zeros, '0');
		DecimalFormat df = new DecimalFormat(String.valueOf(zeros));

		durationString = df.format(m) + ":" + df.format(s);

		render(token, question, answersArray, duration, durationString);
		*/
	}

	public static void nopoll(String token) {
		render(token);
	}
}
