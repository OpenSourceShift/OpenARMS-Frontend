package controllers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Random;

import models.Poll;
import models.PollInstance;
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
			BufferedReader reader = new BufferedReader(new InputStreamReader(request.body));
	
	    	//Takes the PollInstanceJSON and creates a new PollInstance object with this PollInstanceJSON.
	        String json = reader.readLine();
	        PollInstanceJSON pollinstancejson = GsonHelper.fromJson(json, PollInstanceJSON.class);
	        PollInstance pollinstance = PollInstance.fromJson(pollinstancejson);
	                    
	        pollinstance.save();
	        
	        //Creates the PollInstanceJSON Response.
	        CreatePollInstanceResponse r = new CreatePollInstanceResponse(pollinstance);
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
	
			//Creates the PollInstanceJSON Response.
			if (pollinstance == null) {
				renderJSON("The Poll Instance does not exist!");
			}
			
			CreatePollInstanceResponse r = new CreatePollInstanceResponse(pollinstance);
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
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(request.body));
			String pollinstanceid = params.get("id");
	
			//Takes the PollInstance from the DataBase.
			PollInstance originalpollinstance = PollInstance.find("byID", pollinstanceid).first();

			//Takes the edited PollInstanceJSON and creates a new PollInstance object with this PollInstanceJSON.
            String json = reader.readLine();
            PollInstanceJSON pollinstancejson = GsonHelper.fromJson(json, PollInstanceJSON.class);
            PollInstance editedpollinstance = PollInstance.fromJson(pollinstancejson);
            
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
            CreatePollInstanceResponse r = new CreatePollInstanceResponse(originalpollinstance);
        	String jsonresponse = GsonHelper.toJson(r);
        	renderJSON(jsonresponse);
            
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
			
			//Deletes the PollInstance from the DataBase and creates an empty PollInstanceJSON for the response.
			pollinstance.delete();
	
			pollinstance.endDateTime = null;
			pollinstance.startDateTime = null;
			pollinstance.poll = null;
			pollinstance.votes = null;
			
			//Creates the PollInstanceJSON Response.
			CreatePollInstanceResponse r = new CreatePollInstanceResponse(pollinstance);
			String jsonresponse = GsonHelper.toJson(r);
			renderJSON(jsonresponse);
			
		} catch (Exception e) {
			renderException(e);
		}
	}
}
