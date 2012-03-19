package api.requests;

import api.entities.ChoiceJSON;

public class DeleteChoiceRequest extends Request {

	public static final Class EXPECTED_RESPONSE = DeleteChoiceResponse.class;
	
	public ChoiceJSON choice;
	public DeleteChoiceRequest (ChoiceJSON c) {
		this.choice = c;
	}
	
	@Override
	public String getURL() {
		return "/choice/" + choice.id;
	}
}
