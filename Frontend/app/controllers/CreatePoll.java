package controllers;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import play.mvc.Controller;
import play.mvc.Http;
import api.entities.ChoiceJSON;
import api.entities.PollInstanceJSON;
import api.entities.PollJSON;
import api.helpers.GsonHelper;
import api.requests.CreatePollInstanceRequest;
import api.requests.CreatePollRequest;
import api.responses.CreatePollInstanceResponse;
import api.responses.CreatePollResponse;

public class CreatePoll extends BaseController {

	public static void index(String question, String[] choices) {
		if (!LoginUser.isLoggedIn()) {
			session.put("page_prior_to_login", request.url);
			LoginUser.index(null);
		}
		PollJSON poll = new PollJSON();
		if(question == null) {
			poll.question = "";
		} else {
			poll.question = question;
		}
		poll.choices = new LinkedList<ChoiceJSON>();
		if (choices != null) {
			for(String c: choices) {
				ChoiceJSON choiceJson = new ChoiceJSON();
				choiceJson.text = c;
				poll.choices.add(choiceJson);
			}
		} else {
			// Default
			ChoiceJSON c1 = new ChoiceJSON();
			c1.text = "";
			poll.choices.add(c1);
			ChoiceJSON c2 = new ChoiceJSON();
			c2.text = "";
			poll.choices.add(c2);
		}
		// Default values.
		poll.loginRequired = false;
		poll.multipleAllowed = true;
		/*
		List<ChoiceJSON> choices_json = new LinkedList<ChoiceJSON>();
		for(String c: choices) {
			ChoiceJSON cJSON = new ChoiceJSON();
			cJSON.text = c;
			choices_json.add(cJSON);
		}
		String pollJson = GsonHelper.toJson(poll);
		render(poll, pollJson);
		*/
		String choicesJson = GsonHelper.toJson(poll.choices);
		render(poll, choicesJson);
	}

	public static void success(String token) {
		render(token);
	}

	public static void submit(String question, String[] choices, String type, Boolean loginRequired) {
		if (!LoginUser.isLoggedIn()) {
			session.put("page_prior_to_login", request.url);
			LoginUser.index(null);
		}
				
		// Remove empty lines from the answers
		List<ChoiceJSON> choicesJson = new LinkedList<ChoiceJSON>();
		if (choices != null) {
			for (String s: choices) {
				if (s != null && !s.isEmpty()) {
					ChoiceJSON choice = new ChoiceJSON();
					choice.text = s;
					choicesJson.add(choice);
				}
			}
		}

		// Validate that the question and answers are there.
		validation.required(question);
		validation.required(type);

		// Validate that we have at least 2 answer options.
		if (choicesJson.size() < 2) {
			validation.addError("choices", "validation.required.atLeastTwo", choices);
		}

		// If we have an error, go to newpollform.
		if (validation.hasErrors()) {
			params.flash();
			validation.keep();
			index(question, choices);
			return;
		}
		
		// TODO: Check if the email provided belongs to an existing user
		// If yes: Redirect the user to the login page - save the poll somewhere temporarily.
		// If no: Create a new user and create the poll, and bind these two together.

		// Finally, we're ready to send it to the server, and see if it likes it or not.
		PollJSON p = new PollJSON();
		p.question = question;
		p.choices = choicesJson;
		if (loginRequired == null)
			loginRequired = false;
		p.loginRequired = loginRequired;
		if (type.equals("multiple")) {
			p.multipleAllowed = true;
		}

		try {
			CreatePollResponse response = (CreatePollResponse) APIClient.send(new CreatePollRequest(p));
			PollJSON poll = response.poll;
			if(poll != null) {
				PollInstanceJSON pi = new PollInstanceJSON();
				pi.poll_id = response.poll.id;
				pi.start = new Date();
				pi.end = new Date();
				pi.end.setMinutes(pi.start.getMinutes() + 30);
				
				CreatePollInstanceResponse piresp = (CreatePollInstanceResponse) APIClient.send(new CreatePollInstanceRequest(pi));
				if (piresp.statusCode == Http.StatusCode.CREATED)
					success(poll.token);
				else
					notFound("Something went wrong during Poll creation! :-(");
			} else {
				notFound("Something went wrong during Poll creation! :-(");
			}
	
			// Redirect to success
		} catch (Exception e) {
			// It failed!
			// TODO: Tell the user!
			//e.printStackTrace();
			params.flash();
			validation.addError(null, e.getMessage());
			validation.keep();
			index(question, choices);
		}
	}
}
