package controllers;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import play.mvc.Controller;
import api.requests.ReadPollInstanceByTokenRequest;
import api.requests.ReadPollInstanceRequest;
import api.requests.ReadPollRequest;
import api.responses.ReadPollInstanceResponse;
import api.responses.ReadPollResponse;
import api.entities.ChoiceJSON;
import com.google.gson.JsonArray;
import com.google.gson.JsonParseException;

public class JoinPoll extends Controller {
	public static void index(String token) {
		try {
			// get the Poll Instance Data
			ReadPollInstanceResponse instanceResponse = (ReadPollInstanceResponse) APIClient.send(new ReadPollInstanceByTokenRequest(token));
			Long poll_id = instanceResponse.pollinstance.poll_id;
			Long instanceId = instanceResponse.pollinstance.id;
			Date startDateTime = instanceResponse.pollinstance.startDateTime;
			Date endDateTime = instanceResponse.pollinstance.endDateTime;
			
			// get the Poll Data
			ReadPollResponse pollResponse = (ReadPollResponse) APIClient.send(new ReadPollRequest(poll_id));
			String pollReference = pollResponse.poll.reference;
			Long pollUserId = pollResponse.poll.admin;
			Boolean pollMultipleAllowed = pollResponse.poll.multipleAllowed;
			String pollQuestion = pollResponse.poll.question;
			List<ChoiceJSON> pollChoices = pollResponse.poll.choices;
			
			// render it, poll data + poll instance data !?
			render(token, poll_id, instanceId, startDateTime, endDateTime, pollReference, pollUserId, pollMultipleAllowed, pollQuestion, pollChoices);
			
		} catch (Exception e)
		{
			// TODO: tell the user it failed
		}
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
