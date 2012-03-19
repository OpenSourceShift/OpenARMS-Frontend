package api.responses;

import models.Choice;
import api.entities.ChoiceJSON;

public class CreateChoiceResponse extends Response {
	public ChoiceJSON choice;
	public CreateChoiceResponse() {
	}
	public CreateChoiceResponse(Choice c) {
		this(c.toJson());
	}
	public CreateChoiceResponse(ChoiceJSON json) {
		this.choice = json;
	}
}