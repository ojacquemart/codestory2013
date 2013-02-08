package controllers;

import play.*;
import play.mvc.*;
import play.data.*;
import play.data.validation.Constraints.*;

import java.util.*;

import views.html.*;

import jaja.*;

public class SimpleApplication extends Controller {

      public static Result javajaja() {
    	System.out.println(request().body());
    	return ok("");
    }

}