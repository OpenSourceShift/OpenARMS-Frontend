package controllers;

import org.json.JSONException;
import org.json.JSONObject;

/*import Utility.RestClient;*/
import play.mvc.Controller;

@Deprecated
public class Poll extends Controller {
	public static void getResults(String id, String adminkey) throws JSONException {
		/*String res = RestClient.getInstance().getResults(id, adminkey);*/
		JSONObject resJSON = new JSONObject(APIClient.getInstance().send(new api.Request.getResults(id, adminkey)));
		String res = resJSON.getString("res");
		System.out.println(res);
		if (res != null && !res.isEmpty())
			renderJSON(res);
		renderJSON(false);
	}
}
