package edu.wayne.femr.ui.controllers;

import edu.wayne.femr.ui.views.html.home.index;
import play.mvc.Controller;
import play.mvc.Result;

public class HomeController extends Controller {

    public Result index() {
        return ok(index.render());
    }
}