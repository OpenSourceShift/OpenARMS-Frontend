package api.requests;
import models.Poll;
import api.responses.CreatePollResponse;
import api.entities.ChoiceJSON;
import api.entities.PollJSON;

public class CreatePollRequest extends Request {
	public static final Class EXPECTED_RESPONSE = CreatePollResponse.class;
	public Poll poll;
	public CreatePollRequest(PollJSON p) {
		// Reset any id ...
		this.poll.id = null;
		for(ChoiceJSON choice: p.choices) {
			choice.id = null;
		}
	}
	@Override
	public String getURL() {
		// TODO Auto-generated method stub
		return null;
	}
}