package api.requests;

import api.entities.UserJSON;
import api.responses.AuthenticateUserResponse;
import api.responses.EmptyResponse;
import api.responses.Response;

/**
 * A request for the service: Deauthenticate against an authentication backend.
 */
public class DeauthenticateUserRequest extends Request {
	public DeauthenticateUserRequest () {
	}
		
	@Override
	public String getURL() {
		return "/user/deauthenticate";
	}

	@Override
	public Method getHttpMethod() {
		return Method.GET;
	}

	@Override
	public Class<? extends Response> getExpectedResponseClass() {
		return EmptyResponse.class;
	}
	
}
