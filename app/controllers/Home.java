package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.home.index;

public class Home extends Controller {
    public static Result index() {
        return ok(index.render("fEMR - The free EMR"));
    }
}