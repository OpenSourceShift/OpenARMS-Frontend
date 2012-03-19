package api.requests;

import api.entities.ChoiceJSON;
import api.responses.ReadChoiceResponse;

public class ReadChoiceRequest extends Request {
	public static final Class EXPECTED_RESPONSE = ReadChoiceResponse.class;
	public ChoiceJSON choice;
	public ReadChoiceRequest (ChoiceJSON c) {
		this.choice = c;
	}
	
	@Override
	public String getURL() {
		return "/choice/" + choice.id;
	}
}
