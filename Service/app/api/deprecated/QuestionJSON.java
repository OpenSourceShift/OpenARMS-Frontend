/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package api.deprecated;

import models.Poll;

/**
 * JSON that holds all information information about question
 * @author OpenARS Server API team
 */
@Deprecated
public class QuestionJSON {

    public String token;
    public long questionID;
    public String question;
    public String[] answers;
    public boolean multipleAllowed;
    public String responderID;
    public int duration;
    public String email;

    /**
     * @param question Question object (model) to create JSON from
     */
    public QuestionJSON(Poll question) {
        this.token = question.token;
        this.questionID = question.id;
        this.answers = getAnswersArray(question);
        this.question = question.question;
        this.duration = question.timeRemaining();
        this.multipleAllowed = question.multipleAllowed;
    }

    /**
     * Gets all answers from model to be inserted into QuestionJSON
     * @param question
     * @return
     */
    public final String[] getAnswersArray(Poll question) {
        int size = question.choices.size();
        String[] answersArray = new String[size];
        for (int i = 0; i < size; i++) {
            answersArray[i] = question.choices.get(i).text;
        }
        return answersArray;
    }

    /**
     * Makes Question object (model) from JSON object
     * @return
     */
    public Poll makeModelFromJSON() {
        Poll q = new Poll(token, question, multipleAllowed);
        return q;
    }
}
