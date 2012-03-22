import java.util.HashMap;
import java.util.Random;

import org.junit.Test;

import api.entities.UserJSON;
import api.requests.CreateUserRequest;
import api.responses.CreateUserResponse;
import api.responses.Response;
import controllers.APIClient;
import play.mvc.Http.StatusCode;
import play.test.UnitTest;


public class TestDuplicateUser extends UnitTest {
	public static void failIfNotSuccessful(Response response) {
    	if(!StatusCode.success(response.statusCode)) {
    		if(response.error_message == null) {
    			response.error_message = "No error message from service.";
    		}
    		fail("did not get the HTTP-OK status-code from the service, got "+response.statusCode+": "+response.error_message);
		}
	}
	
	@Test
	public void testCreateFullConfig() throws Exception {

    	Random r = new Random();
		// Create user
		UserJSON u = new UserJSON();
		u.name = "openarms";
		u.email = "test@test.com";
		u.secret = null;
		u.attributes = new HashMap<String, String>();
		u.attributes.put("password", "1234");
		u.backend = "class models.SimpleUserAuthBinding";

		CreateUserResponse userresponse = (CreateUserResponse) APIClient.send(new CreateUserRequest(u));

    	failIfNotSuccessful(userresponse);
    	assertNotNull(userresponse.user.id);
    	assertEquals(userresponse.user.name, u.name);
    	
    	CreateUserResponse userresponse2 = (CreateUserResponse) APIClient.send(new CreateUserRequest(u));

    	failIfNotSuccessful(userresponse2);
    	assertNotNull(userresponse2.user.id);
    	assertEquals(userresponse2.user.name, u.name);
 
	}

}
