package models;

import play.db.jpa.Model;

@Deprecated
public class QuestionAnswer extends Model {
	protected int token = -1;
	protected int questionId = -1;
	protected String[] answers;
	protected int responderId = -1;
}
