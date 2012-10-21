package controllers;
import java.util.Date;
import java.util.List;

import api.entities.ChoiceJSON;
import api.entities.PollInstanceJSON;
import api.entities.PollJSON;
import api.requests.CreatePollInstanceRequest;
import api.requests.ReadPollByTokenRequest;
import api.requests.ReadPollInstanceRequest;
import api.requests.ReadUserDetailsRequest;
import api.requests.UpdatePollInstanceRequest;
import api.requests.UpdatePollRequest;
import api.responses.CreatePollInstanceResponse;
import api.responses.ReadPollByTokenResponse;
import api.responses.ReadPollInstanceResponse;
import api.responses.ReadUserDetailsResponse;
import api.responses.UpdatePollResponse;
import controllers.authentication.BaseAuthenticationFrontend;

public class ManagePoll extends BaseController {
	public static void index() {
		APIClient apiClient = new APIClient();
		/** get all polls + instances for the current user */
    	try {
    		Long userId = APIClient.getCurrentUserId();
    		if(userId == null) {
    			BaseAuthenticationFrontend.showform();
    		} else {
				ReadUserDetailsResponse responseUser = (ReadUserDetailsResponse) apiClient.sendRequest(new ReadUserDetailsRequest(userId));
				List<PollJSON> pollsJson = responseUser.polls;
				render(pollsJson);
    		}
    	} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void update(String token, String reference, Boolean multipleAllowed, String question, List<ChoiceJSON> choices) {
		validation.required(reference);
		validation.required(multipleAllowed);
		validation.required(question);
		validation.required(choices);

		if (!validation.hasErrors()) {
			// TODO: error handling
			}

		PollJSON pollJson = new PollJSON();
		pollJson.choices = choices;
		pollJson.multipleAllowed = multipleAllowed;
		pollJson.question = question;
		pollJson.reference = reference;

		try {
			UpdatePollResponse response = (UpdatePollResponse) APIClient.send(new UpdatePollRequest(pollJson));
		} catch (Exception e) {
			// TODO: tell the user it failed
		}
	}

	public static void activateForm(String token) {
		ReadPollByTokenResponse pollResponse = (ReadPollByTokenResponse) APIClient.send(new ReadPollByTokenRequest(token));
		PollJSON poll = pollResponse.poll;

		render(poll);
	}

	public static void activate(String token, Long duration) {
		validation.required(token);
		validation.required(duration);
		validation.min(duration, 1);
		
		if (!validation.hasErrors()) {
			ReadPollByTokenResponse pollResponse = (ReadPollByTokenResponse) APIClient.send(new ReadPollByTokenRequest(token));
			PollJSON poll = pollResponse.poll;
			
			// TODO: this! (activate/instantiate pollinstance with start and end time
			PollInstanceJSON pi = new PollInstanceJSON();
			pi.poll_id = poll.id;
			pi.start = pollResponse.currentDate;
			pi.end = (Date) pollResponse.currentDate.clone();
			pi.end.setTime(pi.start.getTime() + duration*1000);
			
			CreatePollInstanceResponse pollInstanceResponse = (CreatePollInstanceResponse) APIClient.send(new CreatePollInstanceRequest(pi));
			PollInstanceJSON pollInstance = pollInstanceResponse.pollinstance;
			statistics(pollInstance.id, true);
		} else {
			params.flash();
			validation.keep();
			activateForm(token);
		}
	}
	
	public static void clone(String token) {
		CreatePoll.index(token);
	}

	public static void close(Long pollInstanceId) {
		ReadPollInstanceResponse response = (ReadPollInstanceResponse) APIClient.send(new ReadPollInstanceRequest(pollInstanceId));
		response.pollinstance.end = response.currentDate;
		APIClient.send(new UpdatePollInstanceRequest(response.pollinstance));
		
		flash.success("managepoll.close.success");
		flash.keep();
		index();
	}

	public static void statistics(Long pollInstanceId, Boolean showQRCode) {
		ReadPollInstanceResponse res = (ReadPollInstanceResponse) APIClient.send(new ReadPollInstanceRequest(pollInstanceId));
		PollInstanceJSON pollInstance = res.pollinstance;
		render(pollInstance, showQRCode);
	}

	public static void testStatistics() {
		renderText("{\"pollID\":518196,\"questionID\":127,\"question\":\"Question?\",\"answers\":[\"Answer 1\",\"Answer 2\", \"Answer 3\"],\"votes\":[50, 10, 40]}");
	}
}
