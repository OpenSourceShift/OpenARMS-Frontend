package controllers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Random;

import controllers.APIController.NotFoundException;
import controllers.APIController.UnauthorizedException;

import models.Poll;
import models.User;
import models.Vote;
import api.requests.CreateVoteRequest;
import api.responses.CreatePollResponse;
import api.responses.CreateVoteResponse;
import api.responses.EmptyResponse;
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
	    	//Takes the VoteJSON and creates a new Vote object with this VoteJSON.
	        CreateVoteRequest req = GsonHelper.fromJson(request.body, CreateVoteRequest.class);
	        Vote vote = Vote.fromJson(req.vote);
          
	        //If current user is not the same as the poll creator or there is no current user, throws an exception
			User u = AuthBackend.getCurrentUser();
			if (u == null || vote.user.id != u.id) {
		        throw new UnauthorizedException();
		    }
			
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
			
			if (vote == null) {
				throw new NotFoundException();
			}
	
			//Creates the VoteJSON Response.
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
			String voteid = params.get("id");
	
			//Takes the Vote from the DataBase.
			Vote originalvote = Vote.find("byID", voteid).first();
			
			if (originalvote == null) {
				throw new NotFoundException();
			}
			
	        //If current user is not the same as the poll creator or there is no current user, throws an exception
			User u = AuthBackend.getCurrentUser();
			if (u == null || originalvote.user.id != u.id) {
		        throw new UnauthorizedException();
		    }

			//Takes the edited VoteJSON and creates a new Vote object with this VoteJSON.
			CreateVoteRequest req = GsonHelper.fromJson(request.body, CreateVoteRequest.class);
            Vote editedvote = Vote.fromJson(req.vote);
            
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
			
			if (vote == null) {
				throw new NotFoundException();
			}
			
	        //If current user is not the same as the poll creator or there is no current user, throws an exception
			User u = AuthBackend.getCurrentUser();
			if (u == null || vote.user.id != u.id) {
		        throw new UnauthorizedException();
		    }
			
			//Deletes the Vote from the DataBase and creates an empty VoteJSON for the response.
			vote.delete();

			renderJSON(new EmptyResponse().toJson());
		} catch (Exception e) {
			renderException(e);
		}
	}
}
