package femr.ui.controllers;

import play.mvc.Controller;
import play.mvc.Result;

public class TriageController extends Controller {
    public static Result index(){
        return ok(femr.ui.views.html.triage.index.render());
    }


}
