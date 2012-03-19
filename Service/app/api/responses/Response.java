package api.responses;

import api.helpers.GsonSkip;

public class Response {
	
	public String error_message = null;
	@GsonSkip(classes = {Response.class})
	public int statusCode;
	public Response() {
		
	}
	public Response(String error_message) {
		this.error_message = error_message;
	}
}
