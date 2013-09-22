package femr.ui.controllers;

import play.mvc.Controller;
import play.mvc.Result;

public class MedicalController extends Controller {
    public static Result index() {
        return ok(femr.ui.views.html.medical.index.render());
    }
}
