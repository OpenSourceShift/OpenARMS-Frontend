package api.requests;

import api.helpers.GsonSkip;

public abstract class Request {
	public static enum Method {
		GET,
		POST,
		PUT,
		DELETE
	}

	@GsonSkip(classes={Request.class})
	public static final Class EXPECTED_RESPONSE = null;
	@GsonSkip(classes={Request.class})
	public Method method;
	public abstract String getURL();
}
