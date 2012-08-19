import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import javax.mail.Session;

import controllers.APIClient;
import api.entities.UserJSON;
import api.requests.AuthenticateUserRequest;
import api.requests.CreateUserRequest;
import api.requests.DeleteUserRequest;
import api.responses.CreateUserResponse;
import api.responses.EmptyResponse;
import api.responses.Response;
import play.Logger;
import play.mvc.Controller;
import play.mvc.Http.StatusCode;
import play.mvc.With;
import play.test.UnitTest;

public abstract class BaseTest extends UnitTest {
	
	public static final APIClient client = new APIClient();
	public static final Random random = new Random();

	public static final String NAME = "John Doe";
	public static final String EMAIL = "test@openarms.dk";
	public static final String PASSWORD = "1234";
	public static final String BACKEND = "controllers.SimpleAuthenticationBackend";
	
	public static Long latestCreatedUserID;
	
	public static Long createUser() {
		CreateUserResponse response = createUser(EMAIL, PASSWORD);
		if(response.success()) {
			return response.user.id;
		} else {
			return null;
		}
	}
	
	public static CreateUserResponse createUser(String email, String password) {
		UserJSON user = new UserJSON();
		user.name = NAME;
		user.email = email;
		Map<String, String> attributes = new HashMap<String, String>();
		attributes.put("password", password);
		CreateUserRequest request = new CreateUserRequest(user, BACKEND, attributes);
		CreateUserResponse response = (CreateUserResponse) client.send(request);
		if(response.success()) {
			latestCreatedUserID = response.user.id;
		}
		return response;
	}
	
	public static void deleteUserIfCreated() {
		if(latestCreatedUserID != null) {
			client.authenticateSimple(EMAIL, PASSWORD);
			EmptyResponse response1 = deleteUser();
			assertTrue(response1.error_message, response1.success());
		}
	}
	
	public static EmptyResponse deleteUser(Long id) {
		DeleteUserRequest request = new DeleteUserRequest(id);
		EmptyResponse response = (EmptyResponse) client.send(request);
		return response;
	}
	
	public static EmptyResponse deleteUser() {
		if(latestCreatedUserID == null) {
			throw new RuntimeException("You have to create a user, before it can be deleted.");
		} else {
			Long id = latestCreatedUserID;
			latestCreatedUserID = null;
			return deleteUser(id);
		}
	}
	
	public static boolean authenticateUser() {
		return client.authenticateSimple(EMAIL, PASSWORD);
	}
	
	public static Long ensureAuthenticatedUser() {
		try {
			if(authenticateUser()) {
				return client.getCurrentUserId();
			} else {
				return null;
			}
		} catch(RuntimeException e) {
			if(e.getCause().getMessage().equals("No user with this email.")) {
				createUser();
				if(authenticateUser()) {
					return client.getCurrentUserId();
				} else {
					return null;
				}
			} else {
				throw e;
			}
		}
	}

	public static void ensureSuccessful(Response response) {
    	if(!response.success()) {
    		if(response.error_message == null) {
    			response.error_message = "No error message from service.";
    		}
    		fail("did not get the HTTP-OK status-code from the service, got "+response.statusCode+": "+response.error_message);
		}
	}

	public static void ensureUnSuccessful(Response response) {
    	if(response.success()) {
    		fail("did not get the an error status-code from the service, got "+response.statusCode);
		}
	}
}
