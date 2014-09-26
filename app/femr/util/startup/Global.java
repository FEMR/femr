/*
     fEMR - fast Electronic Medical Records
     Copyright (C) 2014  Team fEMR

     fEMR is free software: you can redistribute it and/or modify
     it under the terms of the GNU General Public License as published by
     the Free Software Foundation, either version 3 of the License, or
     (at your option) any later version.

     fEMR is distributed in the hope that it will be useful,
     but WITHOUT ANY WARRANTY; without even the implied warranty of
     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
     GNU General Public License for more details.

     You should have received a copy of the GNU General Public License
     along with fEMR.  If not, see <http://www.gnu.org/licenses/>. If
     you have any questions, contact <info@teamfemr.org>.
*/
package femr.util.startup;

import com.google.inject.Guice;
import com.google.inject.Injector;
import femr.common.dto.CurrentUser;
import femr.util.dependencyinjection.modules.BusinessLayerModule;
import femr.util.dependencyinjection.modules.DataLayerModule;
import femr.util.dependencyinjection.modules.UtilitiesModule;
import play.Application;
import play.GlobalSettings;
import play.mvc.*;
import play.mvc.Http.*;
import play.libs.F.*;

import static play.mvc.Results.*;

public class Global extends GlobalSettings {

    private static final Injector INJECTOR = createInjector();

    @Override
    public void onStart(Application app) {
        super.onStart(app);
        new DatabaseSeeder().seed();
    }

    @Override
    public <A> A getControllerInstance(Class<A> controllerClass) throws Exception {
        return INJECTOR.getInstance(controllerClass);
    }

    /*
    //it is called when throwing a runtime exception....
    @Override
    public Promise<SimpleResult> onError(RequestHeader request, Throwable t) {
        //String test = t.getCause().getMessage();
        return Promise.<SimpleResult>pure(internalServerError(
                femr.ui.views.html.errors.global.render()
        ));
    } */

    //used when a resource is not found
    @Override
    public Promise<Result> onBadRequest(RequestHeader request, String error) {
        return Promise.<Result>pure(internalServerError(
                femr.ui.views.html.errors.global.render()
        ));
    }

    /*
    //used for any route that doesn't match an entry in the routes file
    @Override
    public Promise<SimpleResult> onHandlerNotFound(RequestHeader request) {
        return Promise.<SimpleResult>pure(internalServerError(
                femr.ui.views.html.errors.global.render()
        ));
    } */

    private static Injector createInjector() {
        return Guice.createInjector(new BusinessLayerModule(), new DataLayerModule(), new UtilitiesModule());
    }
}