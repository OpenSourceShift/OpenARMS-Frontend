package controllers;

import java.util.HashMap;
import java.util.Map;

import api.helpers.GsonHelper;
import api.responses.ExceptionResponse;
import api.responses.Response;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Http.StatusCode;

public abstract class APIController extends Controller {
	
	public static Map<Class<Exception>, Integer> STATUS_CODES = new HashMap<Class<Exception>, Integer>();
	
	static {
		STATUS_CODES.put(Exception.class, StatusCode.INTERNAL_ERROR);
	}
	
	protected static void renderException(Exception e) {
		System.err.println("Exception thrown in an APIController: "+e.getMessage());
		e.printStackTrace();
		// Return this error to the user.
		Integer statusCode = STATUS_CODES.get(e.getClass());
		if(statusCode == null) {
			response.status = StatusCode.INTERNAL_ERROR;
		} else {
			response.status = statusCode;
		}
		String encoding = Http.Response.current().encoding;
        response.setContentTypeIfNotSet("application/json; charset="+encoding);
        
        Response responseJson = new ExceptionResponse(e);
		renderJSON(responseJson);
	}
	
	protected static void renderJSON(Object o) {
		renderText(GsonHelper.toJson(o));
	}
	
}
