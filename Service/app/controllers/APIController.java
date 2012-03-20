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
	public static class NotFoundException extends Exception {}
	public static class UnauthorizedException extends Exception {}
	
	public static Map<Class<? extends Exception>, Integer> STATUS_CODES = new HashMap<Class<? extends Exception>, Integer>();
	
	static {
		STATUS_CODES.put(Exception.class, StatusCode.INTERNAL_ERROR);
		STATUS_CODES.put(NotFoundException.class, StatusCode.NOT_FOUND);
		STATUS_CODES.put(UnauthorizedException.class, StatusCode.UNAUTHORIZED);
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
		
        response.contentType = "application/json";
        // TODO: Find out if this is the right way to get the encoding.
        response.encoding = Http.Response.current().encoding;
        
        Response responseJson = new ExceptionResponse(e);
        String json = GsonHelper.toJson(responseJson);
		renderJSON(json);
	}
	
	protected static void renderJSON(Object o) {
		renderText(GsonHelper.toJson(o));
	}
	
}
