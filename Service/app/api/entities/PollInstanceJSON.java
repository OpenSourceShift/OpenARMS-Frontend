package api.entities;

import java.util.Date;
import java.util.List;

public class PollInstanceJSON extends BaseModelJSON {
	public Long poll_id;
	public String poll_question;
	public String poll_token;
	public String time_remaining;
	public Boolean closed;
	public Long id;
	public Date start;
    public Date end;
    public Long vote_count;
    public List<VoteSummaryJSON> votes;
}
