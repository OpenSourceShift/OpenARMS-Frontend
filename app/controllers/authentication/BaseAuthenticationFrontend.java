package controllers.authentication;

import controllers.APIClient;
import controllers.Application;
import controllers.BaseController;

public class BaseAuthenticationFrontend extends BaseController {

	public static void logout() {
	    try {
			APIClient.deauthenticate();
	    } catch (Exception e) {
	    	params.flash();
	    	validation.addError(null, e.getMessage());
	    	validation.keep();
	    }
    	Application.index();
	}
}
