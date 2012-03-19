package controllers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Random;

import models.Poll;
import models.Vote;
import api.Response.CreatePollResponse;
import api.Response.CreateVoteResponse;
import api.entities.PollJSON;
import api.entities.VoteJSON;
import api.helpers.GsonHelper;

/**
 * Class that manages the responses in the API for Votes.
 * @author OpenARMS Service Team
 *
 */

public class VoteController extends APIController {
	/**
	 * Method that saves a new Vote in the DataBase.
	 */
	public static void create() {
        try {	
			BufferedReader reader = new BufferedReader(new InputStreamReader(request.body));
	
	    	//Takes the VoteJSON and creates a new Vote object with this VoteJSON.
	        String json = reader.readLine();
	        VoteJSON votejson = GsonHelper.fromJson(json, VoteJSON.class);
	        Vote vote = Vote.fromJson(votejson);
          
	        vote.save();
	        
	        //Creates the PollJSON Response.
	        CreateVoteResponse r = new CreateVoteResponse(vote);
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
