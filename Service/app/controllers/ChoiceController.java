package controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import controllers.APIController.NotFoundException;

import models.Choice;
import models.Poll;
import api.requests.CreateChoiceRequest;
import api.responses.CreateChoiceResponse;
import api.responses.CreatePollResponse;
import api.entities.ChoiceJSON;
import api.entities.PollJSON;
import api.helpers.GsonHelper;
import play.mvc.Controller;

/**
 * Class that manages the responses in the API for Choices.
 * @author OpenARMS Service Team
 *
 */

public class ChoiceController extends APIController {

	/**
	 * Method that saves a new Choice in the DataBase.
	 */
	public static void create() {
        try {
        	//Takes the ChoiceJSON and creates a new Choice object with this ChoiceJSON.
            CreateChoiceRequest req = GsonHelper.fromJson(request.body, CreateChoiceRequest.class);
            Choice choice = Choice.fromJson(req.choice);
            
            choice.save();
            
            //Creates the ChoiceJSON Response.
            CreateChoiceResponse r = new CreateChoiceResponse(choice.toJson());
        	String jsonresponse = GsonHelper.toJson(r);
        	renderJSON(jsonresponse);
	
        } catch (Exception e) {
			renderException(e);
		}
        
    }
	/**
	 * Method that gets a Choice from the DataBase.
	 */
	public static void retrieve() {
		try {
			String choiceid = params.get("id");
	
			//Takes the Choice from the DataBase.
			Choice choice = Choice.find("byID", choiceid).first();
		
			if (choice == null) {
				throw new NotFoundException();
			}
			
			//Creates the ChoiceJSON Response.
			CreateChoiceResponse r = new CreateChoiceResponse(choice.toJson());
			String jsonresponse = GsonHelper.toJson(r);
	
			renderJSON(jsonresponse);
			
		} catch (Exception e) {
			renderException(e);
		}
	}

	/**
	 * Method that edits a Choice already existing in the DataBase.
	 */
	public static void edit () {
		try {
			String choiceid = params.get("id");
			
			//Takes the Choice from the DataBase.
			Choice originalchoice = Choice.find("byID", choiceid).first();
			
			if (originalchoice == null) {
				throw new NotFoundException();
			}

			//Takes the edited ChoiceJSON and creates a new Choice object with this ChoiceJSON.
            CreateChoiceRequest req = GsonHelper.fromJson(request.body, CreateChoiceRequest.class);
            Choice editedchoice = Choice.fromJson(req.choice);

            //Changes the old text field for the new one.
            originalchoice.text = editedchoice.text;
            
            originalchoice.save();

            //Creates the ChoiceJSON Response.
            CreateChoiceResponse r = new CreateChoiceResponse(originalchoice.toJson());
        	String jsonresponse = GsonHelper.toJson(r);
        	renderJSON(jsonresponse);
            
		} catch (Exception e) {
			renderException(e);
		}
	}

	/**
	 * Method that deletes a Choice existing in the DataBase.
	 */
	public static void delete () {
		try {
			String choiceid = params.get("id");
			
			//Takes the Choice from the DataBase.
			Choice choice = Choice.find("byID", choiceid).first();
			
			if (choice == null) {
				throw new NotFoundException();
			}
	
			choice.delete();
	
			choice.id = null;
			choice.poll = null;
			choice.text = null;
	
			//Creates the ChoiceJSON Response.
			CreateChoiceResponse r = new CreateChoiceResponse(choice.toJson());
			String jsonresponse = GsonHelper.toJson(r);
			renderJSON(jsonresponse);
		
		} catch (Exception e) {
			renderException(e);
		}
	}

}
