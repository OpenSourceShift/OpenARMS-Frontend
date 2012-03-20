package api.requests;
import api.entities.ChoiceJSON;
import api.requests.Request.Method;
import api.responses.CreateChoiceResponse;
import api.responses.Response;

/**
 * A request for the service: Creates a choice
 */
public class CreateChoiceRequest extends Request {
	public ChoiceJSON choice;
	public CreateChoiceRequest(ChoiceJSON c) {
		this.choice = c;
	}
	
	@Override
	public String getURL() {
		return "/choice";
	}

	@Override
	public Class<? extends Response> getExpectedResponseClass() {
		return CreateChoiceResponse.class;
	}

	@Override
	public Method getHttpMethod() {
		return Method.POST;
	}
}