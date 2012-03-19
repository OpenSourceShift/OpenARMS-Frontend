package controllers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Random;

import models.Poll;
import models.Vote;
import api.responses.CreatePollResponse;
import api.responses.CreateVoteResponse;
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
	        
	        //Creates the VoteJSON Response.
	        CreateVoteResponse r = new CreateVoteResponse(vote.toJson());
	    	String jsonresponse = GsonHelper.toJson(r);
	    	renderJSON(jsonresponse);
        	
		} catch (Exception e) {
			e.printStackTrace();
			renderException(e);
		}
	}

	/**
	 * Method that gets a Vote from the DataBase.
	 */
	public static void retrieve () {
		try {
			String voteid = params.get("id");
	
			//Takes the Vote from the DataBase.
			Vote vote= Vote.find("byID", voteid).first();
	
			//Creates the VoteJSON Response.
			if (vote == null) {
				renderJSON("The Vote does not exist!");
			}
			
			CreateVoteResponse r = new CreateVoteResponse(vote.toJson());
			String jsonresponse = GsonHelper.toJson(r);
	
			renderJSON(jsonresponse);
			
		} catch (Exception e) {
			renderException(e);
		}
	}

	/**
	 * Method that edits a Vote already existing in the DataBase.
	 */
	public static void edit () {
		try {
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(request.body));
			String voteid = params.get("id");
	
			//Takes the Vote from the DataBase.
			Vote originalvote = Vote.find("byID", voteid).first();

			//Takes the edited VoteJSON and creates a new Vote object with this VoteJSON.
            String json = reader.readLine();
            VoteJSON votejson = GsonHelper.fromJson(json, VoteJSON.class);
            Vote editedvote = Vote.fromJson(votejson);
            
            //Changes the old fields for the new ones.
            if (editedvote.choice != null) {
				originalvote.choice = editedvote.choice;
			}
            if (editedvote.pollInstance != null) {
				originalvote.pollInstance = editedvote.pollInstance;
			}
 
            originalvote.save();
            
            //Creates the VoteJSON Response.
            CreateVoteResponse r = new CreateVoteResponse(originalvote.toJson());
        	String jsonresponse = GsonHelper.toJson(r);
        	renderJSON(jsonresponse);
            
		} catch (Exception e) {
			renderException(e);
		}
	}

	/**
	 * Method that deletes a Vote existing in the DataBase.
	 */
	public static void delete () {
		try {
			String voteid = params.get("id");
	
			//Takes the Vote from the DataBase.
			Vote vote = Vote.find("byID", voteid).first();
			
			//Deletes the Vote from the DataBase and creates an empty VoteJSON for the response.
			vote.delete();
			vote.choice = null;
			vote.pollInstance = null;

			
			//Creates the VoteJSON Response.
			CreateVoteResponse r = new CreateVoteResponse(vote.toJson());
			String jsonresponse = GsonHelper.toJson(r);
			renderJSON(jsonresponse);
			
		} catch (Exception e) {
			renderException(e);
		}
	}
}
