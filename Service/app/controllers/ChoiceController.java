package controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import models.Choice;
import models.Poll;
import api.Response.CreateChoiceResponse;
import api.Response.CreatePollResponse;
import api.entities.ChoiceJSON;
import api.entities.PollJSON;
import api.helpers.GsonHelper;
import play.mvc.Controller;

public class ChoiceController extends APIController {

	public static void create() {
		BufferedReader reader = new BufferedReader(new InputStreamReader(request.body));
		
        try {
        	
            String json = reader.readLine();
            ChoiceJSON choicejson = GsonHelper.fromJson(json, ChoiceJSON.class);
            Choice choice = Choice.fromJson(choicejson);
            choice.save();
            CreateChoiceResponse r = new CreateChoiceResponse(choice);
        	String jsonresponse = GsonHelper.toJson(r);
        	renderJSON(jsonresponse);
	
        } catch (IOException ex) {
            ex.printStackTrace();
            renderJSON(new String());
        }
        
    }
	
	public static void retrieve() {
		String choiceid = params.get("id");
		
		Choice choice = Choice.find("byChoiceID", choiceid).first();
		
		if (choice == null) {
		    renderJSON("The Choice does not exist!");
		}
		
		CreateChoiceResponse r = new CreateChoiceResponse(choice);
		String jsonresponse = GsonHelper.toJson(r);
		
		renderJSON(jsonresponse);
	}
	
	public static void edit () {
		BufferedReader reader = new BufferedReader(new InputStreamReader(request.body));
		String choiceid = params.get("id");
		Choice originalchoice = Choice.find("byChoiceID", choiceid).first();
		
		try {
        	
            String json = reader.readLine();
            ChoiceJSON choicejson = GsonHelper.fromJson(json, ChoiceJSON.class);
            Choice editedchoice = Choice.fromJson(choicejson);

            originalchoice.text = editedchoice.text;
            
            originalchoice.save();

            CreateChoiceResponse r = new CreateChoiceResponse(originalchoice);
        	String jsonresponse = GsonHelper.toJson(r);
        	renderJSON(jsonresponse);
            
		} catch (IOException ex) {
            ex.printStackTrace();
            renderJSON(new String());
        }
	}
	
	public static void delete () {
		String choiceid = params.get("id");
		Choice choice = Choice.find("byChoiceID", choiceid).first();
		choice.delete();
		
		choice.id = null;
		choice.poll = null;
		choice.text = null;
		
		CreateChoiceResponse r = new CreateChoiceResponse(choice);
    	String jsonresponse = GsonHelper.toJson(r);
    	renderJSON(jsonresponse);
	}

}
