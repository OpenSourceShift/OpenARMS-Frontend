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
    	
    	String p2 = GsonHelper.toJson(json);
    	System.out.println(p2);
    	
    }
}
