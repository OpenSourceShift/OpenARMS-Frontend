/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package api;

/**
 *
 * @author veri
 */
public class VoteJSON {

    private long pollID;
    private long questionID;
    private String[] answers;
    private String responderID;

    public VoteJSON(long pollID, long questionID, String[] answers, String responderID) {
        this.pollID = pollID;
        this.questionID = questionID;
        this.answers = answers;
        this.responderID = responderID;
    }

    public long getPollID() {
        return pollID;
    }

    public void setPollID(long pollID) {
        this.pollID = pollID;
    }

    public long getQuestionID() {
        return questionID;
    }

    public void setQuestionID(long questionID) {
        this.questionID = questionID;
    }

    public String[] getChoices() {
        return answers;
    }

    public void setAnswers(String[] answers) {
        this.setAnswers(answers);
    }

    public String getResponderID() {
        return responderID;
    }

    public void setResponderID(String responderID) {
        this.responderID = responderID;
    }

}
