package controllers.crud;

import controllers.CRUD;
import controllers.Secure;
import play.*;
import play.mvc.*;

/**
 * Controller which takes care of choices
 * @author OpenARMS Service team
 */
@With(Secure.class)
@CRUD.For(models.Choice.class)
public class Choices extends CRUD {

}
