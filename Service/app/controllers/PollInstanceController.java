package controllers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Random;

import controllers.APIController.NotFoundException;

import models.Poll;
import models.PollInstance;
import api.requests.CreatePollInstanceRequest;
import api.responses.CreatePollInstanceResponse;
import api.responses.CreatePollResponse;
import api.entities.PollInstanceJSON;
import api.entities.PollJSON;
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
	public static void retrieve () {
		try {
			String pollinstanceid = params.get("id");
	
			//Takes the PollInstance from the DataBase.
			PollInstance pollinstance = PollInstance.find("byID", pollinstanceid).first();
	
			if (pollinstance == null) {
				throw new NotFoundException();
			}
			
			//Creates the PollInstanceJSON Response.
			CreatePollInstanceResponse r = new CreatePollInstanceResponse(pollinstance.toJson());
			String jsonresponse = GsonHelper.toJson(r);
	
			renderJSON(jsonresponse);
			
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

			//Takes the edited PollInstanceJSON and creates a new PollInstance object with this PollInstanceJSON.
			CreatePollInstanceRequest req = GsonHelper.fromJson(request.body, CreatePollInstanceRequest.class);
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
            CreatePollInstanceResponse r = new CreatePollInstanceResponse(originalpollinstance.toJson());
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
			
			//Closes the PollInstance and save the changes in the DataBase.
			pollinstance.closePollInstance();
			pollinstance.save();
			
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
			
			//Deletes the PollInstance from the DataBase and creates an empty PollInstanceJSON for the response.
			pollinstance.delete();
	
			pollinstance.endDateTime = null;
			pollinstance.startDateTime = null;
			pollinstance.poll = null;
			pollinstance.votes = null;
			
			//Creates the PollInstanceJSON Response.
			CreatePollInstanceResponse r = new CreatePollInstanceResponse(pollinstance.toJson());
			String jsonresponse = GsonHelper.toJson(r);
			renderJSON(jsonresponse);
			
		} catch (Exception e) {
			renderException(e);
		}
	}
}
