package api.requests;
import models.Poll;
import api.responses.CreatePollResponse;
import api.entities.ChoiceJSON;
import api.entities.PollJSON;

public class CreatePollRequest extends Request {
	public static final Class EXPECTED_RESPONSE = CreatePollResponse.class;
	public PollJSON poll;
	public CreatePollRequest(PollJSON p) {
		this.poll = p;
	}
	@Override
	public String getURL() {
		return "/poll";
	}
}