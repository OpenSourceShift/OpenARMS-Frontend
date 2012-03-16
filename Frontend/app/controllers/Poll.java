package controllers;

/*import org.json.JSONException;
import org.json.JSONObject;*/

/*import Utility.RestClient;*/
import com.google.gson.JsonParseException;

import api.Response.GetResultsResponse;
import api.Request.GetResultsRequest;

import play.mvc.Controller;

@Deprecated
public class Poll extends Controller {
	public static void getResults(String id, String adminkey) throws JsonParseException {
		/*String res = RestClient.getInstance().getResults(id, adminkey);*/
		/*JSONObject resJSON = new JSONObject(APIClient.getInstance().send(new api.Request.getResults(id, adminkey)));
		String res = resJSON.getString("res");*/
		
		GetResultsResponse response = (GetResultsResponse) APIClient.getInstance().send(new GetResultsRequest(id, adminkey));
		String res = "wtf is res";
		
		System.out.println(res);
		if (res != null && !res.isEmpty())
			renderJSON(res);
		renderJSON(false);
	}
}
