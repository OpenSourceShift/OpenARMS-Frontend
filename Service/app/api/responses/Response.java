package api.responses;

public class Response {
	
	public String error_message = null;
	public Response() {
		
	}
	public Response(String error_message) {
		this.error_message = error_message;
	}
}
