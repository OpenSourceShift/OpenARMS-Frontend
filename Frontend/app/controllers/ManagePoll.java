package controllers;
import java.util.Calendar;

import play.mvc.Controller;
import play.mvc.Http.StatusCode;
import api.entities.PollInstanceJSON;
import api.requests.CreatePollInstanceRequest;
import api.requests.ReadPollByTokenRequest;
import api.responses.AuthenticateUserResponse;
import api.responses.CreatePollInstanceResponse;
import api.responses.ReadPollResponse;

public class ManagePoll extends Controller {
	public static void index(String token, String adminkey) {
		/*
		validation.required(token);
		validation.required(adminkey);
		boolean redirect = true;
		if (validation.hasErrors()) {
			token = session.get("token");
			adminkey = session.get("adminkey");
			redirect = false;
		}
		
		try {
			CheckAdminkeyResponse response = (CheckAdminkeyResponse) APIClient.getInstance().send(new CheckAdminkeyRequest(token, adminkey));
			boolean correct = response.bool;
			if (correct) {
				if (redirect) {
					session.put("token", token);
					session.put("adminkey", adminkey);
					ManagePoll.index(null, null);
				} else {
					render(token, adminkey);
				}
			}
		} catch (Exception e) {
		}
		Application.index();
		*/
	}

	public static void activate() {
		/*
		String token = session.get("token");
		String adminkey = session.get("adminkey");

		String duration = "";
		String durationString = "00:00";
		// Get the duration from the server
		String res = null;
		try {
			GetPollResponse response = (GetPollResponse) APIClient.getInstance().send(new GetPollRequest(token));
			duration = response.duration;
			// Parse the duration and turn it into minutes and seconds
			int dur = Integer.parseInt(duration);
			int m = (int) Math.floor(dur / 60);
			int s = dur - m * 60;
			// Add leading zeros and make the string.
			char[] zeros = new char[2];
			Arrays.fill(zeros, '0');
			DecimalFormat df = new DecimalFormat(String.valueOf(zeros));

			durationString = df.format(m) + ":" + df.format(s);
		} catch (Exception e) {
			System.err.println("Error parsing the response from the service layer: '"+res+"'");
			e.printStackTrace();
		}

		render(token, adminkey, duration, durationString);
		*/
	}

	public static void activateSubmit(String minutes, String seconds) {
		/*
		String token = session.get("token");
		String adminkey = session.get("adminkey");
		
		int s = 0;
		int m = 0;

		validation.required(minutes);
		validation.required(seconds);
		if (!validation.hasErrors()) {
			try {
				s = Integer.parseInt(seconds);
				if (s < 0) {
					s = 0;
				}
				
				m = Integer.parseInt(minutes);
				if (m < 0) {
					m = 0;
				}

				int duration = s + m * 60;
				APIClient.getInstance().send(new ActivatePollRequest(token, adminkey, duration));
			} catch (Exception e) {
			}
		}

		activate();
		*/
	}

	public static void edit() {
		/*
		String token = session.get("token");
		String adminkey = session.get("adminkey");
		render(token, adminkey);
		*/
	}

	public static void statistics() {
		try {
			// Load service data.
			APIClient.loadServiceData("data.yml");
			boolean authenticated = APIClient.authenticateSimple("spam@creen.dk", "openarms");
			if(!authenticated) {
				throw new Exception("Could not authenticate");
			}

			// Load service data.
			ReadPollResponse res1 = (ReadPollResponse)APIClient.send(new ReadPollByTokenRequest("123456"));
			if(!StatusCode.success(res1.statusCode)) {
				throw new Exception("Couldn't read poll with token 123456.");
			}

			// Load service data.
			PollInstanceJSON pi = new PollInstanceJSON();
			pi.poll_id = res1.poll.id;
			pi.startDateTime = Calendar.getInstance().getTime();
			Calendar endDateTimeCalendar = Calendar.getInstance();
			endDateTimeCalendar.set(Calendar.YEAR, 2020);
			pi.endDateTime = endDateTimeCalendar.getTime();
			CreatePollInstanceResponse res2 = (CreatePollInstanceResponse)APIClient.send(new CreatePollInstanceRequest(pi));
			if(!StatusCode.success(res2.statusCode)) {
				throw new Exception("Couldn't create poll instance with token time now.");
			}
			
			Long pollinstance_id = res2.pollinstance.id;
			render(pollinstance_id);
		} catch(Exception e) {
			e.printStackTrace();
			// TODO: Use an error template.
		}
	}
}
