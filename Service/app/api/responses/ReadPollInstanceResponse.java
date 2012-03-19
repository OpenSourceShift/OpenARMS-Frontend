package api.responses;

import models.Choice;
import models.PollInstance;
import api.entities.ChoiceJSON;
import api.entities.PollInstanceJSON;

public class ReadPollInstanceResponse extends Response {
	public PollInstanceJSON pollinstance;
	public ReadPollInstanceResponse() {
	}
	public ReadPollInstanceResponse(PollInstance pi) {
		this(pi.toJson());
	}
	public ReadPollInstanceResponse(PollInstanceJSON json) {
		this.pollinstance = json;
	}
}
