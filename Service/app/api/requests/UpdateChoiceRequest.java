package api.requests;

import api.entities.ChoiceJSON;
import api.responses.UpdateChoiceResponse;

public class UpdateChoiceRequest extends Request {
	public static final Class EXPECTED_RESPONSE = UpdateChoiceResponse.class;

	public ChoiceJSON choice;
	public UpdateChoiceRequest (ChoiceJSON c) {
		this.choice = c;
	}
	
	@Override
	public String getURL() {
		return "/choice/" + choice.id;
	}

	
	
}
