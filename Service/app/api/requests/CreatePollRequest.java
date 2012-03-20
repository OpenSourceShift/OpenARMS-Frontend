package api.requests;
import api.entities.PollJSON;
import api.responses.CreatePollResponse;
import api.responses.Response;

/**
 * A request for the service: Creates a poll
 */
public class CreatePollRequest extends Request {
	public PollJSON poll;
	public CreatePollRequest(PollJSON p) {
		this.poll = p;
	}
	@Override
	public String getURL() {
		return "/poll";
	}
	@Override
	public Class<? extends Response> getExpectedResponseClass() {
		return CreatePollResponse.class;
	}
	@Override
	public Method getHttpMethod() {
		return Method.POST;
	}
}