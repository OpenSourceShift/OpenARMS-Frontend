/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package notifiers;

import play.mvc.*;
import models.*;

/**
 * Class to notify users via email.
 * @author OpenARMS Service Team
 */
public class MailNotifier extends Mailer {

    public static void sendAdminLink(Poll question) {
        setSubject("Your admin link");
        // TODO: Add the email for the user that owns the poll.
        //addRecipient(question.email);
        setFrom("OpenARMS.dk <no-reply@openarms.dk>");
        System.out.println("question: " + question);
        if (question != null) {
            send(question);
        }
    }

    public static void sendPollIDLink(Poll question) {
        setSubject("Your poll link");
        // TODO: Add the email for the user that owns the poll.
        //addRecipient(question.email);
        setFrom("OpenARMS.dk <no-reply@openarms.dk>");
        System.out.println(question);
        if (question != null) {
            send(question);
        }
    }
    
    /**
     * Method that sends a new password to the user
     * @param user the user that requested password reset     
     */
    public static void sendPassword(User user, String password) {
        setSubject("Your new password");
        addRecipient(user.email);
        setFrom("OpenARMS.dk <no-reply@openarms.dk>");
        send("Your new password: " + password);
    }
}
