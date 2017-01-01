package femr.ui.controllers;

import play.mvc.Controller;
import play.mvc.Result;

public class ApplicationController extends Controller {
    public Result removeTrailingSlash(String path) {
        return movedPermanently("/" + path);
    }
}
