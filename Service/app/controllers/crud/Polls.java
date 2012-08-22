package controllers.crud;

import controllers.CRUD;
import controllers.Secure;
import controllers.CRUD.For;
import play.*;
import play.mvc.*;

/**
 * Controller which takes care of polls
 * @author OpenARMS Service team
 */
@With(Secure.class)
@CRUD.For(models.Poll.class)
public class Polls extends CRUD {

}
