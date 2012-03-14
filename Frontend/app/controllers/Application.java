package controllers;

import java.util.Calendar;
import java.util.Date;

import play.Play;
import play.i18n.Lang;
import play.mvc.Controller;

public class Application extends Controller {

	public static void index() {
		String test = "We are testing :)";
		render(test);
	}

	public static void changeLang(String lang) {
		Lang.change(lang);
	}
}

