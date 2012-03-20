package api.requests;

import api.helpers.GsonSkip;
import api.responses.Response;

/**
 * A request for the service.
 */
public abstract class Request {
	public static enum Method {
		GET,
		POST,
		PUT,
		DELETE
	}

	public abstract Method getHttpMethod();
	public abstract String getURL();
	public abstract Class<? extends Response> getExpectedResponseClass();
}
