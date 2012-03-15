import java.util.List;

import models.Poll;
import models.helpers.GsonHelper;

import org.junit.Test;

import play.test.Fixtures;
import play.test.UnitTest;

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
}
