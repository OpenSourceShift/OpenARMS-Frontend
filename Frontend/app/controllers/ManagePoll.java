package controllers;
import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.mysql.jdbc.Messages;
import com.sun.imageio.plugins.common.I18N;

import play.Logger;
import play.i18n.Lang;
import play.mvc.Controller;
import play.mvc.Http.StatusCode;
import play.mvc.results.RenderJson;
import api.entities.ChoiceJSON;
import api.entities.PollInstanceJSON;
import api.entities.PollJSON;
import api.entities.VoteJSON;
import api.requests.CreatePollInstanceRequest;
import api.requests.CreatePollRequest;
import api.requests.ReadPollByTokenRequest;
import api.requests.ReadPollInstanceRequest;
import api.requests.ReadPollRequest;
import api.requests.ReadUserDetailsRequest;
import api.requests.UpdatePollInstanceRequest;
import api.requests.UpdatePollRequest;
import api.requests.VoteOnPollInstanceRequest;
import api.responses.CreatePollInstanceResponse;
import api.responses.CreatePollResponse;
import api.responses.ReadPollInstanceResponse;
import api.responses.ReadPollResponse;
import api.responses.ReadPollByTokenResponse;
import api.responses.ReadUserDetailsResponse;
import api.responses.Response;
import api.responses.UpdatePollResponse;
import api.responses.VoteOnPollInstanceResponse;

public class ManagePoll extends BaseController {
	public static void index() {
		APIClient apiClient = new APIClient();
		/** get all polls + instances for the current user */
    	try {
    		Long userId = controllers.LoginUser.getCurrentUserId();
    		if(userId == null) {
        		LoginUser.showform(null);
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

	public static void activateForm(Long id) {
		ReadPollResponse pollResponse = (ReadPollResponse) APIClient.send(new ReadPollRequest(id));
		PollJSON poll = pollResponse.poll;

		render(poll);
	}

	public static void activate(Long id, Long duration) {
		validation.required(id);
		validation.required(duration);
		validation.min(duration, 1);
		
		if (!validation.hasErrors()) {
			ReadPollResponse pollResponse = (ReadPollResponse) APIClient.send(new ReadPollRequest(id));
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
			activateForm(id);
		}
	}
	
	public static void clone(Long id) {
		CreatePoll.index(id);
	}

	public static void close(Long id) {
		ReadPollInstanceResponse response1 = (ReadPollInstanceResponse) APIClient.send(new ReadPollInstanceRequest(id));
		response1.pollinstance.end = response1.currentDate;
		APIClient.send(new UpdatePollInstanceRequest(response1.pollinstance));
		
		flash.success("managepoll.close.success");
		flash.keep();
		index();
	}

	public static void statistics(Long id, Boolean showQRCode) {
		ReadPollInstanceResponse res = (ReadPollInstanceResponse) APIClient.send(new ReadPollInstanceRequest(id));
		PollInstanceJSON pollInstance = res.pollinstance;
		render(pollInstance, showQRCode);
	}

	public static void testStatistics() {
		renderText("{\"pollID\":518196,\"questionID\":127,\"question\":\"Question?\",\"answers\":[\"Answer 1\",\"Answer 2\", \"Answer 3\"],\"votes\":[50, 10, 40]}");
	}
}
