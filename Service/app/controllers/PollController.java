package controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

import models.Choice;
import models.Poll;
import api.helpers.GsonHelper;
import notifiers.MailNotifier;
import api.Response.CreatePollResponse;
import api.deprecated.CreateResponseJSON;
import api.deprecated.QuestionJSON;
import play.mvc.Controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import api.deprecated.ActivationJSON;
import api.deprecated.BaseJSON;
import api.deprecated.CreateResponseJSON;
import api.deprecated.QuestionJSON;
import api.entities.PollJSON;

public class PollController extends APIController {

	public static void create() {
        try {
        	BufferedReader reader = new BufferedReader(new InputStreamReader(request.body));
        	
            String json = reader.readLine();
            PollJSON polljson = GsonHelper.fromJson(json, PollJSON.class);
            Poll poll = Poll.fromJson(polljson);
            
            // generate data and save question, try until we have unique poll ID
            do {
                poll.token = String.valueOf(new Random(System.currentTimeMillis()).nextInt(999999));
            } while (!Poll.find("byToken", poll.token).fetch().isEmpty());
                        
            poll.save();
            
            CreatePollResponse r = new CreatePollResponse(poll);
        	String jsonresponse = GsonHelper.toJson(r);
        	renderJSON(jsonresponse);

        } catch (Exception e) {
            renderException(e);
        }
    }
	
	public static void retrieve () {
		String pollid = params.get("id");
		
		Poll poll = Poll.find("byPollID", pollid).first();
		
		if (poll == null) {
		    renderJSON("The question does not exist!");
		}
		
		CreatePollResponse r = new CreatePollResponse(poll);
		String jsonresponse = GsonHelper.toJson(r);
		
		renderJSON(jsonresponse);
	}
	
	public static void edit () {
		BufferedReader reader = new BufferedReader(new InputStreamReader(request.body));
		String pollid = params.get("id");
		Poll originalpoll = Poll.find("byPollID", pollid).first();
		
		try {
        	
            String json = reader.readLine();
            PollJSON polljson = GsonHelper.fromJson(json, PollJSON.class);
            Poll editedpoll = Poll.fromJson(polljson);
            
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
            
            CreatePollResponse r = new CreatePollResponse(originalpoll);
        	String jsonresponse = GsonHelper.toJson(r);
        	renderJSON(jsonresponse);
            
		} catch (IOException ex) {
            ex.printStackTrace();
            renderJSON(new String());
        }
	}
	
	public static void delete () {
		String pollid = params.get("id");
		Poll poll = Poll.find("byPollID", pollid).first();
		poll.delete();
		
		poll.question = null;
		poll.reference = null;
		poll.choices = null;
		
		CreatePollResponse r = new CreatePollResponse(poll);
    	String jsonresponse = GsonHelper.toJson(r);
    	renderJSON(jsonresponse);
	}
}
