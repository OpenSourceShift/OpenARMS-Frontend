package api.deprecated.responses;

/**
 * @author veri
 */
public class VoteResponse {

    private boolean voteSuccessful;

    /**
     * @param voteSuccessful
     */
    public VoteResponse(boolean voteSuccessful) {
        this.voteSuccessful = voteSuccessful;
    }

    public boolean getStatus() {
        return voteSuccessful;
    }

    public void setStatus(boolean status) {
        this.voteSuccessful = status;
    }
}
