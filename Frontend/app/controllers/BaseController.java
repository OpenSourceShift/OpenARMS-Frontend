package controllers;

import controllers.APIClient.APIException;
import play.Logger;
import play.mvc.Before;
import play.mvc.Catch;
import play.mvc.Controller;

public abstract class BaseController extends Controller {
	@Before
	public static void injectUser() {
		renderArgs.put("loggedIn", LoginUser.isLoggedIn());
	}
	
	@Catch(APIException.class)
	public static void renderAPIException(Exception e) {
		renderText("Catched an exception: "+e.getMessage());
		//Logger.debug("Catched an exception: ", e);
	}
}
