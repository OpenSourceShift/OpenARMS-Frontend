package controllers;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import play.Logger;
import play.Play;
import play.data.validation.Validation;
import play.data.validation.Validation.ValidationResult;
import play.mvc.Http;

import models.Choice;
import models.Poll;
import models.PollInstance;
import models.SimpleAuthenticationBinding;
import models.User;
import models.Vote;
import api.entities.PollInstanceJSON;
import api.entities.PollJSON;
import api.entities.VoteSummaryJSON;
import api.helpers.GsonHelper;
import api.requests.AuthenticateUserRequest;
import api.requests.CreateUserRequest;
import api.requests.GenerateAuthChallengeRequest;
import api.requests.ListAuthBackendsRequest;
import api.requests.UpdateUserRequest;
import api.responses.AuthenticateUserResponse;
import api.responses.CreateUserResponse;
import api.responses.EmptyResponse;
import api.responses.GenerateAuthChallengeResponse;
import api.responses.GenerateSimpleAuthChallengeResponse;
import api.responses.ListAuthBackendsResponse;
import api.responses.ReadUserDetailsResponse;
import api.responses.ReadUserResponse;
import api.responses.UpdateUserResponse;

/**
 * Class that manages the responses in the API for Users.
 * @author OpenARMS Service Team
 *
 */
public class UserController extends APIController {
	/**
	 * Method that either generates a challange for the user or sends back a list of advailable backends.
	 * @throws Exception 
	 */
	public static void challenge() throws Exception {
		// Takes the UserJSON from the http body
		GenerateAuthChallengeRequest req = GsonHelper.fromJson(request.body, GenerateAuthChallengeRequest.class);
		
		if(req == null || req.backend == null) {
			// It was a ListAuthBackendsRequest ... but we dont need it for anything ...
			// ListAuthBackendsRequest listRequest = GsonHelper.fromJson(request.body, ListAuthBackendsRequest.class);
			List<Class<? extends AuthBackend>> backends = AuthBackend.advailableBackends();
			ListAuthBackendsResponse res = new ListAuthBackendsResponse();
			res.backends = new LinkedList<String>();
			for(Class clazz: backends) {
				res.backends.add(clazz.getCanonicalName());
			}
			renderJSON(res);
		} else {
			Class<? extends AuthBackend> backend = AuthBackend.getBackend(req.backend);
			if(backend != null) {
				// Find a challenge for this backend.
				Method getChallengeRequestClass = backend.getMethod("getChallengeRequestClass");
				Method generateChallenge = backend.getMethod("generateChallenge", GenerateAuthChallengeRequest.class);
				Class<? extends GenerateAuthChallengeRequest> requestClass = (Class<? extends GenerateAuthChallengeRequest>) getChallengeRequestClass.invoke(null);
				request.body.reset();
				GenerateAuthChallengeRequest challengeRequest = GsonHelper.fromJson(request.body, requestClass);
				GenerateAuthChallengeResponse response = (GenerateAuthChallengeResponse) generateChallenge.invoke(null, challengeRequest);
				renderJSON(response);
			} else {
				throw new Exception("This authentication backend is not supported.");
			}
		}
	}
	
	/**
	 * Method that authenticates the user.
	 * It generates new secret for the user.
	 * Used only when user is logging in.
	 * @throws Exception 
	 */
	public static void authenticate() throws Exception {
		// Takes the UserJSON from the http body
		AuthenticateUserRequest req = GsonHelper.fromJson(request.body, AuthenticateUserRequest.class);
		Class<? extends AuthBackend> backend = AuthBackend.getBackend(req.backend);
		
		if(backend != null) {
			Method getAuthenticateUserRequestClass = backend.getMethod("getAuthenticateUserRequestClass");
			Method authenticate = backend.getMethod("authenticate", AuthenticateUserRequest.class);
			
			/*// This is not needed, it will be tackled by tge aythenticate method call.
			// Lookup the user by email
			User user = User.find("byEmail", req.user.email).first();
			notFoundIfNull(user);
			*/

			Class<? extends AuthenticateUserRequest> requestClass = (Class<? extends AuthenticateUserRequest>) getAuthenticateUserRequestClass.invoke(null);
			request.body.reset();
			AuthenticateUserRequest authenticationRequest = GsonHelper.fromJson(request.body, requestClass);
			
			try {
				User user = (User) authenticate.invoke(null, authenticationRequest);
				if (user != null) {
				    //Creates the UserJSON Response.
					renderJSON(new AuthenticateUserResponse(user.toJson()));
				} else {
					unauthorized();
				}
			} catch (InvocationTargetException e) {
				if(e.getCause() != null) {
					unauthorized(e.getCause().getMessage());
				} else {
					unauthorized(e.getMessage());
				}
			}
		} else {
			// TODO: Consider to read this url from the routes.
			error("Backend not choosen, pick one from the 'GET /user/authenticate' list.");
		}
	}
	
	/**
	 * Method that deauthenticates the user.
	 * It deletes the secret for the user.
	 * Used only when user is logged in.
	 * @throws Exception 
	 */
	public static void deauthenticate() throws Exception {
		EmptyResponse response = new EmptyResponse();
		User user = AuthBackend.getCurrentUser();
		if (user != null) {
			user.secret = "";
			user.save();
		} else {
			// Already deauthenticated ...
			//throw new NotFoundException("Tried to deauthenticate a bad user");
		}
		renderJSON(response.toJson());
	}
	
	/**
	 * Method that authorizes the user.
	 * It keeps user logged in the system.
	 * Used every time user sends any request.
	 */
	public static boolean authorize() {
		User user = AuthBackend.getCurrentUser();
		return (user != null);
	}
	
	/**
	 * Method that saves a new User in the DataBase.
	 * @throws Exception 
	 */
	public static void create() throws Exception {
    	// Takes the UserJSON and creates a new User object with this UserJSON.
        CreateUserRequest req = GsonHelper.fromJson(request.body, CreateUserRequest.class);
        
        // Check if the email already exists in the system
        Long exists = User.count("byEmail", req.user.email);
        if (exists > 0) {
        	forbidden("User already exists in the system");
        }
        
        User user = User.fromJson(req.user);

		if(!user.validateAndCreate()) {
			error("The user didn't validate correctly.");
		}
        
		Class<? extends AuthBackend> backend = AuthBackend.getBackend(req.backend);
		if (backend != null && backend.equals(SimpleAuthenticationBackend.class)) {
			SimpleAuthenticationBinding authenticationBinding = new SimpleAuthenticationBinding();
			//Logger.debug("Creating new user with password = %s", req.attributes.get("password"));
			authenticationBinding.setPassword(req.attributes.get("password"));
			user.authenticationBinding = authenticationBinding;
		} else {
			error("Cannot create a user, using "+String.valueOf(req.backend)+" as authentication backend.");
		}
		
		user.authenticationBinding.user = user;
		//Logger.debug("Creating new authenticationBinding for user = %s with passwordHash = %s", user.authenticationBinding.user.toString(), ((SimpleAuthenticationBinding)user.authenticationBinding).passwordHash);
		if(user.authenticationBinding == null || !user.authenticationBinding.validateAndSave()) {
			error("The authentication attributes didn't validate.");
		}
		
		/*
		
		user.userAuth.save();
		
		user.save();
        
		user.userAuth.user = user;
		user.userAuth.save();
		*/
        
        // if (user.userAuth instanceof SimpleUserAuthBinding)
        // 	((SimpleUserAuthBinding)user.userAuth).save();
        //user.save();
           
        //Creates the UserJSON Response.
        CreateUserResponse res = new CreateUserResponse(user.toJson());
    	String jsonResponse = GsonHelper.toJson(res);
		response.status = Http.StatusCode.CREATED;
    	renderJSON(jsonResponse);
	}
	
	/**
	 * Method that gets a User from the DataBase.
	 * @throws Exception 
	 */
	public static void retrieve() throws Exception {
		String userid = params.get("id");

		// Takes the User from the DataBase.
		User user = User.find("byID", userid).first();
		notFoundIfNull(user);
		
        // If current user is not the same as the poll creator or there is no current user, throws an exception
		requireUser(user);
		
		//Creates the UserJSON Response.
		ReadUserResponse response = new ReadUserResponse(user.toJson());
		String jsonResponse = GsonHelper.toJson(response);
		renderJSON(jsonResponse);
	}
	
	/**
	 * Method that edits a User already existing in the DB.
	 * @throws Exception 
	 */
	public static void edit() throws Exception {
		String userid = params.get("id");

		//Takes the User from the DataBase.
		User originalUser = User.find("byID", userid).first();
		notFoundIfNull(originalUser);

		requireUser(originalUser);
		
		//Takes the edited UserJSON and creates a new User object with this UserJSON.
		UpdateUserRequest req = GsonHelper.fromJson(request.body, UpdateUserRequest.class);
        User editedUser = User.fromJson(req.user);
        
        //Changes the old fields for the new ones.
        if (editedUser.name != null)
        	originalUser.name = editedUser.name;
        if (editedUser.email != null)
        	originalUser.email = editedUser.email;
        if (editedUser.secret != null)
        	originalUser.secret = editedUser.secret;
        if (editedUser.authenticationBinding != null) {
        	throw new Exception("Cannot edit the users authentication binding, as this has not been implemented yet.");
        	/*
        	// Compares originalAuth with editedAuth
        	if (editedUser.authenticationBinding.getClass().toString().equals(originalUser.authenticationBinding.getClass().toString())) {
        		// Check authentication method
        		if (originalUser.authenticationBinding instanceof SimpleAuthenticationBinding) {
        			Long idAuth = ((SimpleAuthenticationBinding)originalUser.authenticationBinding).id;
        			SimpleAuthenticationBinding originalAuth = (SimpleAuthenticationBinding)SimpleAuthenticationBinding.find("byID", idAuth).fetch().get(0);
        			originalAuth.setPassword(((SimpleAuthenticationBinding)editedUser.authenticationBinding).passwordHash);
        			originalAuth.save();
        		}
        	}
        	*/
        }
        originalUser.save();
        
        
        //Creates the PollJSON Response.
        UpdateUserResponse response = new UpdateUserResponse(originalUser.toJson());
    	String jsonResponse = GsonHelper.toJson(response);
    	renderJSON(jsonResponse);
	}
	
	/**
	 * Method that deletes a User existing in the DataBase.
	 * @throws Exception 
	 */
	public static void delete () throws Exception {
		String userid = params.get("id");
		
		User.em().getTransaction().begin();

		//Takes the User from the DataBase.
		User user = User.find("byID", userid).first();
		notFoundIfNull(user);
		
		requireUser(user);
		
		//Deletes the Authentication from the DataBase.
		if (user.authenticationBinding instanceof SimpleAuthenticationBinding) {
			//Long idAuth = ((SimpleAuthenticationBinding)user.authenticationBinding).id;
			//SimpleAuthenticationBinding auth = (SimpleAuthenticationBinding)SimpleAuthenticationBinding.find("byID", idAuth).first();
			//auth.delete();
			user.authenticationBinding.delete();
		}
		//Deletes the User from the DataBase and creates an empty UserJSON for the response.
		user.delete();

		renderJSON(new EmptyResponse().toJson());
	}
	
	/**
	 * Method that gets a User from the DataBase.
	 * @throws Exception 
	 */
	public static void details() throws Exception {
		String userid = params.get("id");

		//Takes the User from the DataBase.
		User user = User.find("byID", userid).first();
		notFoundIfNull(user);

		requireUser(user);
		
		List<Poll> polls = Poll.find("byAdmin", AuthBackend.getCurrentUser()).fetch();
		List<PollJSON> pollsJ = new LinkedList<PollJSON>();
		
		for(Poll p: polls) {
			PollJSON pJ = p.toJson();
			pJ.pollinstances = new LinkedList<PollInstanceJSON>();
			for(PollInstance pi: p.instances) {
				PollInstanceJSON piJson = pi.toJson();
				long vote_count = 0;
		    	for(Choice c: pi.poll.choices) {
		    		VoteSummaryJSON summary = new VoteSummaryJSON();
					summary.choice_id = c.id;
					summary.choice_text = c.text;
					summary.count = Vote.count("pollInstance = ? and choice = ?", pi, c);
					vote_count += summary.count;
					// Add it to the result.
					piJson.votes.add(summary);
		    	}
		    	piJson.vote_count = vote_count;
		    	pJ.pollinstances.add(piJson);
			}
			pollsJ.add(pJ);
		}
		
		
		//Creates the UserJSON Response.
		ReadUserDetailsResponse response = new ReadUserDetailsResponse(user.toJson(), pollsJ);
		String jsonResponse = GsonHelper.toJson(response);
		renderJSON(jsonResponse);
	}
	
}