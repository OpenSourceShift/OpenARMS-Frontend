package controllers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import controllers.authentication.SimpleAuthentication;

import play.Logger;
import play.Play;
import play.libs.Codec;
import play.libs.Crypto;
import play.libs.Crypto.HashType;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Http.StatusCode;
import play.mvc.Util;
import api.helpers.GsonHelper;
import api.requests.DeauthenticateUserRequest;
import api.requests.LoadTestDataRequest;
import api.requests.Request;
import api.requests.SimpleAuthenticateUserRequest;
import api.responses.AuthenticateUserResponse;
import api.responses.EmptyResponse;
import api.responses.ExceptionResponse;
import api.responses.Response;

public class APIClient extends Controller {

	/**
	 * The host to use when contacting the service, this will be set by the constructor.
	 */
	public HttpHost host;
	
	class APIException extends RuntimeException {
		public APIException(String error_message) {
			super(error_message);
		}
	}
	
	/**
	 * Constructing a new API client, try to reuse these.
	 */
	public APIClient() {
		this(Play.configuration.getProperty("openarms.service_host"), Integer.valueOf(Play.configuration.getProperty("openarms.service_port")));
	}
	
	/**
	 * Constructs a new API client, from a set of try to reuse these.
	 * 
	 * @param hostname
	 * @param port
	 * @param userId
	 * @param userSecret
	 */
	public APIClient(String hostname, int port) {
		host = new HttpHost(hostname, port);
	}
	
	@Util
	public static void setAuthentication(Long userId, String userSecret) {
		session.put("user_id", userId);
		if(userSecret == null) {
			session.put("user_secret", null);
		} else {
			session.put("user_secret", Crypto.encryptAES(userSecret));
		}
	}
	
	@Util
	public static APIClient getInstance() {
		return new APIClient();
	}
	
	@Util
	private HttpRequestBase getBaseRequest(api.requests.Request request) {
		HttpRequestBase httpRequest;
		if(request.getHttpMethod().equals(Request.Method.GET)) {
			httpRequest = new HttpGet();
		} else if(request.getHttpMethod().equals(Request.Method.POST)) {
			httpRequest = new HttpPost();
		} else if(request.getHttpMethod().equals(Request.Method.PUT)) {
			httpRequest = new HttpPut();
		} else if(request.getHttpMethod().equals(Request.Method.DELETE)) {
			httpRequest = new HttpDelete();
		} else {
			throw new RuntimeException("Unknown HTTP-method of the API-request.");
		}
		return httpRequest;
	}

	@Util
	public Response sendRequest(Request request) {
		return sendRequest(request, true);
	}
	
	@Util
	public Response sendRequest(Request request, boolean redirectOnAuthenticationRequest) {
		DefaultHttpClient client = new DefaultHttpClient();
		
		String json = GsonHelper.toJson(request);
		HttpRequestBase httpRequest = getBaseRequest(request);
		ByteArrayEntity bae = new ByteArrayEntity(json.getBytes());
		
		// Adding the payload
		if(httpRequest instanceof HttpPost) {
			((HttpPost) httpRequest).setEntity(bae);
		} else if(httpRequest instanceof HttpPut) {
			((HttpPut) httpRequest).setEntity(bae);
		}
		
		// Setting the URI.
		httpRequest.setURI(URI.create(request.getURL()));
		// Set the authentication header to match the user_secret known from the user session.
		String encryptedUserSecret = session.get("user_secret");
		String userId = session.get("user_id");
		if(encryptedUserSecret != null && !encryptedUserSecret.isEmpty() && userId != null && !userId.isEmpty()) {
			Logger.debug("encryptedUserSecret = "+encryptedUserSecret);
			String decryptedUserSecret = Crypto.decryptAES(encryptedUserSecret);
			String authenticationString = userId+":"+decryptedUserSecret;
			String decryptedUserSecretBase64Encoded = Codec.encodeBASE64(authenticationString.getBytes());
			httpRequest.addHeader("Authorization", "Basic "+decryptedUserSecretBase64Encoded);
		}
		
		// TODO: Remember to set the encoding of the request.
		Logger.debug("APIClient sends: %s to %s%s as %s", json, host.toString(), httpRequest.getURI(), httpRequest.getMethod());
		
		try {
			HttpResponse httpResponse = client.execute(host, httpRequest);
			HttpEntity httpResponseEntity = httpResponse.getEntity();
			// Check the response content-type.
			if(httpResponseEntity.getContentType().getValue().startsWith("application/json")) {
				BufferedReader br = new BufferedReader(new InputStreamReader(httpResponseEntity.getContent()));
				String responseJson = "";
				String line;
				while ((line = br.readLine()) != null) {
					responseJson += line;
					Logger.debug("APIClient receives: %s", line);
				}
				Response response = GsonHelper.fromJson(responseJson, request.getExpectedResponseClass());
				response.statusCode = httpResponse.getStatusLine().getStatusCode();
				
				if(response.statusCode == StatusCode.UNAUTHORIZED && redirectOnAuthenticationRequest) {
					if(isLoggedIn()) {
						// Deauthenticate as the client and the service is in different believes.
						Logger.debug("Deauthenticating as the client and the service is in different believes.");
						deauthenticate();
					}
					SimpleAuthentication.showform(null);
					 // This we will never get to.
					return response;
				} else if(response.success()) {
					return response;
				} else {
					throw new APIException(response.error_message);
				}
			} else {
				Logger.error("APIClient receives something with the wrong content-type.");
				//Logger.debug(httpResponseEntity.getContent());
				BufferedReader br = new BufferedReader(new InputStreamReader(httpResponseEntity.getContent()));
				String line;
				String html = "";
				while ((line = br.readLine()) != null) {
					//Logger.debug("APIClient receives: %s", line);
					html += line;
				}
				renderHtml(html);
				Response response = new EmptyResponse();
				response.statusCode = httpResponse.getStatusLine().getStatusCode();
				response.error_message = "Http response didn't have the application/json content-type, got: "+httpResponseEntity.getContentType().getValue();
				return response;
			}
		} catch (Exception e) {
			throw new RuntimeException("Error from the OpenARMS service: "+e.getMessage(), e);
		}
	}
	
	@Util
	public static Response send(Request request) {
		return getInstance().sendRequest(request);
	}
	
	@Util
	public static Response send(Request request, boolean redirectOnAuthenticationRequest) {
		return getInstance().sendRequest(request, redirectOnAuthenticationRequest);
	}

	public static void tunnel(String url) {
		try {
			url = "/"+url; // As the initial slash is removed in routes.
			
			DefaultHttpClient client = new DefaultHttpClient();
			
			HttpRequestBase httpRequest;
			if(request.method.equals("GET")) {
				httpRequest = new HttpGet();
			} else if(request.method.equals("POST")) {
				httpRequest = new HttpPost();
			} else if(request.method.equals("PUT")) {
				httpRequest = new HttpPut();
			} else if(request.method.equals("DELETE")) {
				httpRequest = new HttpDelete();
			} else {
				System.err.println("Unknown HTTP-method of the API-request.");
				throw new Exception("Unknown HTTP-method of the API-request.");
			}
			
			httpRequest.setURI(URI.create(url));
			// Set the authentication header to match the user_secret known from the user session.
			String encryptedUserSecret = session.get("user_secret");
			String userId = session.get("user_id");
			if(encryptedUserSecret != null && !encryptedUserSecret.isEmpty() && userId != null && !userId.isEmpty()) {
				Logger.debug("encryptedUserSecret = "+encryptedUserSecret);
				String decryptedUserSecret = Crypto.decryptAES(encryptedUserSecret);
				String authenticationString = userId+":"+decryptedUserSecret;
				String decryptedUserSecretBase64Encoded = Codec.encodeBASE64(authenticationString.getBytes());
				httpRequest.addHeader("Authorization", "Basic "+decryptedUserSecretBase64Encoded);
			}

			if(Logger.isDebugEnabled()) {
				Logger.debug("APIClient tunnel sends: "+httpRequest.getMethod()+" for "+httpRequest.getURI()+" on "+getInstance().host);
				for(org.apache.http.Header h: httpRequest.getAllHeaders()) {
					Logger.debug(" - with the following headers ("+httpRequest.getAllHeaders().length+" in total): "+h.getName()+" = "+h.getValue());
				}
			}
			
			HttpResponse httpResponse = client.execute(getInstance().host, httpRequest);
			
			// Set the status code.
			response.status = httpResponse.getStatusLine().getStatusCode();
			// Tunnel through the headers ...
			for(org.apache.http.Header h: httpResponse.getAllHeaders()) {
				response.setHeader(h.getName(), h.getValue());
			}
			// Write the response.
			httpResponse.getEntity().writeTo(response.out);
			
		} catch (Exception e) {
			e.printStackTrace();
			response.status = StatusCode.INTERNAL_ERROR;
	        response.contentType = "application/json";
	        
	        Response responseJson = new ExceptionResponse(e);
			renderJSON(responseJson);
		}
	}
	
	@Util
	public static EmptyResponse loadServiceData(String yamlDataFile) throws Exception {
		LoadTestDataRequest req = new LoadTestDataRequest(yamlDataFile);
		return (EmptyResponse) APIClient.send(req);
	}

	@Util
	public static void deauthenticate() {
		EmptyResponse deauthenticateResponse = (EmptyResponse)APIClient.send(new DeauthenticateUserRequest());
		if(Http.StatusCode.error(deauthenticateResponse.statusCode)) {
			System.err.println("Error deauthenticating: "+deauthenticateResponse.error_message);
		}
		session.put("user_id", null);
		session.put("user_secret", null);
		if(StatusCode.error(deauthenticateResponse.statusCode)) {
			throw new RuntimeException(deauthenticateResponse.error_message);
		}
	}
	
	@Util
	public static Long getCurrentUserId() {
		if(session.get("user_id") == null) {
			return null;
		} else {
			return Long.valueOf(session.get("user_id"));
		}
	}

	@Util
	public static boolean isLoggedIn() {
		return (session.get("user_id") != null && session.get("user_secret") != null);
	}
}
