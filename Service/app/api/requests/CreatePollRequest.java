package api.requests;

import api.Request;
import api.Response.CreatePollResponse;
import api.entities.ChoiceJSON;
import api.entities.PollJSON;

/*
	POST /poll
		{ "poll": [
		     "reference": "Some text",
		     "question": "What is 2+2?",
		     "choices": [ { "text": "Answer is 1",
		                    "correct": 0 },
		                  { "text": "Answer is 2",
		                    "correct": 1 },
		                  { "text": "Answer is 42",
		                    "correct": 0 }
		     ],
		  ]
		}        
 */


/* static? */
public class CreatePollRequest extends Request {
	public static final Class EXPECTED_RESPONSE = CreatePollResponse.class;
	
	public PollJSON poll;
	public CreatePollRequest(PollJSON p) {
		// Reset any id ...
		this.poll.id = null;
		for(ChoiceJSON choice: this.poll.choices) {
			choice.id = null;
		}
	}
	@Override
	public String getURL() {
		// TODO Auto-generated method stub
		return null;
	}
}