package api.requests;

import java.net.URL;

import api.entities.UserJSON;
import api.responses.AuthenticateUserResponse;
import api.responses.CreateVoteResponse;
import api.responses.EmptyResponse;
import api.responses.Response;

/**
 * A request for the service: Authenticate against an authentication backend.
 */
public class CASAuthenticateUserRequest extends AuthenticateUserRequest {
	public final static String BACKEND = "controllers.authentication.CASAuthenticationBackend";
	public URL service;
	public String ticket;
	public CASAuthenticateUserRequest(String ticket) {
		super(BACKEND);
		this.ticket = ticket;
	}
	
}
