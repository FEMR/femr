package integration;
import static org.junit.Assert.*;
import static play.test.Helpers.*;

import com.google.common.collect.ImmutableMap;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Module;
import com.mysql.jdbc.Driver;
import io.ebean.EbeanServer;
import org.junit.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import play.ApplicationLoader;
import play.Mode;
import play.api.Application;
import play.db.Database;
import play.db.Databases;
import play.api.Environment;
import play.api.db.evolutions.EnvironmentEvolutionsReader;
import play.db.evolutions.*;
import play.inject.guice.GuiceApplicationBuilder;
import play.inject.guice.GuiceApplicationLoader;
import play.test.Helpers;
import play.test.WithBrowser;

import javax.swing.plaf.nimbus.State;
import java.io.File;
import java.sql.SQLException;
import java.sql.Connection;


public class TestIntegrations extends WithBrowser {

    @Inject
    Application application;

    @Before
    public void setup() {
        GuiceApplicationBuilder builder =
                new GuiceApplicationLoader()
                        .builder(new ApplicationLoader.Context(Environment.simple(new File("."), Mode.DEV.asScala()).asJava()))
                        .configure("ebean.default", "femr.data.models.*");
        Guice.createInjector(builder.applicationModule()).injectMembers(this);

        application = builder.build().asScala();
        Helpers.start(application.asJava());
    }

    @After
    public void teardown() {
        Helpers.stop(application.asJava());
    }
//    @Before
//    public void createDatabase() {
////        database = Databases.createFrom("com.mysql.jdbc.Driver", "jdbc:mysql://127.0.0.1:3306/femr_db?characterEncoding=UTF-8&useSSL=false&allowPublicKeyRetrieval=true",
////                ImmutableMap.of("username", "femr", "password", "password"));
////
////
////        wipeEntireDatabase(database);
////
////        // Relative path (based on your project's root folder)
////        Evolutions.applyEvolutions(
////                database,
////                new EnvironmentEvolutionsReader(Environment.simple(new File("."), Mode.DEV.asScala())));
//
//
//    }
//
//    @After
//    public void shutdownDatabase() {
//        // Some migrations are wrong, so we wipe entire database instead
////        wipeEntireDatabase(database);
////        database.shutdown();
//    }

    @Test
    public void browserTest()  {
        File pathBinary = new File("/usr/lib/firefox/firefox");
        FirefoxBinary firefoxBinary = new FirefoxBinary(pathBinary);

        System.setProperty("webdriver.gecko.driver", "/geckodriver_linux");
        // System.setProperty("webdriver.chrome.driver", "/home/benij/Software/chromedriver_linux64/chromedriver");

        // Removing unnecessary JavaScript errors/warnings.
        System.setProperty(FirefoxDriver.SystemProperty.DRIVER_USE_MARIONETTE, "true");
        System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, "/dev/null");

        // FirefoxProfile profile = new FirefoxProfile("");
        FirefoxProfile profile = new FirefoxProfile();
        profile.setPreference("dom.webdriver.enabled", false);
        profile.setPreference("useAutomationExtension", false);
        profile.setPreference("permissions.default.image", 2);
        profile.setPreference("dom.ipc.plugins.enabled.libflashplayer.so", false);
        profile.setPreference("webgl.disabled", true);
        FirefoxOptions options = new FirefoxOptions();
        options.setProfile(profile);
        options.setBinary(firefoxBinary);
        // options.addArguments("--headless");

        // WebDriver webDriver = new ChromeDriver();
        WebDriver webDriver = new FirefoxDriver(options);


        running(
                testServer(application.asJava()),
                webDriver,
                browser -> {
                    browser.goTo("/");
                });
    }

}
