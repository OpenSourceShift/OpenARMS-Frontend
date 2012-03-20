package api.requests;

import api.entities.ChoiceJSON;
import api.responses.ReadChoiceResponse;
import api.responses.Response;

public class ReadChoiceRequest extends Request {
	public ChoiceJSON choice;
	public ReadChoiceRequest (ChoiceJSON c) {
		this.choice = c;
	}
	
	@Override
	public String getURL() {
		return "/choice/" + choice.id;
	}

	@Override
	public Class<? extends Response> getExpectedResponseClass() {
		return ReadChoiceResponse.class;
	}

	@Override
	public Method getHttpMethod() {
		return Method.GET;
	}
}
