package api.requests;

import api.responses.EmptyResponse;
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
		return EmptyResponse.class;
	}

	@Override
	public Method getHttpMethod() {
		return Method.DELETE;
	}
}
