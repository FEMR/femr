package femr.ui.controllers;

import play.mvc.Controller;
import play.mvc.Result;

public class PharmaciesController extends Controller {
    public static Result index(){
        return ok(femr.ui.views.html.pharmacies.index.render());
    }
}
