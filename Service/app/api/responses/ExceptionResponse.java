package api.responses;

public class ExceptionResponse extends Response {
	public ExceptionResponse(Throwable throwable) {
		if(throwable == null) {
			this.error_message = "(No message attached)";
		} else {
			if(throwable.getMessage() == null) {
				this.error_message = throwable.getClass().getName()+": (No message attached)";
			} else {
				//this.error_message = throwable.getClass().getName()+": "+throwable.getMessage();
				this.error_message = throwable.getMessage();
			}
		}
	}
}
