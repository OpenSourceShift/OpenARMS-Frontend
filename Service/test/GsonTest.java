import java.util.LinkedList;
import java.util.List;

import models.Choice;
import models.Poll;
import models.helpers.GsonHelper;

import org.junit.Test;

import api.Request;
import api.Request.CreateChoiceRequest;
import api.Request.CreatePollRequest;
import api.Response.CreatePollResponse;

import play.test.Fixtures;
import play.test.UnitTest;
import test.Response;

public class GsonTest extends UnitTest {

	@Test
    public void serializationTest() {
		Fixtures.deleteAllModels();
		Fixtures.loadModels("data.yml");
		
		List<Poll> polls = Poll.findAll();
    	assertEquals(polls.size(), 1);
    	
    	String json = GsonHelper.toJson(polls.get(0));
    	System.out.println(json);
    	
    	Poll p2 = GsonHelper.fromJson(json, Poll.class);
    	System.out.println(p2);
    	
    	assertEquals(polls.get(0).question, p2.question);
    	
    }
	
	@Test
    public void CreatePollRequestTest() {
		Fixtures.deleteAllModels();
		Fixtures.loadModels("data.yml");
		
		Poll p = new Poll("123456", "Strange question ...", false, "spam@creen.dk");
		p.choices = new LinkedList<Choice>();
		
		Choice c1 = new Choice(p, "Strange answer ..");
		p.choices.add(c1);
		
		Choice c2 = new Choice(p, "... even worse");
		p.choices.add(c2);
    	
    	CreatePollRequest r = new CreatePollRequest(p);
    	String json = GsonHelper.toJson(r);
    	
    	System.out.println();
    	System.out.println(json);
    	System.out.println();
    	
    }
	
	@Test
    public void CreatePollResponseTest() {
		Fixtures.deleteAllModels();
		Fixtures.loadModels("data.yml");
		
		Poll p = Poll.all().first();
    	
		CreatePollResponse r = new CreatePollResponse(p);
    	String json = GsonHelper.toJson(r);
    	
    	System.out.println();
    	System.out.println(json);
    	System.out.println();
    	
    }
	
	@Test
    public void CreateChoiceRequestTest() {
		Fixtures.deleteAllModels();
		Fixtures.loadModels("data.yml");
		
		List<Choice> choices = Choice.findAll();
    	assertEquals(choices.size(), 2);
    	
    	Choice c = choices.get(0);
    	CreateChoiceRequest r = new CreateChoiceRequest(c);
    	String json = GsonHelper.toJson(r);

    	System.out.println();
    	System.out.println(json);
    	System.out.println();
    }


	@Test
	public String CreatePollRequestTest2() {
		Fixtures.deleteAllModels();
		Fixtures.loadModels("data.yml");
		
		Poll p = new Poll("123456", "Strange question ...", false);
		p.choices = new LinkedList<Choice>();
		
		Choice c1 = new Choice(p, "Strange answer ..");
		p.choices.add(c1);
		
		Choice c2 = new Choice(p, "... even worse");
		p.choices.add(c2);
		
		CreatePollRequest r = new CreatePollRequest(p);
		String json = GsonHelper.toJson(r);
		
		return json;
	}
	/*
	@Test
    public void CreatePollResponseTest2() {
        Response response = GET("/");
        assertIsOk(response);
        assertContentType("text/html", response);
        assertCharset("utf-8", response);
    }*/
}