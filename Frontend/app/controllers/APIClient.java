package controllers;

import javax.naming.OperationNotSupportedException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import api.requests.VoteRequest;
import api.responses.VoteResponse;

import play.mvc.*;
import play.mvc.Http.StatusCode;

public class APIClient extends Controller {
    
	public DefaultHttpClient client;
	/**
	 * Constructing a new API client, try to reuse these.
	 */
	public APIClient() {
		client = new DefaultHttpClient();
	}
	
	public VoteResponse send(VoteRequest request) throws OperationNotSupportedException {
		throw new OperationNotSupportedException();
	}
	
	public JSONObject send(JSONObject request) throws OperationNotSupportedException {
		throw new OperationNotSupportedException();
	}
}
