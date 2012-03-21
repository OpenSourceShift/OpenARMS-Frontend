package api.entities;

import java.util.Date;
import java.util.List;

public class PollInstanceJSON extends BaseModelJSON {
	public Long poll_id;
	public Long id;
	public Date startDateTime;
    public Date endDateTime;
    public List<VoteSummaryJSON> votes;
}
