package api.requests;
import api.entities.PollJSON;
import api.responses.CreatePollResponse;

public class CreatePollRequest extends Request {
	public static final Class EXPECTED_RESPONSE = CreatePollResponse.class;
	public PollJSON poll;
	public CreatePollRequest(PollJSON p) {
		this.method = Method.POST;
		this.poll = p;
	}
	@Override
	public String getURL() {
		return "/poll";
	}
}