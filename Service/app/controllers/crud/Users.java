package controllers.crud;

import controllers.CRUD;
import controllers.Secure;
import play.mvc.With;

/**
 * Controller which takes care of users.
 * @author OpenARMS Service team
 */
@With(Secure.class)
@CRUD.For(models.User.class)
public class Users extends CRUD {

}
