package api.responses;

public class ExceptionResponse extends Response {
	public ExceptionResponse(Exception e) {
		this.error_message = e.getMessage();
	}
}
