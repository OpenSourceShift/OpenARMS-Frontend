package controllers;

import play.Logger;
import play.mvc.Http;
import models.Choice;
import models.User;
import api.helpers.GsonHelper;
import api.requests.CreateChoiceRequest;
import api.requests.UpdateChoiceRequest;
import api.responses.CreateChoiceResponse;
import api.responses.EmptyResponse;
import api.responses.ReadChoiceResponse;
import api.responses.UpdateChoiceResponse;

/**
 * Class that manages the responses in the API for Choices.
 * @author OpenARMS Service Team
 *
 */

public class ChoiceController extends APIController {

	/**
	 * Method that saves a new Choice in the DataBase.
	 */
	public static void create() throws Exception {
    	//Takes the ChoiceJSON and creates a new Choice object with this ChoiceJSON.
        CreateChoiceRequest req = GsonHelper.fromJson(request.body, CreateChoiceRequest.class);
        Choice choice = Choice.fromJson(req.choice);
        
        Logger.debug("ChoiceController.create() called ...");
        notFoundIfNull(choice.poll);
        
		requireUser(choice.poll.admin);
        
        choice.save();
        
		response.status = Http.StatusCode.CREATED;
    	renderJSON(new CreateChoiceResponse(choice.toJson()));
    }
	/**
	 * Method that gets a Choice from the DataBase.
	 * @throws Exception 
	 */
	public static void retrieve() throws Exception {
		String choiceid = params.get("id");

		//Takes the Choice from the DataBase.
		Choice choice = Choice.find("byID", choiceid).first();
	
		notFoundIfNull(choice);
		
		//Creates the ChoiceJSON Response.
		ReadChoiceResponse r = new ReadChoiceResponse(choice.toJson());
		String jsonresponse = GsonHelper.toJson(r);

		renderJSON(jsonresponse);
	}

	/**
	 * Method that edits a Choice already existing in the DataBase.
	 * @throws Exception 
	 */
	public static void edit () throws Exception {
		String choiceid = params.get("id");
		
		//Takes the Choice from the DataBase.
		Choice originalchoice = Choice.find("byID", choiceid).first();
		
		notFoundIfNull(originalchoice);
		
		requireUser(originalchoice.poll.admin);

		//Takes the edited ChoiceJSON and creates a new Choice object with this ChoiceJSON.
		UpdateChoiceRequest req = GsonHelper.fromJson(request.body, UpdateChoiceRequest.class);
        Choice editedchoice = Choice.fromJson(req.choice);

        //Changes the old text field for the new one.
        originalchoice.text = editedchoice.text;
        
        originalchoice.save();

        //Creates the ChoiceJSON Response.
        UpdateChoiceResponse r = new UpdateChoiceResponse(originalchoice.toJson());
    	String jsonresponse = GsonHelper.toJson(r);
    	renderJSON(jsonresponse);
	}

	/**
	 * Method that deletes a Choice existing in the DataBase.
	 * @throws Exception 
	 */
	public static void delete () throws Exception {
		String choiceid = params.get("id");
		
		//Takes the Choice from the DataBase.
		Choice choice = Choice.find("byID", choiceid).first();
		
		notFoundIfNull(choice);

		requireUser(choice.poll.admin);

		choice.delete();

		renderJSON(new EmptyResponse().toJson());
	}

}
