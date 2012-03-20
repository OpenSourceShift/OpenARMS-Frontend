package api.requests;

import api.entities.ChoiceJSON;
import api.requests.Request.Method;
import api.responses.DeleteChoiceResponse;
import api.responses.Response;

/**
 * A request for the service: Deletes a choice
 */
public class DeleteChoiceRequest extends Request {
	public Long choice_id;
	public DeleteChoiceRequest (Long l) {
		this.choice_id = l;
	}
	
	@Override
	public String getURL() {
		return "/choice/" + choice_id;
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
