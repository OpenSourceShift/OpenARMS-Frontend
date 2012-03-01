package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import play.db.jpa.Model;

/**
 * Model class for a possible answer linked to a question.
 * @author OpenARS Server API team
 */
@Entity
public class Choice extends Model {
	private static final long serialVersionUID = 7558864274526935981L;
	/**
	 * The poll that this is a choice for.
	 */
	@ManyToOne
	public Poll poll;
	/**
	 * The human understandable text describing the choice.
	 */
	public String text;
	/**
	 * The votes that has used this choice when voting for an instance of a poll.
	 */
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "answer")
	public List<Vote> votes;

	public Choice(Poll poll, String text) {
		this.poll = poll;
		this.text = text;
	}

	/**
	 * Used to determine whether this answer was voted for in the last/current voting round
	 * @return boolean true when it was voted for already or false otherwise
	 */
	public boolean inLatestPollInstance() {
		PollInstance pi = poll.getLatestInstance();
		List<Vote> latestVotes = pi.votes;
		for (Vote vote : latestVotes) {
			if (vote.choice.equals(this)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns list of votes that are associated with the last/current voting round.
	 * @return List<Vote> list of latest votes
	 */
	public List<Vote> latestVotes() {
		PollInstance lastRound = poll.getLatestInstance();
		List<Vote> latestVotes = new ArrayList<Vote>();

		if (!votes.isEmpty()) {
			for (Vote vote : votes) {
				if (vote.pollInstance.equals(lastRound)) {
					latestVotes.add(vote);
				}
			}
		}
		return latestVotes;
	}
}
