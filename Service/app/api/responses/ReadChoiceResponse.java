package api.responses;

import api.entities.ChoiceJSON;

public class ReadChoiceResponse extends Response {
	public ChoiceJSON choice;
	public ReadChoiceResponse() {
	}

	public ReadChoiceResponse(ChoiceJSON json) {
		this.choice = json;
	}
}