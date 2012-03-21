package api.responses;

import api.helpers.GsonHelper;
import api.helpers.GsonSkip;

public class Response {
	
	public String error_message = null;
	public Integer statusCode;
	public Response() {
		
	}
	public Response(String error_message) {
		this.error_message = error_message;
	}
	public String toJson() {
		return GsonHelper.toJson(this);
	}
}
