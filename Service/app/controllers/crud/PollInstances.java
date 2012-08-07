package controllers.crud; 

import controllers.CRUD;
import controllers.Secure;
import controllers.CRUD.For;
import play.*;
import play.mvc.*;

/**
 * Controller which takes care of poll instances
 * @author OpenARMS Service team
 */
@With(Secure.class)
@CRUD.For(models.PollInstance.class)
public class PollInstances extends CRUD {

}
