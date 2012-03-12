package api;

public class BaseJSON {
	public String error_message = null;
	public BaseJSON() {
		
	}
	public BaseJSON(String error_message) {
		this.error_message = error_message;
	}
}
