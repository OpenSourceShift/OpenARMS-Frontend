package controllers;

import javax.naming.OperationNotSupportedException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

/*
import api.requests.VoteRequest;
import api.responses.VoteResponse;
*/

import play.mvc.*;
import play.mvc.Http.StatusCode;

public class APIClient extends Controller {
	
	public static APIClient singleton;
    
	public DefaultHttpClient client;
	/**
	 * Constructing a new API client, try to reuse these.
	 */
	public APIClient() {
		client = new DefaultHttpClient();
	}
	
	public static APIClient getInstance() {
		if(singleton == null) {
			singleton = new APIClient();
		}
		return singleton;
	}
	
	public api.Response send(api.Request request) {
		// TODO: Serialize the request object and send it for the service.
		// Also remember to do proper errorhandling
		return null;
	}
}
