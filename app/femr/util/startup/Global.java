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
    public Promise<SimpleResult> onBadRequest(RequestHeader request, String error) {
        return Promise.<SimpleResult>pure(internalServerError(
                femr.ui.views.html.errors.global.render()
        ));
    }

    //used for any route that doesn't match an entry in the routes file
    @Override
    public Promise<SimpleResult> onHandlerNotFound(RequestHeader request) {
        return Promise.<SimpleResult>pure(internalServerError(
                femr.ui.views.html.errors.global.render()
        ));
    }

    private static Injector createInjector() {
        return Guice.createInjector(new BusinessLayerModule(), new DataLayerModule(), new UtilitiesModule());
    }
}