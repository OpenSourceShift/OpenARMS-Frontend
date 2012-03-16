package api.entities;

import java.util.List;

import api.helpers.GsonSkip;

import models.Choice;
import models.Poll;
import models.Vote;

public class ChoiceJSON {
	public Long poll_id;
	public Long id;
	public String text;
	
	public List<VoteJSON> votes;
	
	public ChoiceJSON(Choice c) {
		this.id = c.id;
		this.text = c.text;
		this.poll_id = c.poll.id;
		for(Vote v: c.votes) {
			votes.add(new VoteJSON(v));
		}
	}

	public Choice toChoice() {
		return new Choice(null, this.text);
	}
}
