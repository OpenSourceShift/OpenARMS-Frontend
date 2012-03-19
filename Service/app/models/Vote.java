package models;

import javax.persistence.*;

import api.entities.Jsonable;
import api.entities.VoteJSON;
import play.db.jpa.*;

/**
 * Model class that represents vote for an answer. Is connected with one
 * voting round and there is a counter which increments by voting for a question.
 * @author OpenARS Server API team
 */
@Entity
public class Vote extends Model implements Jsonable {
	private static final long serialVersionUID = -4255311415057973971L;
	
	/**
	 * The Choice selected by the student.
	 */
    @ManyToOne
    public Choice choice;
    /**
     * The PollInstance that this vote has been coupled with.
     */
    @ManyToOne
    public PollInstance pollInstance;


    /**
     * Constructs a new Vote, only one Vote is created pr. Choice and PollInstance
     * the count field is used to represent multiple votes for the same Choice-PollInstance-pair.
     * @param choice Represents choice that this vote should belong to
     * @param count Count of votes for choice provided
     * @param instance Poll instance this vote should belong to
     */
    public Vote(Choice choice, PollInstance instance) {
        this.choice = choice;
        this.pollInstance = instance;
    }



	public VoteJSON toJson() {
		// TODO Auto-generated method stub
		return null;
	}

	public static Vote fromJson(VoteJSON v) {
		Choice choice = Choice.find("byID", v.choiceid).first();
		PollInstance pollinstance = PollInstance.find("byID", v.pollInstanceid).first();
		Vote result = new Vote (choice, pollinstance);
		return result;
	}

}
