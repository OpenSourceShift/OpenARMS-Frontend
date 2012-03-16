package models;

import play.db.jpa.Model;

public class Poll extends Model {
	public String token;
	public String question;
	public String[] answers;
	public boolean multipleAllowed;
	public String email;
}
