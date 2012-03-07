package Utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

import models.Vote;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import play.Play;

import com.google.gson.Gson;

public class RestClientBackup {

	private static RestClient instance;
	private static String server_address;
	private static int server_port;
	static {
		try {
			server_address = (String)Play.configuration.get("openarms.service_host");
			server_port = Integer.parseInt((String)Play.configuration.get("openarms.service_port"));
		} catch (Exception e) {
			System.err.println("Error loading the OpenARMS configuration parameters, please check that your .conf files contains" +
				"Both the 'openarms.service_host' and the 'openarms.service_port' keys.");
		}
	}
	
	private String response;
	private final String tag = "RestClient";

	/**
	 * Constructor
	 * 
	 * @param address
	 *            is address of server
	 * @param port
	 *            the server is running on
	 */
	private RestClientBackup(String address, int port) {
		server_address = address;
		server_port = port;
	}

	/**
	 * getInstance of RestClient Singleton
	 * 
	 * @return
	 */
	public static RestClient getInstance() {
		if (instance == null)
			instance = new RestClient(server_address, server_port);
		return instance;
	}

	/**
	 * This method provide hard assembled process to connect to server
	 * 
	 * @param service
	 */
	private void executeRequest(HttpRequestBase method, String service, String stringJSON) {
		DefaultHttpClient client = new DefaultHttpClient();
		StringBuilder sb = new StringBuilder();
		try {
			String url = "http://" + server_address + ":" + Integer.toString(server_port) + "/" + service;
			URI u = new URI(url);
			method.setURI(u);

			if ((method instanceof HttpPost) && (stringJSON.length() != 0)) {
				ByteArrayEntity bae = new ByteArrayEntity(stringJSON.getBytes());
				bae.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
				((HttpPost) method).setEntity(bae);
			}

			HttpResponse hr = client.execute(method);
			
			HttpEntity entity = hr.getEntity();

			if (entity != null) {
				InputStream is = entity.getContent();
				BufferedReader br = new BufferedReader(
						new InputStreamReader(is));
				String line;
				while ((line = br.readLine()) != null) {
					sb.append(line);
				}
				response = sb.toString();
				is.close();
			}
		} catch (URISyntaxException ex) {
		} catch (IOException ex) {
		} finally {
			client.getConnectionManager().shutdown();
		}
	}

	/**
	 * Encapsulate http response from server to String
	 * 
	 * @param service
	 * @return JSONObject
	 */
	@SuppressWarnings("finally")
	private JSONObject getService(String service) {
		return requestService(new HttpGet(), service, null);
	}

	/**
	 * Send json to the server in the request
	 * 
	 * @param service
	 * @param json
	 * @return
	 */
	@SuppressWarnings("finally")
	private JSONObject postService(String service, String stringJSON) {
		return requestService(new HttpPost(), service, stringJSON);
	}

	/**
	 * Using the 
	 * 
	 * @param service
	 * @param json
	 * @return
	 */
	@SuppressWarnings("finally")
	private JSONObject requestService(HttpRequestBase baseRequest, String service, String stringJSON) {
		try {
			this.executeRequest(baseRequest, service, stringJSON);
			return new JSONObject(this.response);
		} catch (Exception e) {
			// Error parsing the response
			System.err.println("Error parsing the response from the service layer, I got '"+this.response+"'");
			return new JSONObject();
		}
	}

	/**
	 * Returns the response of the HTTP request
	 * 
	 * @return
	 */
	public String getResponse() {
		return this.response;
	}

	/**
	 * Handle method
	 * 
	 * @return get question encapsulated in JSONObject
	 */
	public JSONObject getQuestion(String id) {
		return this.getService(StaticQuery.get_question(id));
	}

	public boolean vote(Vote v) {
		try {
			Gson g = new Gson();
			JSONObject o = new JSONObject(this.postService(StaticQuery.vote(v.pollID), g.toJson(v)));
			return o.getBoolean("voteSuccessful");
		} catch (JSONException e) {
		}

		return false;
	}

	public JSONObject activate(String id, String adminkey, int duration) throws JSONException {
		JSONObject o = new JSONObject();
		o.put("duration", duration);
		return this.postService(StaticQuery.activate(id, adminkey), o.toString());
	}

	/**
	 * Post a new question to the server
	 * 
	 * @param o
	 *            The object to base the JSON on.
	 * @return A JSON object with the adminkey and pollID.
	 * @throws JSONException
	 */
	public JSONObject createQuestion(Object o) throws JSONException {
		Gson gson = new Gson();
		return this.postService(StaticQuery.post_question, gson.toJson(o));
	}

	/**
	 * Handle method
	 * 
	 * @return get poll results from server
	 */
	public JSONObject getResults(String pollID, String adminkey) {
		return this.getService(StaticQuery.get_results(pollID, adminkey));
	}

	public boolean checkAdminkey(String id, String adminkey) {
		JSONObject res = this.getService(StaticQuery.get_checkadminkey(id, adminkey));
		// TODO: Fix
		return false;
	}

	/**
	 * Private class for identifying base URL for services
	 */
	private static class StaticQuery {
		public final static String post_question = "newPoll";

		public static String vote(int id) {
			return "vote/" + id;
		}

		public static String get_question(String id) {
			return id;
		}

		public static String get_results(String id, String adminkey) {
			if (adminkey != null)
				return "getResults/" + id + "/" + adminkey;
			else
				return "getResults/" + id;
		}

		public static String get_checkadminkey(String id, String adminkey) {
			return "checkAdminKey/" + id + "/" + adminkey;
		}

		public static String activate(String id, String adminkey) {
			return "activation/" + id + "/" + adminkey;
		}
	}
}
