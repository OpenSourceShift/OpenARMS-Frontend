package api.requests;

import api.entities.ChoiceJSON;
import api.responses.ReadChoiceResponse;
import api.responses.Response;

/**
 * A request for the service: 
 */
public class ReadChoiceRequest extends Request {
	public Long choice_id;
	public ReadChoiceRequest (Long l) {
		this.choice_id = l;
	}
	
	@Override
	public String getURL() {
		return "/choice/" + choice_id;
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
