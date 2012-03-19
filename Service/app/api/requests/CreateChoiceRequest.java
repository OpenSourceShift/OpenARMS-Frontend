package api.requests;
import models.Choice;
import api.responses.CreateChoiceResponse;
import api.entities.ChoiceJSON;

public class CreateChoiceRequest extends Request {
	public static final Class EXPECTED_RESPONSE = CreateChoiceResponse.class;
	public Choice choice;
	public CreateChoiceRequest(ChoiceJSON c)
	{
		this.choice.fromJson(c);
	}
	
	@Override
	public String getURL() {
		// TODO Auto-generated method stub
		return null;
	}
}