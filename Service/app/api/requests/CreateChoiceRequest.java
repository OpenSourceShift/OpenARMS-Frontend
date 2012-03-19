package api.requests;
import api.entities.ChoiceJSON;
import api.requests.Request.Method;
import api.responses.CreateChoiceResponse;

public class CreateChoiceRequest extends Request {
	public static final Class EXPECTED_RESPONSE = CreateChoiceResponse.class;
	public ChoiceJSON choice;
	public CreateChoiceRequest(ChoiceJSON c) {
		this.method = Method.POST;
		this.choice = c;
	}
	
	@Override
	public String getURL() {
		return "/choice";
	}
}