package api.responses;

import models.Choice;
import api.entities.ChoiceJSON;

public class ReadChoiceResponse extends Response {
	public ChoiceJSON choice;
	public ReadChoiceResponse() {
	}
	public ReadChoiceResponse(Choice c) {
		this(c.toJson());
	}
	public ReadChoiceResponse(ChoiceJSON json) {
		this.choice = json;
	}
}