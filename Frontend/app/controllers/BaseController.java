package controllers;

import play.Logger;
import play.mvc.Before;
import play.mvc.Catch;
import play.mvc.Controller;

public abstract class BaseController extends Controller {
	@Before
	public static void injectUser() {
		renderArgs.put("loggedIn", LoginUser.isLoggedIn());
	}
	
	@Catch
	public static void renderException(Exception e) {
		Logger.debug("Catched an exception: ", e);
	}
}
