package api.requests;

import api.responses.EmptyResponse;
import api.responses.Response;

/**
 * A request for the service: Deletes a choice
 */
public class LoadTestDataRequest extends Request {
	public String yaml_file;
	public LoadTestDataRequest (String yaml_file) {
		this.yaml_file = yaml_file;
	}
	
	@Override
	public String getURL() {
		return "/loadtestdata/" + yaml_file;
	}

	@Override
	public Class<? extends Response> getExpectedResponseClass() {
		return EmptyResponse.class;
	}

	@Override
	public Method getHttpMethod() {
		return Method.GET;
	}
}
