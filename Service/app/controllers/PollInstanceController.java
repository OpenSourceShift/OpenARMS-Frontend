package controllers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.List;
import java.util.Random;

import play.Logger;
import play.db.helper.SqlQuery;

import controllers.APIController.NotFoundException;
import controllers.APIController.UnauthorizedException;

import models.Choice;
import models.Poll;
import models.PollInstance;
import models.User;
import models.Vote;
import api.requests.CreatePollInstanceRequest;
import api.requests.CreateVoteRequest;
import api.requests.UpdatePollInstanceRequest;
import api.requests.VoteOnPollInstanceRequest;
import api.responses.CreatePollInstanceResponse;
import api.responses.CreatePollResponse;
import api.responses.CreateVoteResponse;
import api.responses.EmptyResponse;
import api.responses.ReadPollInstanceResponse;
import api.responses.UpdatePollInstanceResponse;
import api.entities.PollInstanceJSON;
import api.entities.PollJSON;
import api.entities.VoteSummaryJSON;
import api.helpers.GsonHelper;

/**
 * Class that manages the responses in the API for PollsInstances.
 * @author OpenARMS Service Team
 *
 */
public class PollInstanceController extends APIController  {
	
	/**
	 * Method that saves a new PollInstance in the DataBase.
	 */
	public static void create() {
        try {
	    	//Takes the PollInstanceJSON and creates a new PollInstance object with this PollInstanceJSON.
	        CreatePollInstanceRequest req = GsonHelper.fromJson(request.body, CreatePollInstanceRequest.class);
	        PollInstance pollinstance = PollInstance.fromJson(req.pollInstance);
	      	if(pollinstance.poll == null) {
		        throw new NotFoundException("The poll_id references a non-exsistant poll.");
	        }
	        //If current user is not the same as the poll creator or there is no current user, throws an exception
			User u = AuthBackend.getCurrentUser();
			System.out.println("Logged in with this user: "+u.email+" ("+u.id+")");
			// TODO: Check the null values along the way
			AuthBackend.requireUser(pollinstance.poll.admin);
			
	        pollinstance.save();
	        
	        //Creates the PollInstanceJSON Response.
	        CreatePollInstanceResponse r = new CreatePollInstanceResponse(pollinstance.toJson());
	    	String jsonresponse = GsonHelper.toJson(r);
	    	renderJSON(jsonresponse);
        	
		} catch (Exception e) {
			renderException(e);
		}
	}
	
	/**
	 * Method that gets a PollInstance from the DataBase.
	 */
	private static void retrieve(PollInstance pi) throws Exception {
		//Takes the PollInstance from the DataBase.
		
		//Creates the PollInstanceJSON Response.
		ReadPollInstanceResponse r = new ReadPollInstanceResponse(pi.toJson());
		
		r.pollinstance.votes = new LinkedList<VoteSummaryJSON>();
		Logger.debug("We have in total %d votes in the db.", Vote.count());
		Logger.debug("This poll instance (#%d) has %d votes.", pi.id, Vote.find("byPollInstance.id", pi.id).fetch().size());
    	// Create vote summaries for all votes.
		long vote_count = 0;
    	for(Choice c: pi.poll.choices) {
    		VoteSummaryJSON summary = new VoteSummaryJSON();
			summary.choice_id = c.id;
			summary.choice_text = c.text;
			summary.count = Vote.count("pollInstance = ? and choice = ?", pi, c);
			vote_count += summary.count;
			// Add it to the result.
    		r.pollinstance.votes.add(summary);
    	}
    	r.pollinstance.vote_count = vote_count;
		
		String jsonresponse = GsonHelper.toJson(r);

		renderJSON(jsonresponse);
	}

	/**
	 * Method that gets a PollInstance from the DataBase.
	 */
	public static void retrieve () {
		try {

			PollInstance pollinstance = PollInstance.find("byID", params.get("id")).first();

			if (pollinstance == null) {
				throw new NotFoundException();
			} else {
				retrieve(pollinstance);
			}
		} catch (Exception e) {
			renderException(e);
		}
	}
	/**
	 * Method that gets a PollInstance by a token from the DataBase.
	 */
	public static void retrieveByToken () {
		try {
			String token = params.get("token");
	
			PollInstance pollinstance = null;
			//Takes the PollInstance from the DataBase.
			List<PollInstance> pollinstances = PollInstance.find("byPoll.token", token).fetch();
			Date lastdate = new Date(0);
			
			// FIXME: This should be rewritten to do "orderby" in the database, instead of looping through stuff here
			for(PollInstance pi: pollinstances ) {
				if (lastdate.before(pi.endDateTime)) {
					lastdate = pi.endDateTime;
					pollinstance = pi;
				}
			}
			
			if (pollinstance == null) {
				throw new NotFoundException();
			} else {
				retrieve(pollinstance);
			}
			
		} catch (Exception e) {
			renderException(e);
		}
	}
	
	/**
	 * Method that edits a PollInstance already existing in the DataBase.
	 */
	public static void edit () {
		try {
			String pollinstanceid = params.get("id");
	
			//Takes the PollInstance from the DataBase.
			PollInstance originalpollinstance = PollInstance.find("byID", pollinstanceid).first();
			
			if (originalpollinstance == null) {
				throw new NotFoundException();
			}
			
	        //If current user is not the same as the poll creator or there is no current user, throws an exception
			AuthBackend.requireUser(originalpollinstance.poll.admin);

			//Takes the edited PollInstanceJSON and creates a new PollInstance object with this PollInstanceJSON.
			UpdatePollInstanceRequest req = GsonHelper.fromJson(request.body, UpdatePollInstanceRequest.class);
            PollInstance editedpollinstance = PollInstance.fromJson(req.pollInstance);
            
            //Changes the old fields for the new ones.
            if (editedpollinstance.startDateTime != null) {
            	originalpollinstance.startDateTime = editedpollinstance.startDateTime;
            }
            if (editedpollinstance.endDateTime != null) {
            	originalpollinstance.endDateTime = editedpollinstance.endDateTime;
            }
            if (editedpollinstance.poll != null) {
            	originalpollinstance.poll = editedpollinstance.poll;
            }
            if (editedpollinstance.votes != null) {
            	originalpollinstance.votes = editedpollinstance.votes;
            }
            
            originalpollinstance.save();
            
            //Creates the PollInstanceJSON Response.
            UpdatePollInstanceResponse r = new UpdatePollInstanceResponse(originalpollinstance.toJson());
        	String jsonresponse = GsonHelper.toJson(r);
        	renderJSON(jsonresponse);
            
		} catch (Exception e) {
			renderException(e);
		}
	}
	
	/**
	 * Method that closes a PollInstance before the end time has run out.
	 */
	public static void close () {
		try {
			
			String pollinstanceid = params.get("id");
			
			//Takes the PollInstance from the DataBase.
			PollInstance pollinstance = PollInstance.find("byID", pollinstanceid).first();
			
			if (pollinstance == null) {
				throw new NotFoundException();
			}
			
			AuthBackend.requireUser(pollinstance.poll.admin);
			
			//Closes the PollInstance and save the changes in the DataBase.
			pollinstance.closePollInstance();
			pollinstance.save();
			
			renderJSON(new EmptyResponse().toJson());
		} catch (Exception e) {
			renderException(e);
		}
	}

	/**
	 * Method that deletes a PollInstance existing in the DataBase.
	 */
	public static void delete () {
		try {
			String pollinstanceid = params.get("id");
	
			//Takes the PollInstance from the DataBase.
			PollInstance pollinstance = PollInstance.find("byID", pollinstanceid).first();
			
			if (pollinstance == null) {
				throw new NotFoundException();
			}

			AuthBackend.requireUser(pollinstance.poll.admin);
			
			//Deletes the PollInstance from the DataBase and creates an empty PollInstanceJSON for the response.
			pollinstance.delete();

			renderJSON(new EmptyResponse().toJson());
		} catch (Exception e) {
			renderException(e);
		}
	}
	public static void vote() {
        try {
	    	//Takes the VoteJSON and creates a new Vote object with this VoteJSON.
	        VoteOnPollInstanceRequest req = GsonHelper.fromJson(request.body, VoteOnPollInstanceRequest.class);
			User u = AuthBackend.getCurrentUser();
	        req.vote.userid = u.id;
	        VoteController.create(req.vote);
	        Vote vote = Vote.fromJson(req.vote);
          
	        //If current user is not the same as the poll creator or there is no current user, throws an exception
			//User u = AuthBackend.getCurrentUser();
			// TODO: Check if the user has already voted.
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
}
