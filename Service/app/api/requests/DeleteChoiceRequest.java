package api.requests;

import api.entities.ChoiceJSON;
import api.requests.Request.Method;
import api.responses.DeleteChoiceResponse;
import api.responses.Response;

public class DeleteChoiceRequest extends Request {
	public ChoiceJSON choice;
	public DeleteChoiceRequest (ChoiceJSON c) {
		this.choice = c;
	}
	
	@Override
	public String getURL() {
		return "/choice/" + choice.id;
	}

	@Override
	public Class<? extends Response> getExpectedResponseClass() {
		return DeleteChoiceResponse.class;
	}

	@Override
	public Method getHttpMethod() {
		return Method.DELETE;
	}
}
