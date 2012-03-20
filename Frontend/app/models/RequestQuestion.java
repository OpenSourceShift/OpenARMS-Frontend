package models;

import play.db.jpa.Model;
@Deprecated
public class RequestQuestion extends Model {
	protected int token = -1;
	protected int responderId = -1;
}
