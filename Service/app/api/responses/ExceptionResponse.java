package api.responses;

public class ExceptionResponse extends Response {
	public ExceptionResponse(Exception e) {
		if(e == null) {
			this.error_message = "(No message attached)";
		} else {
			if(e.getMessage() == null) {
				this.error_message = e.getClass().getName()+": (No message attached)";
			} else {
				this.error_message = e.getClass().getName()+": "+e.getMessage();
			}
		}
	}
}
