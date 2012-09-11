package controllers;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.annotations.AccessType;

import controllers.authentication.AuthenticationBackend;

import models.Choice;
import models.PollInstance;
import models.SimpleAuthenticationBinding;
import models.User;
import models.Vote;

import play.Logger;
import play.Play;
import play.mvc.After;
import play.mvc.Before;
import play.mvc.Catch;
import play.mvc.Controller;
import play.mvc.Finally;
import play.mvc.Http;
import play.mvc.Scope;
import play.mvc.Http.StatusCode;
import play.mvc.Util;
import play.mvc.results.BadRequest;
import play.mvc.results.Error;
import play.mvc.results.Forbidden;
import play.mvc.results.NotFound;
import play.mvc.results.NotModified;
import play.mvc.results.Ok;
import play.mvc.results.Result;
import play.mvc.results.Unauthorized;
import play.test.Fixtures;
import play.utils.FastRuntimeException;
import api.helpers.GsonHelper;
import api.responses.EmptyResponse;
import api.responses.ExceptionResponse;
import api.responses.Response;

public abstract class APIController extends Controller {
	
	public static Map<Class<? extends Exception>, Integer> STATUS_CODES = new HashMap<Class<? extends Exception>, Integer>();

	// TODO: See if we can find some way of using the builtin NotFound, Unautorized or Forbidden classes?
	public static class SpecialNotFound extends FastRuntimeException {
		public SpecialNotFound(String what) {
			super(what);
		}
	};
	public static class SpecialUnauthorized extends FastRuntimeException {
		public SpecialUnauthorized(String realm) {
			super(realm);
		}
	};
	public static class SpecialForbidden extends FastRuntimeException {
		public SpecialForbidden(String reason) {
			super(reason);
		}
	};
	public static class SpecialError extends FastRuntimeException {
		public SpecialError(String reason) {
			super(reason);
		}
	};
	
	static {
		STATUS_CODES.put(Exception.class, StatusCode.INTERNAL_ERROR);
		STATUS_CODES.put(SpecialError.class, StatusCode.INTERNAL_ERROR);
		STATUS_CODES.put(SpecialNotFound.class, StatusCode.NOT_FOUND);
		STATUS_CODES.put(SpecialUnauthorized.class, StatusCode.UNAUTHORIZED);
		STATUS_CODES.put(SpecialForbidden.class, StatusCode.FORBIDDEN);
	}
	
	/*
	@Before
	protected static void setFormat() {
		//System.out.println("request.format = "+request.format);
		// Assuming that the format of any call to any API Controller action should be json.
		request.format = "json";
		response.contentType = "application/json";
	}
	*/

	@Catch()
	protected static void renderException(Throwable throwable) {
		System.err.println("Exception thrown in an APIController: "+throwable.getMessage());
		throwable.printStackTrace();
		// Return this error to the user.
		Integer statusCode = STATUS_CODES.get(throwable.getClass());
		if(statusCode == null) {
			response.status = StatusCode.INTERNAL_ERROR;
		} else {
			response.status = statusCode;
		}
	    
	    Response responseJson = new ExceptionResponse(throwable);
	    String json = GsonHelper.toJson(responseJson);
		renderJSON(json);
	}
	 
	protected static void renderJSON(Object o) {
        String encoding = Http.Response.current().encoding;
        response.contentType= "application/json; charset="+encoding;
		renderText(GsonHelper.toJson(o));
	}
	
	public static void loadTestData(String yaml_file) {
		try {
			if(Play.mode.equals(Play.Mode.DEV)) {
				Fixtures.deleteAllModels();
				Fixtures.loadModels(yaml_file);
				
				// Create the binding
				/*
		        User user = User.find("byEmail", "spam@creen.dk").first();
		        SimpleAuthenticationBinding authBinding = new SimpleAuthenticationBinding();
		        authBinding.user = user;
		        authBinding.password = "openarms";
		        authBinding.save();
		        user.authenticationBinding = authBinding;
		        user.authenticationBinding.save();
		        */
		        
				renderJSON(new EmptyResponse().toJson());
			} else {
				unauthorized("This action is only activated when the application runs in development mode.");
			}
		} catch (Exception e) {
			// TODO: Change this..
			//renderException(e);
		}
	}
	
	protected static void requireUser(User user) {
		User currentUser = AuthenticationBackend.getCurrentUser();
		if(user != null) {
			if(currentUser == null) {
				unauthorized("This action requires authentication. Please use the /user/authenticate to get your user secret.");
			} else {
				if(!user.equals(currentUser)) {
					unauthorized("This action requires authentication from a specific user, and you are not this user.");
				}
			}
		}
	}

    /**
     * Send a 401 Unauthorized response
     * @param realm The realm name
     */
    protected static void unauthorized(String realm) {
        throw new SpecialUnauthorized(realm);
    }

    /**
     * Send a 401 Unauthorized response
     */
    protected static void unauthorized() {
        throw new SpecialUnauthorized("Unauthorized");
    }

    /**
     * Send a 404 Not Found response
     * @param what The Not Found resource name
     */
    protected static void notFound(String what) {
        throw new SpecialNotFound(what);
    }

    /**
     * Send a todo response
     */
    protected static void todo() {
        notFound("This action has not been implemented Yet (" + request.action + ")");
    }

    /**
     * Send a 404 Not Found reponse
     */
    protected static void notFound() {
        throw new SpecialNotFound("");
    }

    /**
     * Send a 403 Forbidden response
     * @param reason The reason
     */
    protected static void forbidden(String reason) {
        throw new SpecialForbidden(reason);
    }

    /**
     * Send a 403 Forbidden response
     */
    protected static void forbidden() {
        throw new SpecialForbidden("Access denied");
    }

    /**
     * Send a 500 Error response
     * @param reason The reason
     */
    protected static void error(String reason) {
        throw new SpecialError(reason);
    }

    /**
     * Send a 500 Error response
     * @param reason The reason
     */
    protected static void error(Exception reason) {
        Logger.error(reason, "error()");
        throw new SpecialError(reason.toString());
    }

    /**
     * Send a 500 Error response
     */
    protected static void error() {
        throw new SpecialError("Internal Error");
    }
}
