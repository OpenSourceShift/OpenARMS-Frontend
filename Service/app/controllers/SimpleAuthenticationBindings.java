package controllers;

import play.*;
import play.mvc.*;

/**
 * Controller which takes care of simple authentication passwords.
 * @author OpenARMS Service team
 */
@With(Secure.class)
public class SimpleAuthenticationBindings extends CRUD {

}
