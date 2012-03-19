package api.responses;

import models.Choice;
import api.entities.ChoiceJSON;

public class CreateChoiceResponse extends Response {
	public ChoiceJSON choice;
	public CreateChoiceResponse(Choice c) {
		this.choice = c.toJson();
	}
}