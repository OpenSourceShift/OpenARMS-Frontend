package api.entities;

import java.util.LinkedList;
import java.util.List;

import models.Choice;
import models.Poll;

public class PollJSON {
	public Long id;
	public String token;
	public String reference;
	public String question;
	public List<ChoiceJSON> choices;
	public PollJSON(Poll p) {
		this.id = p.id;
		this.token = p.token;
		this.reference = p.reference;
		this.question = p.question;
		
		choices = new LinkedList<ChoiceJSON>();
		for(Choice c: p.choices) {
			choices.add(new ChoiceJSON(c));
		}
	}
}
