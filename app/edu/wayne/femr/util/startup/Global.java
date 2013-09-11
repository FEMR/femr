package edu.wayne.femr.util.startup;

import com.google.inject.Guice;
import com.google.inject.Injector;
import edu.wayne.femr.util.dependencyinjection.BusinessLayerModule;
import edu.wayne.femr.util.dependencyinjection.DataLayerModule;
import play.Application;
import play.GlobalSettings;

public class Global extends GlobalSettings {

    private static final Injector INJECTOR = createInjector();

    @Override
    public void onStart(Application app) {
//        new DatabaseSeeder().seed();
    }

    @Override
    public <A> A getControllerInstance(Class<A> controllerClass) throws Exception {
        return INJECTOR.getInstance(controllerClass);
    }

    private static Injector createInjector() {
        return Guice.createInjector(new BusinessLayerModule(), new DataLayerModule());
    }
}