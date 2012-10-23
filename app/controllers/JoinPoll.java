package controllers;
import play.Logger;
import play.mvc.Http;
import play.mvc.Http.StatusCode;
import api.entities.PollInstanceJSON;
import api.entities.PollJSON;
import api.entities.VoteJSON;
import api.requests.CreateVoteRequest;
import api.requests.ReadPollInstanceByTokenRequest;
import api.requests.ReadPollInstanceRequest;
import api.requests.ReadPollRequest;
import api.responses.CreateVoteResponse;
import api.responses.ReadPollInstanceResponse;
import api.responses.ReadPollResponse;
import controllers.authentication.BaseAuthenticationFrontend;

public class JoinPoll extends BaseController {
	public static void index(String token) throws Exception {
		Logger.debug("Initial token is %s", token);
		if(token == null) {
			notFound("No poll without a token.");
		}
		
		// get the Poll Instance Data
		ReadPollInstanceResponse res = (ReadPollInstanceResponse) APIClient.send(new ReadPollInstanceByTokenRequest(token.toUpperCase()));
		if(res.statusCode.equals(StatusCode.NOT_FOUND)) {
			notFound("Poll not found.");
		} else if (StatusCode.error(res.statusCode)) {
			notFound("Error finding the poll.");
		}
		
		PollInstanceJSON pollInstance = res.pollinstance;
		
		// TODO: Consider if this is even nessesary to get the poll, as choices are allready defined in the instance response (as VoteSummaryJSONs).
		ReadPollResponse pollResponse = (ReadPollResponse) APIClient.send(new ReadPollRequest(pollInstance.poll_id));
		if(pollResponse.statusCode.equals(StatusCode.NOT_FOUND)) {
			notFound("Poll not found.");
		} else if (StatusCode.error(pollResponse.statusCode)) {
			notFound("Error finding the poll.");
		}
		
		PollJSON poll = pollResponse.poll;
		// Check if login is required for the poll
		if (poll.loginRequired != null && poll.loginRequired) {
			if (!APIClient.isLoggedIn()) {
				session.put("page_prior_to_login", request.url);
				BaseAuthenticationFrontend.showform(null);
			}
		}
		
		render(poll, pollInstance);
	}

	public static void submit(String token, Long pollinstance_id, Long poll_id, Long[] answers) throws Exception {
		validation.required("answers", answers);
		validation.required("pollinstance_id", pollinstance_id);
		if(validation.hasErrors()) {
			// Show the user ...
			validation.keep();
			index(token);
		} else {
			for (Long l : answers) {
				VoteJSON vote = new VoteJSON();
				vote.choiceid = l;
				vote.pollInstanceid = pollinstance_id;
				CreateVoteResponse response = (CreateVoteResponse) APIClient.getInstance().send(new CreateVoteRequest(vote));
				if (response.statusCode != Http.StatusCode.CREATED) {
					// FIXME: Proper error page here?
					forbidden("You have already voted once for this poll!");
					break;
				}
			}
			success(pollinstance_id);
		}
	}

	public static void success(Long pollInstanceId) throws Exception {
		/** get the Poll Instance Data */
		ReadPollInstanceResponse response1 = (ReadPollInstanceResponse) APIClient.send(new ReadPollInstanceRequest(pollInstanceId));
		PollInstanceJSON pollInstance = response1.pollinstance;
		render(pollInstance);
	}
}
