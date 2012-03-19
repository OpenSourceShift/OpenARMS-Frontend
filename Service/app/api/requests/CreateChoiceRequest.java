package api.requests;
import api.responses.CreateChoiceResponse;
import api.entities.ChoiceJSON;

public class CreateChoiceRequest extends Request {
	public static final Class EXPECTED_RESPONSE = CreateChoiceResponse.class;
	public ChoiceJSON choice;
	public CreateChoiceRequest(ChoiceJSON c) {
		this.choice = c;
	}
	
	@Override
	public String getURL() {
		return "/choice";
	}
}