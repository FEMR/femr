package edu.wayne.femr.controllers;

import edu.wayne.femr.views.html.home.index;
import play.mvc.Controller;
import play.mvc.Result;

public class Home extends Controller {

    public Result index() {
        return ok(index.render("fEMR - The free EMR"));
    }
}