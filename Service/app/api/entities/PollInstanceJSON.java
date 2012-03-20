package api.entities;

import java.util.Date;
import java.util.List;

public class PollInstanceJSON extends BaseModelJSON {
	public Long poll_id;
	public Long id;
	public Date startDateTime;
    public Date endDateTime;
    public List<VoteJSON> votes;
	
    /*
	public PollInstanceJSON(Vote v) {
		// TODO: Fill this in
	}*/
}
