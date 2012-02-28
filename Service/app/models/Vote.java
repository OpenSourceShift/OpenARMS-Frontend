package models;

import javax.persistence.*;
import play.db.jpa.*;

/**
 * Model class that represents vote for an answer. Is connected with one
 * voting round and there is a counter which increments by voting for a question.
 * @author OpenARS Server API team
 */
@Entity
public class Vote extends Model {

    @ManyToOne
    public Choice answer;
    public int count;
    @ManyToOne
    public PollInstance instance;

    /**
     * @param answer Represents answer that this vote should belong to
     * @param count Count of votes for answer provided
     * @param instance Poll instance this vote should belong to
     */
    public Vote(Choice answer, int count, PollInstance instance) {
        this.answer = answer;
        this.count = count;
        this.instance = instance;
    }

    /**
     * @param answer Represents answer that this vote should belong to
     * @param instance Poll instance this vote should belong to
     */
    public Vote(Choice answer, PollInstance instance) {
        this(answer, 1, instance);
    }

}
