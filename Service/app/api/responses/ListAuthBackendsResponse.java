package api.responses;

import java.util.List;

import controllers.AuthBackend;

public class ListAuthBackendsResponse extends Response {
	public List<Class<? extends AuthBackend>> backends;
}
