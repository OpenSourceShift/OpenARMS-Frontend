package api.responses;

import play.mvc.Http.StatusCode;
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
	
	public boolean success() {
		return StatusCode.success(statusCode);
	}
}
