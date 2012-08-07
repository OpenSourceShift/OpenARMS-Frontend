package controllers.crud;

import controllers.CRUD;
import controllers.Secure;
import play.*;
import play.mvc.*;

/**
 * Controller which takes care of votes
 * @author OpenARMS Service team
 */
@With(Secure.class)
@CRUD.For(models.Vote.class)
public class Votes extends CRUD {

}
