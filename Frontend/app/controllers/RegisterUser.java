package controllers;
import play.mvc.Controller;
import play.mvc.Http;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.*;

import org.postgresql.translation.messages_bg;


import api.entities.UserJSON;
import api.requests.CreateUserRequest;
import api.responses.CreateUserResponse;

import com.google.gson.JsonParseException;

public class RegisterUser extends BaseController {
	public static void submit(String name, String email, String password, String passwordConfirmed) throws Exception {
		
		validation.required(name);
		validation.minSize(name, 4); // TODO: Change to use regular expressions so as to exclude profanity
		
		validation.email(email);
		
		validation.required(password);
		validation.minSize(password, 4); // TODO: Change to use regular expressions
		validation.required(passwordConfirmed);
		validation.equals(password, passwordConfirmed);

		if(!validation.hasErrors()){
			UserJSON uj = new UserJSON();
			uj.name = name;
			uj.email = email;
			Map<String, String> attributes = new HashMap<String, String>();
			attributes.put("password", password);
			try {
				CreateUserResponse response = (CreateUserResponse) APIClient.send(new CreateUserRequest(uj, "controllers.SimpleAuthenticationBackend", attributes));

				if (response.statusCode == Http.StatusCode.CREATED) {
					APIClient.authenticateSimple(email, password);
					Application.index();
				} else {
					throw new RuntimeException("Couldn't create user.");
				}
			} catch(RuntimeException e) {
				if(e.getCause() == null) {
					flash.error(e.getMessage());
				} else {
					flash.error(e.getCause().getMessage());
				}
				params.flash();
				validation.keep();
				showform();
			}
		} else {
			params.flash();
			validation.keep();
			showform();
		}
	}

	public static void showform() {
		render();
	}
}
