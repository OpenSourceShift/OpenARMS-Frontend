package api.responses;

import models.Choice;
import models.PollInstance;
import api.entities.ChoiceJSON;
import api.entities.PollInstanceJSON;

public class CreatePollInstanceResponse extends Response {
	public PollInstanceJSON pollinstance;
	public CreatePollInstanceResponse() {
	}

	public CreatePollInstanceResponse(PollInstanceJSON json) {
		this.pollinstance = json;
	}
}
