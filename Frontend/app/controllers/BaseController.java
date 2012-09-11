package controllers;

import controllers.APIClient.APIException;
import play.Logger;
import play.mvc.Before;
import play.mvc.Catch;
import play.mvc.Controller;
import play.mvc.Finally;

public abstract class BaseController extends Controller {
	@Before
	public static void injectUser() {
		renderArgs.put("loggedIn", APIClient.isLoggedIn());
	}
	
	@Catch(APIException.class)
	public static void renderAPIException(Exception e) {
		renderText("Catched an exception: "+e.getMessage());
		//Logger.debug("Catched an exception: ", e);
	}
	
	@Finally
	public static void addSOPHeader() {
		//response.headers.put("Access-Control-Allow-Origin", new Header(name, value))
		response.setHeader("Access-Control-Allow-Origin", "http://stress.openarms.dk/");
	}
	
}
