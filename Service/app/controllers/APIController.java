package controllers;

import java.util.HashMap;
import java.util.Map;

import models.Choice;
import models.PollInstance;
import models.SimpleUserAuthBinding;
import models.User;
import models.Vote;

import play.Logger;
import play.Play;
import play.mvc.Catch;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Http.StatusCode;
import play.test.Fixtures;
import api.helpers.GsonHelper;
import api.responses.EmptyResponse;
import api.responses.ExceptionResponse;
import api.responses.Response;

public abstract class APIController extends Controller {
	
	public static class NotFoundException extends Exception {
		public NotFoundException() {
			this(null);
		}
		public NotFoundException(String message) {
			super(message);
		}
	}
	
	public static class NotModifiedException extends Exception {
		public NotModifiedException() {
			this(null);
		}
		public NotModifiedException(String message) {
			super(message);
		}
	}
	
	public static class UnauthorizedException extends Exception {
		public UnauthorizedException() {
			this(null);
		}
		public UnauthorizedException(String message) {
			super(message);
		}
	}
	
	public static Map<Class<? extends Exception>, Integer> STATUS_CODES = new HashMap<Class<? extends Exception>, Integer>();
	
	static {
		STATUS_CODES.put(Exception.class, StatusCode.INTERNAL_ERROR);
		STATUS_CODES.put(NotFoundException.class, StatusCode.NOT_FOUND);
		STATUS_CODES.put(UnauthorizedException.class, StatusCode.UNAUTHORIZED);
		STATUS_CODES.put(NotModifiedException.class, StatusCode.NOT_MODIFIED);
	}
	
	 @Catch
	 public static void renderException(Throwable throwable) {
		System.err.println("Exception thrown in an APIController: "+throwable.getMessage());
		throwable.printStackTrace();
		// Return this error to the user.
		Integer statusCode = STATUS_CODES.get(throwable.getClass());
		if(statusCode == null) {
			response.status = StatusCode.INTERNAL_ERROR;
		} else {
			response.status = statusCode;
		}
		
        response.contentType = "application/json";
        // TODO: Find out if this is the right way to get the encoding.
        response.encoding = Http.Response.current().encoding;
        
        Response responseJson = new ExceptionResponse(throwable);
        String json = GsonHelper.toJson(responseJson);
		renderJSON(json);
	}
	
	protected static void renderJSON(Object o) {
		renderText(GsonHelper.toJson(o));
	}
	
	public static void loadTestData(String yaml_file) {
		try {
			if(Play.mode.equals(Play.Mode.DEV)) {
				Fixtures.deleteAllModels();
				Fixtures.loadModels(yaml_file);
				
				// Create the binding
		        User user = User.find("byEmail", "spam@creen.dk").first();
		        SimpleUserAuthBinding authBinding = new SimpleUserAuthBinding();
		        authBinding.user = user;
		        authBinding.password = "openarms";
		        authBinding.save();
		        user.userAuth = authBinding;
		        user.userAuth.save();
		        
				renderJSON(new EmptyResponse().toJson());
			} else {
				throw new UnauthorizedException("This action is only activated when the application runs in development mode.");
			}
		} catch (Exception e) {
			renderException(e);
		}
	}
	
}
