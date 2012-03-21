package controllers;
import java.util.Date;

import play.Logger;
import play.mvc.Controller;
import play.mvc.Http.StatusCode;
import api.entities.PollJSON;
import api.entities.VoteJSON;
import api.requests.CreateVoteRequest;
import api.requests.ReadPollInstanceByTokenRequest;
import api.requests.ReadPollRequest;
import api.responses.ReadPollInstanceResponse;
import api.responses.ReadPollResponse;

public class JoinPoll extends Controller {
	public static void index(String token) throws Exception {
		Logger.debug("Initial token is %s", token);
		if(token == null) {
			notFound("No poll without a token.");
		}
		
		/** get the Poll Instance Data */
		ReadPollInstanceResponse res = (ReadPollInstanceResponse) APIClient.send(new ReadPollInstanceByTokenRequest(token));
		if(res.statusCode.equals(StatusCode.NOT_FOUND)) {
			notFound("Poll not found.");
		} else if (StatusCode.error(res.statusCode)) {
			notFound("Error finding the poll.");
		}
		
		Long poll_id = res.pollinstance.poll_id;
		// Long instanceId = instanceResponse.pollinstance.id;
		// Date startDateTime = instanceResponse.pollinstance.startDateTime;
		Date end = res.pollinstance.end;

		// TODO: Consider if this is even nessesary to get the poll, as choices are allready defined in the instance response (as VoteSummaryJSONs).
		ReadPollResponse pollResponse = (ReadPollResponse) APIClient.send(new ReadPollRequest(poll_id));
		if(pollResponse.statusCode.equals(StatusCode.NOT_FOUND)) {
			notFound("Poll not found.");
		} else if (StatusCode.error(pollResponse.statusCode)) {
			notFound("Error finding the poll.");
		}
		
		PollJSON poll = pollResponse.poll;
		render(poll);
	}

	public static void submit(Long instanceId, Long[] votes) throws Exception {
		if (votes != null) {
			for (Long l : votes) {
				VoteJSON vote = new VoteJSON();
				vote.choiceid = l;
				vote.pollInstanceid = instanceId;
				APIClient.getInstance().send(new CreateVoteRequest(vote));
			}
		}
		else {
			throw new Exception("no answers submitted");
		}
		
		/* TODO: validation
		validation.required(stuff);
		if (validation.hasErrors()) {
			params.flash();
			validation.keep();
			index(token);
			return;
		} */
		
		success(instanceId);
	}

	public static void success(Long instanceId) {
		// TODO: what should happen? just show the statistics of the instance?
		
		/* String question = null;
		JsonArray answersArray = null;
		String duration = null;

		try {
			ReadPollInstanceResponse response = (ReadPollInstanceResponse) APIClient.getInstance().send(new ReadPollInstanceRequest(instanceId));
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

		render(token, question, answersArray, duration, durationString);
		
	}

	public static void nopoll(String token) {
		render(token); */
	}
}
