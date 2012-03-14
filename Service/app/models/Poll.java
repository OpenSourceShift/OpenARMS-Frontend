package models;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;
import javax.persistence.*;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import play.data.validation.Required;
import play.data.validation.Unique;
import play.db.jpa.*;

/**
 * Model class for a poll question. This is related to answer one-to-many
 * @author OpenARS Server API team
 */
@Entity
public class Poll extends Model {
	private static final long serialVersionUID = 5276961463864101032L;
	
	/**
	 * The set of charecters to use when generating admin keys.
	 */
	private static final String ADMINKEY_CHARSET = "0123456789abcdefghijklmnopqrstuvwxyz";
	/**
	 * The token of the poll (formerly known as pollID).
	 */
    @Required
    @Unique
    public String token;
    /**
     * The admin key, this is used to gain administrative access to the poll.
     */
    public String adminKey;
    /**
     * The email of the person that created the poll.
     */
    public String email;
    /**
     * The question that the poll states, ex. "What is 2+2?"
     */
    public String question;
    /**
     * Is it allowed to add multiple answers?
     */
    public boolean multipleAllowed;
    /**
     * All the possible choices associated with the poll.
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "poll")
    public List<Choice> choices;
    /**
     * All the concrete instances of the poll.
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "poll")
    public List<PollInstance> instances;

    /**
     * Constructs a new Poll object.
     * This does not save it to the database, use the save method to do this.
     * 
     * @param token
     * @param question Text of the question
     * @param multipleAllowed whether there are multiple options allowed or not
     * @param email e-mail address of the poll creator
     */
    public Poll(String token, String question, boolean multipleAllowed, String email) {
        this.token = token;
        this.question = question;
        this.multipleAllowed = multipleAllowed;
        this.email = email;
    }

    /**
     * Activates the question for provided number of seconds.
     * If the question is in active state, it changes the activation duration,
     * otherwise it creates new voting round.
     * @param duration number of seconds to activate the question for
     * @return activated Question object - does not have to be used
     */
    public Poll activateFor(int duration) {
        if (isActive()) {
            PollInstance latestInstance = getLatestInstance();
            latestInstance.startDateTime = new Date(System.currentTimeMillis());
            latestInstance.endDateTime = new Date(latestInstance.startDateTime.getTime() + duration * 1000);
            latestInstance.save();
        } else {
            new PollInstance(duration, this).save();
        }
        return this;
    }

    /**
     * Gets latest voting round if it exists or null otherwise.
     * TODO: Implement this in a faster way, using a hybernate query.
     * @return PollInstance The latest instance of the poll (based on endDateTime)
     */
    public PollInstance getLatestInstance() {
        if (instances.isEmpty()) {
            return null;
        }
        Collections.sort(instances);
        int lastIndex = instances.size() - 1;
        return instances.get(lastIndex);
    }

    /**
     * Generates random string of alphanumerical characters.
     * @param length int length of generated string
     * @return String generated string
     */
    public void generateAdminKey(int length) {
        Random rand = new Random(System.currentTimeMillis());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int pos = rand.nextInt(ADMINKEY_CHARSET.length());
            sb.append(ADMINKEY_CHARSET.charAt(pos));
        }
        adminKey = sb.toString();
    }

    /**
     * Gets array of answers as strings.
     * @return
     */
    public String[] getChoicesArray() {
        String[] array = new String[choices.size()];
        for (int i = 0; i < choices.size(); i++) {
            array[i] = choices.get(i).text;
        }
        return array;
    }

    /**
     * Can be used to determine if the question is activated or not
     * @return boolean activation status
     */
    public boolean isActive() {
        return timeRemaining() > 0;
    }

    /**
     * Returns remaining time for which the question is activated. This value
     * should be sent to the clients so that they can set the countdown. It is
     * also used by bethod isActive() to determine the activation state.
     * @return
     */
    public int timeRemaining() {
        PollInstance lastRound = getLatestInstance();
        if (lastRound == null) {
            return 0;
        }

        Date endTime = lastRound.endDateTime;
        Date currentTime = new Date(System.currentTimeMillis());

        int difference = (int) Math.ceil((endTime.getTime() - currentTime.getTime()) / 1000);
        return (difference > 0) ? difference : 0;
    }

    /**
     * Returns true when there has not been any voting done yet
     * @return true when there is no voting round
     */
    public boolean isFresh() {
        return getLatestInstance() == null;
    }

    /**
     * Gets vote counts as an array of integers. Used for statistics.
     * @return int[] array of vote counts / results
     */
    public int[] getVoteCounts() {
        int index = 0;
        int[] votes = new int[choices.size()];

        if (isFresh()) {
            return votes;
        }

        for (Choice choice : choices) {
            List<Vote> votesList = choice.latestVotes();
            if (votesList.isEmpty()) {
                votes[index] = 0;
            } else {
                votes[index] = votesList.get(0).count;
            }
            index++;
        }
        return votes;
    }

    @Override
    public String toString() {
        return "AdminKey: " + this.adminKey + " PollID: " + this.token + " id: " + this.id;
    }
}
