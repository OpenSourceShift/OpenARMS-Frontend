package controllers;

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
	public static void create() {
        try {
        	//Takes the ChoiceJSON and creates a new Choice object with this ChoiceJSON.
            CreateChoiceRequest req = GsonHelper.fromJson(request.body, CreateChoiceRequest.class);
            Choice choice = Choice.fromJson(req.choice);
            
            if (choice.poll == null) {
            	throw new NotFoundException();
            }
            
	        //If current user is not the same as the poll creator or there is no current user, throws an exception
			User u = AuthBackend.getCurrentUser();
			if (u == null || choice.poll.admin.id != u.id) {
		        throw new UnauthorizedException();
		    }
            
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
			ReadChoiceResponse r = new ReadChoiceResponse(choice.toJson());
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
			
	        //If current user is not the same as the poll creator or there is no current user, throws an exception
			User u = AuthBackend.getCurrentUser();
			if (u == null || originalchoice.poll.admin.id != u.id) {
		        throw new UnauthorizedException();
		    }

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
			
	        //If current user is not the same as the poll creator or there is no current user, throws an exception
			User u = AuthBackend.getCurrentUser();
			// TODO: Check for null's along the choice.poll.admin.id chain.
			if (u == null || choice.poll.admin.id != u.id) {
		        throw new UnauthorizedException();
		    }
	
			choice.delete();
	
			renderJSON(new EmptyResponse().toJson());
		} catch (Exception e) {
			renderException(e);
		}
	}

}
