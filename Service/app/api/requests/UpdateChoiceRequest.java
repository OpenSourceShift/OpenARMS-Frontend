package api.requests;

import api.entities.ChoiceJSON;
import api.responses.Response;
import api.responses.UpdateChoiceResponse;

/**
 * A request for the service: 
 */
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

	@Override
	public Class<? extends Response> getExpectedResponseClass() {
		return UpdateChoiceResponse.class;
	}

	@Override
	public Method getHttpMethod() {
		return Method.PUT;
	}

	
	
}
