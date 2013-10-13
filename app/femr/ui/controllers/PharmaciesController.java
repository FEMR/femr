package femr.ui.controllers;

import play.mvc.Controller;
import play.mvc.Result;
import femr.ui.views.html.pharmacies.index;

public class PharmaciesController extends Controller {
    public static Result index(){
        return ok(index.render());
    }
}
