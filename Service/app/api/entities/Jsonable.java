package api.entities;

import play.db.jpa.Model;

public interface Jsonable {
	public BaseJSON toJson();
}
