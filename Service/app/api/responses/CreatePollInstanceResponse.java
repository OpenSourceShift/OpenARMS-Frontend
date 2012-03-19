package api.responses;

import models.PollInstance;
import api.entities.PollInstanceJSON;

public class CreatePollInstanceResponse {
	public PollInstanceJSON pollinstance;
	public CreatePollInstanceResponse (PollInstance p) {
		this.pollinstance = p.toJson();
	}
}
