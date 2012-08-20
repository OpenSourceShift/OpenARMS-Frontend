package controllers;
import play.mvc.Controller;
import play.mvc.Http;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.*;


import api.entities.UserJSON;
import api.requests.CreateUserRequest;
import api.responses.CreateUserResponse;

import com.google.gson.JsonParseException;

public class RegisterUser extends BaseController {
	public static void submit(String name, String email, String passw, String confpassw) throws Exception {
		
		validation.minSize(name, 4);	// TODO: Change to use regular expressions so as to exclude profanity
		validation.equals(passw, confpassw);
		validation.email(email);
		validation.minSize(email, 4); 	// TODO: Change to use regular expressions

		if(!validation.hasErrors()){
			UserJSON uj = new UserJSON();
			uj.name = name;
			uj.email = email;
			Map<String, String> attributes = new HashMap<String, String>();
			attributes.put("password", passw);
			CreateUserResponse response = (CreateUserResponse)APIClient.send(new CreateUserRequest(uj, "controllers.SimpleAuthenticationBackend", attributes));
			if (response.statusCode == Http.StatusCode.CREATED) {
				APIClient.authenticateSimple(email, passw);
				Application.index();
			} else {
				validation.addError("emailTaken", "This email is already taken");
				validation.keep();
				showform();
			}
		} else {
			validation.keep();
			showform();
		}
	}

	public static void showform() {
		render();
	}
}
