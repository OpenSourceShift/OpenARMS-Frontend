package controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

import models.Choice;
import models.Poll;
import api.helpers.GsonHelper;
import notifiers.MailNotifier;
import api.responses.CreatePollResponse;
import play.mvc.Controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import api.entities.PollJSON;

/**
 * Class that manages the responses in the API for Polls.
 * @author OpenARMS Service Team
 *
 */

public class PollController extends APIController {

	/**
	 * Method that saves a new Poll in the DataBase.
	 */
	public static void create() {
        try {	
			BufferedReader reader = new BufferedReader(new InputStreamReader(request.body));
	
	    	//Takes the PollJSON and creates a new Poll object with this PollJSON.
	        String json = reader.readLine();
	        PollJSON polljson = GsonHelper.fromJson(json, PollJSON.class);
	        Poll poll = Poll.fromJson(polljson);
	
	        // Generates a Unique ID and saves the Poll.
	        do {
	            poll.token = String.valueOf(new Random(System.currentTimeMillis()).nextInt(999999));
	        } while (!Poll.find("byToken", poll.token).fetch().isEmpty());
	                    
	        poll.save();
	        
	        //Creates the PollJSON Response.
	        CreatePollResponse r = new CreatePollResponse(poll);
	    	String jsonresponse = GsonHelper.toJson(r);
	    	renderJSON(jsonresponse);
        	
		} catch (Exception e) {
			e.printStackTrace();
			renderException(e);
		}
	}

	/**
	 * Method that gets a Poll from the DataBase.
	 */
	public static void retrieve () {
		try {
			String pollid = params.get("id");
	
			//Takes the Poll from the DataBase.
			Poll poll = Poll.find("byID", pollid).first();
	
			//Creates the PollJSON Response.
			if (poll == null) {
				renderJSON("The Poll does not exist!");
			}
			
			CreatePollResponse r = new CreatePollResponse(poll);
			String jsonresponse = GsonHelper.toJson(r);
	
			renderJSON(jsonresponse);
			
		} catch (Exception e) {
			renderException(e);
		}
	}

	/**
	 * Method that edits a Poll already existing in the DataBase.
	 */
	public static void edit () {
		try {
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(request.body));
			String pollid = params.get("id");
	
			//Takes the Poll from the DataBase.
			Poll originalpoll = Poll.find("byID", pollid).first();

			//Takes the edited PollJSON and creates a new Poll object with this PollJSON.
            String json = reader.readLine();
            PollJSON polljson = GsonHelper.fromJson(json, PollJSON.class);
            Poll editedpoll = Poll.fromJson(polljson);
            
            //Changes the old fields for the new ones.
            if (editedpoll.question != null) {
            	originalpoll.question = editedpoll.question;
            }
            if (editedpoll.reference != null) {
            	originalpoll.reference = editedpoll.reference;
            }
            if (editedpoll.choices != null) {
            	originalpoll.choices = editedpoll.choices;
            }
            
            originalpoll.save();
            
            //Creates the PollJSON Response.
            CreatePollResponse r = new CreatePollResponse(originalpoll);
        	String jsonresponse = GsonHelper.toJson(r);
        	renderJSON(jsonresponse);
            
		} catch (Exception e) {
			renderException(e);
		}
	}
	
	/**
	 * Method that creates a new Poll from an already existing one in the DataBase.
	 */
	public static void copy () {
		try {
			String oldpollid = params.get("id_old");
	
			//Takes the Poll from the DataBase.
			Poll oldpoll = Poll.find("byID", oldpollid).first();
			
			Poll newpoll = new Poll(null, oldpoll);
	
	        // Generates a new Unique ID and saves the Poll.
	        do {
	            newpoll.token = String.valueOf(new Random(System.currentTimeMillis()).nextInt(999999));
	        } while (!Poll.find("byToken", newpoll.token).fetch().isEmpty());
	        
	        //Saves the new poll in the DataBase.
	        newpoll.save();
			
		} catch (Exception e) {
			renderException(e);
		}
	}

	/**
	 * Method that deletes a Poll existing in the DataBase.
	 */
	public static void delete () {
		try {
			String pollid = params.get("id");
	
			//Takes the Poll from the DataBase.
			Poll poll = Poll.find("byID", pollid).first();
			
			//Deletes the Poll from the DataBase and creates an empty PollJSON for the response.
			poll.delete();
	
			poll.question = null;
			poll.reference = null;
			poll.choices = null;
			
			//Creates the PollJSON Response.
			CreatePollResponse r = new CreatePollResponse(poll);
			String jsonresponse = GsonHelper.toJson(r);
			renderJSON(jsonresponse);
			
		} catch (Exception e) {
			renderException(e);
		}
	}
}
