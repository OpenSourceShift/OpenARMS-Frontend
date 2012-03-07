package controllers;

import org.json.JSONObject;

import Utility.RestClient;
import play.mvc.Controller;

public class Poll extends Controller {
	public static void getResults(String id, String adminkey) {
		JSONObject res = RestClient.getInstance().getResults(id, adminkey);
		renderJSON(res);
	}
}
