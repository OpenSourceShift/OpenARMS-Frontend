package controllers;
import play.mvc.Controller;

import com.google.gson.JsonParseException;

@Deprecated
public class Poll extends Controller {
	public static void getResults(String id, String adminkey) throws JsonParseException {
		/*
		try {
			
			GetResultsResponse response = (GetResultsResponse) APIClient.send(new GetResultsRequest(id, adminkey));
			String res = "wtf is res";
			
			System.out.println(res);
			if (res != null && !res.isEmpty())
				renderJSON(res);
			renderJSON(false);
		} catch(Exception e) {
			// It failed
			// TODO: Tell the user!
			e.printStackTrace();
		}
		*/
	}
}
