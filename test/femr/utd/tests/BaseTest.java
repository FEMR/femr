package femr.utd.tests;

import femr.common.dtos.ServiceResponse;
import femr.util.dependencyinjection.modules.BusinessLayerModule;
import femr.util.dependencyinjection.modules.DataLayerModule;
import femr.util.dependencyinjection.modules.MapperModule;
import femr.util.dependencyinjection.modules.UtilitiesModule;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import play.test.FakeApplication;
import play.test.Helpers;
import util.runners.GuiceJUnitRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.fail;

/**
 * Created by ojcch on 3/14/2016.
 */
@RunWith(GuiceJUnitRunner.class)
@GuiceJUnitRunner.GuiceModules({BusinessLayerModule.class, DataLayerModule.class, MapperModule.class, UtilitiesModule.class})
public class BaseTest {

    public static FakeApplication app;

    @BeforeClass
    public static void startApp() {

        Map<String, Object> conf = conf = new HashMap<>();
        conf.put("play.evolutions.enabled", "false");
        conf.put(  "db.default.url","jdbc:mysql://localhost:3306/femr_testdb");
        conf.put(  "db.default.username","root");
        conf.put(  "db.default.password","T201328t.");
        app = Helpers.fakeApplication(conf);
        Helpers.start(app);
    }

    @AfterClass
    public static void stopApp() {
        Helpers.stop(app);
    }

    public static void checkForErrors(ServiceResponse response){
        Map<String, String> errs = response.getErrors();

        if (errs.size() != 0){
            fail(errs.toString());
        }

    }
}
