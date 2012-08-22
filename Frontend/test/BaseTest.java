import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import javax.mail.Session;

import controllers.APIClient;
import api.entities.ChoiceJSON;
import api.entities.PollInstanceJSON;
import api.entities.PollJSON;
import api.entities.UserJSON;
import api.requests.AuthenticateUserRequest;
import api.requests.CreatePollInstanceRequest;
import api.requests.CreatePollRequest;
import api.requests.CreateUserRequest;
import api.requests.DeleteUserRequest;
import api.requests.ReadPollRequest;
import api.requests.ReadUserDetailsRequest;
import api.responses.CreatePollInstanceResponse;
import api.responses.CreatePollResponse;
import api.responses.CreateUserResponse;
import api.responses.EmptyResponse;
import api.responses.ReadPollResponse;
import api.responses.ReadUserDetailsResponse;
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
			if(e.getCause() != null && e.getCause().getMessage().equals("No user with this email.")) {
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
	
	public static Long ensurePoll() {
		Long userId = ensureAuthenticatedUser();
		ReadUserDetailsResponse response1 = (ReadUserDetailsResponse) APIClient.send(new ReadUserDetailsRequest(userId));
		if(response1.polls.size() > 0) {
			PollJSON p = response1.polls.get(0);
			return p.id;
		} else {
			PollJSON p = new PollJSON();
			p.question = "Is this a question?";
			p.reference = "myreference";
			p.loginRequired = false;
			p.multipleAllowed = false;
			
			ChoiceJSON c1 = new ChoiceJSON();
			c1.text = "Yes";
			p.choices.add(c1);
			
			ChoiceJSON c2 = new ChoiceJSON();
			c2.text = "Maybe";
			p.choices.add(c2);
			
			ChoiceJSON c3 = new ChoiceJSON();
			c3.text = "No!";
			p.choices.add(c3);
			
			CreatePollResponse response2 = (CreatePollResponse) APIClient.send(new CreatePollRequest(p));
	    	if(response2.success()) {
	    		return response2.poll.id;
	    	} else {
	    		fail("Couldn't create a poll instance.");
	    	}
		}
		return null;
	}
	
	public static Long ensurePollInstance() {
		Long pollId = ensurePoll();
		ReadPollResponse response1 = (ReadPollResponse) APIClient.send(new ReadPollRequest(pollId));
		if(response1.poll.pollinstances != null && response1.poll.pollinstances.size() > 0) {
			PollInstanceJSON pi = response1.poll.pollinstances.get(0);
			return pi.id;
		} else {
	    	PollInstanceJSON pi = new PollInstanceJSON();
	    	pi.poll_id = pollId;
	    	
	    	Calendar start = Calendar.getInstance();
	    	pi.start = start.getTime();
	    	
	    	Calendar end = Calendar.getInstance();
	    	end.set(Calendar.YEAR, start.get(Calendar.YEAR) + 1);
	    	pi.end = end.getTime();

	    	CreatePollInstanceResponse response2 = (CreatePollInstanceResponse) APIClient.getInstance().sendRequest(new CreatePollInstanceRequest(pi));
	    	if(response2.success()) {
	    		return response2.pollinstance.id;
	    	} else {
	    		fail("Couldn't create a poll instance.");
	    	}
		}
		return null;
	}

	public static void assertSuccessful(Response response) {
    	if(!response.success()) {
    		if(response.error_message == null) {
    			response.error_message = "No error message from service.";
    		}
    		fail("did not get the HTTP-OK status-code from the service, got "+response.statusCode+": "+response.error_message);
		}
	}

	public static void assertUnSuccessful(Response response) {
    	if(response.success()) {
    		fail("did not get the an error status-code from the service, got "+response.statusCode);
		}
	}
}
