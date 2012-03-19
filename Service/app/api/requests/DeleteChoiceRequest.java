package api.requests;

import api.entities.ChoiceJSON;
import api.requests.Request.Method;
import api.responses.DeleteChoiceResponse;

public class DeleteChoiceRequest extends Request {

	public static final Class EXPECTED_RESPONSE = DeleteChoiceResponse.class;
	
	public ChoiceJSON choice;
	public DeleteChoiceRequest (ChoiceJSON c) {
		this.method = Method.DELETE;
		this.choice = c;
	}
	
	@Override
	public String getURL() {
		return "/choice/" + choice.id;
	}
}
