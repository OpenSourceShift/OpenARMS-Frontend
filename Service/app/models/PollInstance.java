package models;

import java.util.Date;
import java.util.List;
import javax.persistence.*;
import play.db.jpa.*;

/**
 * Voting round model. Having this enables us to have several voting rounds of a 
 * question. The result returned to the clients should always be the latest.
 * @author OpenARS Server API team
 */
@Entity
public class PollInstance extends Model implements Comparable<PollInstance> {
	private static final long serialVersionUID = -6371181092845400924L;
	
	/**
	 * The votes that students have placed on this poll.
	 */
    @OneToMany(mappedBy = "pollInstance")
    public List<Vote> votes;
	/**
	 * The date and time where this poll starts.
	 */
    public Date startDateTime;
    /**
	 * The date and time where this poll ends.
	 */
    public Date endDateTime;
    /**
	 * The Poll that this is an instance of.
	 */
    @ManyToOne
    public Poll poll;

    public PollInstance(int duration, Poll poll) {
        this.poll = poll;
        this.startDateTime = new Date(System.currentTimeMillis());
        this.endDateTime = new Date(startDateTime.getTime() + duration * 1000);
    }

    public int compareTo(PollInstance other) {
        return this.endDateTime.compareTo(other.endDateTime);
    }

    @Override
    public boolean equals(Object other) {
        if (other == null || !(other instanceof PollInstance)) {
            return false;
        }
        PollInstance vr = (PollInstance) other;
        return this.endDateTime.equals(vr.endDateTime);
    }

    /*
     * TODO: Consider if this is needed.
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + (this.votes != null ? this.votes.hashCode() : 0);
        hash = 71 * hash + (this.endDateTime != null ? this.endDateTime.hashCode() : 0);
        hash = 71 * hash + (this.poll != null ? this.poll.hashCode() : 0);
        return hash;
    }
}
