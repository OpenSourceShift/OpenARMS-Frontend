package api.responses;

import api.entities.ChoiceJSON;

public class UpdateChoiceResponse extends Response {
	public ChoiceJSON choice;
	public UpdateChoiceResponse() {
	}

	public UpdateChoiceResponse(ChoiceJSON json) {
		this.choice = json;
	}
}