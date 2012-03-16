package api.entities;

import java.util.LinkedList;
import java.util.List;

import models.Choice;
import models.Poll;

public class PollJSON extends BaseJSON {
	public Long id;
	public String token;
	public String reference;
    public Boolean multipleAllowed;
	public String question;
	public List<ChoiceJSON> choices;
	
	/*
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

	public Poll toPoll() {
		
		List<Choice> choices2 = new LinkedList<Choice>();

		for (ChoiceJSON c : this.choices) {
			choices2.add(c.toChoice());	
		}
		
		Poll poll = new Poll(this.token, this.question,false);
		poll.choices = choices2;
		
		for (Choice c : poll.choices) {
			c.poll = poll;
		}
		
		return poll;
	}
	*/
}
