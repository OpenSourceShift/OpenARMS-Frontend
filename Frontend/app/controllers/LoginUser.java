package controllers;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import api.entities.ChoiceJSON;
import api.entities.UserJSON;
import api.helpers.GsonHelper;
import api.requests.AuthenticateUserRequest;
import api.requests.CreatePollRequest;
import api.requests.DeauthenticateUserRequest;
import api.responses.AuthenticateUserResponse;
import api.responses.CreatePollResponse;
import api.responses.EmptyResponse;
import play.Logger;
import play.i18n.Messages;
import play.libs.Crypto;
import play.mvc.Controller;
import play.mvc.Util;

public class LoginUser extends BaseController {
	//public static String forward = "";
	public static String pollToken = "";
	
	public static void showform(String email) {
		if (email == null)
			email = "";
		render(email);
	}
	
	public static void logout() {
	    try {
	    	EmptyResponse response = (EmptyResponse)APIClient.send(new DeauthenticateUserRequest());
	    	session.put("user_id", null);
	    	session.put("user_secret", null);
	    } catch (Exception e) {
	    	params.flash();
	    	validation.addError(null, e.getMessage());
	    	validation.keep();
	    }
    	Application.index();
	}
	
	public static void submit(String email, String password) {
		// Validate that the question and answers are there.
		validation.required(email);
		validation.required(password);

		// If we have an error, go to loginform.
		if (validation.hasErrors()) {
			params.flash();
			validation.keep();
			showform(email);
		} else {
			try {
				boolean success = APIClient.authenticateSimple(email, password);
				if (success) {
					// Go to the page.
					String redirectTo = session.get("page_prior_to_login");
					if(redirectTo == null) {
						Application.index();
					} else {
						redirect(redirectTo);
					}
				} else {
					// It failed!
					flash.error(Messages.get("unexpected_behavior"));
					params.flash();
					validation.keep();
					showform(email);
				}
			} catch (Exception e) {
				// It failed!
				flash.error(e.getMessage().toString());
				params.flash();
				validation.keep();
				showform(email);
			}
		}
	}
	
	@Util
	public static Long getCurrentUserId() {
		return APIClient.getCurrentUserId();
	}

	@Util
	public static boolean isLoggedIn() {
		return APIClient.isLoggedIn();
	}
}
