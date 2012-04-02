package controllers;
import play.mvc.Controller;
import play.mvc.Http;

import java.util.HashMap;
import java.util.regex.*;


import api.entities.UserJSON;
import api.requests.CreateUserRequest;
import api.responses.CreateUserResponse;

import com.google.gson.JsonParseException;

public class RegisterUser extends BaseController {
	public static void getdata(String name, String email, String passw, String confpassw) throws Exception {
		boolean changed = false;
		if (!checkname(name)) {
			validation.addError("nameError", "Invalid user name");
		}
		
		if(!checkpassw(passw, confpassw)){
			validation.addError("passwDontMatch", "Passwords doesn't match");
			changed = true;
		}
		if(!checkemail(email)){
			validation.addError("email", "Wrong email");
			changed = true;
		}
		if(!validatePass(passw)){
			validation.addError("validatePass", "Password is short");
			changed = true;
		}

		if(!changed){
			UserJSON uj = new UserJSON();
			uj.name = name;
			uj.email = email;
			uj.attributes = new HashMap<String, String>();
			uj.attributes.put("password", confpassw);
			CreateUserResponse response = (CreateUserResponse)APIClient.send(new CreateUserRequest(uj, "controllers.SimpleAuthBackend"));
			if  (response.statusCode == Http.StatusCode.CREATED)
				success();
			else {
				validation.addError("emailTaken", "This email is already taken");
				validation.keep();
				showform();
			}
		} else {
			validation.keep();
			showform();
		}
	}
	// TODO: proper name check, maybe against service?
	private static boolean checkname(String name) {
		if (name.length() < 3)
			return false;
		return true;
	}
	public static void showform(){
		render();
	}
	public static void success(){
		render();
	}

	//does passwords match?
	// TODO: proper password checks
	public static boolean checkpassw(String passw, String confpassw){
		return passw.equals(confpassw);
	}
	//check wrong symbols and length
	public static boolean validatePass(String passw){
		int length = passw.length();
		return (length > 4);
	}
	// TODO: proper email validation check, against the service also?
	public static boolean checkemail(String email) {
		Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
		Matcher m = p.matcher(email);
		boolean result = m.matches();
		return result;
	}
}
