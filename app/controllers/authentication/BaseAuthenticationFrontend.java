package controllers.authentication;

import java.util.LinkedList;
import java.util.List;

import play.Play;
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
	
	public static void showform(String email) {
		List<String> frontendTemplates = new LinkedList<String>();
		String frontends = Play.configuration.getProperty("openarms.authentication.frontends");
		if(frontends == null) {
			throw new RuntimeException("You have to specify the openarms.authentication.frontends configuration property.");
		}
		for(String frontend: frontends.split(",")) {
			try {
				Class c = Class.forName(frontend);
				if(!BaseAuthenticationFrontend.class.isAssignableFrom(c)) {
					throw new RuntimeException("Any OpenARMS authentication frontend has to extend the BaseAuthenticationFrontend-class.");
				}
				frontend = frontend.replaceAll("\\.", "/").replaceFirst("controllers/", "");
				frontendTemplates.add(frontend+"/showform.html");
			} catch (ClassNotFoundException e) {
				throw new RuntimeException("Couldn't load the "+frontend+" authentication frontend.", e);
			}
		}
		render(frontendTemplates, email);
	}
}
