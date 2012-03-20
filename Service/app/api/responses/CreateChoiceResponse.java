package api.responses;

import api.entities.ChoiceJSON;

public class CreateChoiceResponse extends Response {
	public ChoiceJSON choice;
	public CreateChoiceResponse() {
	}

	public CreateChoiceResponse(ChoiceJSON json) {
		this.choice = json;
	}
}