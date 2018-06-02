package functional;

import com.google.inject.Inject;

//import jdk.nashorn.internal.ir.annotations.Immutable;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runners.MethodSorters;
import org.junit.FixMethodOrder;
import static org.junit.Assert.*;

import play.Application;
import play.Environment;
import play.inject.guice.GuiceApplicationBuilder;

import java.util.Map;
import java.util.HashMap;

import play.db.evolutions.*;

import play.test.*;
import play.Mode;
import play.Configuration;
//import play.db.*;
import play.db.Database;
import play.api.db.Databases;
import play.api.*;
import play.db.evolutions.Evolutions;
import play.test.WithBrowser;
import static play.test.Helpers.*;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import com.google.inject.Module;
import com.google.inject.AbstractModule;

import com.google.common.collect.ImmutableMap;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import org.fluentlenium.adapter.junit.FluentTest;
import org.fluentlenium.core.annotation.Page;
import org.fluentlenium.core.hook.wait.Wait;
import org.fluentlenium.core.annotation.PageUrl;
import org.fluentlenium.core.domain.FluentWebElement;



//@PageUrl("http://localhost:9000/")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class InventoryTest/* extends FluentTest*/{
        @Inject Application application;
//        private static String femrURLS String = "http://127.0.0.1:9000/";
//        private WebDriver driver;
        private static Boolean setupIsDone = false;
        private static Boolean sequentialTestHasFailed = false;
        private static Boolean lastTestIsDone = false;
        private void failIfOtherTestsFailed() {
            //Get calling function - this should be the test
            StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
            StackTraceElement e = stacktrace[2];//maybe this number needs to be corrected
            String callingClassAndTest = e.getClassName() + "." +e.getMethodName();
            //Cause a test failure, and print to console what happened.
            if(sequentialTestHasFailed){
                System.out.println("[\033[41m  \033[0m] Some previous test that " + callingClassAndTest + " relies on failed, so it did not run.");
                assertTrue(false);
            }
        }

        @Before
        public void setup() {
            if(!setupIsDone){
                Map<String, String> h2OptionMap = new HashMap<String, String>();
                h2OptionMap.put("MODE", "MYSQL");
                application = fakeApplication(Helpers.inMemoryDatabase("femr", h2OptionMap));
                Helpers.start(application);
                setupIsDone = true;
            }
        }

        @Test
        public void a_createAdminUserAndSignInAsNewAdmin() {
            failIfOtherTestsFailed();
            running(testServer(), HTMLUNIT, browser -> {
                browser.goTo("/");
//                assertEquals("Welcome to Play!", browser.$("#title").text());
//                browser.$("a").click();
//                assertEquals("login", browser.url());
            });
//            goTo(femrURLString);
//            try {
//                Thread.sleep(2000);
//            } catch(Exception e){}
//
//            System.out.println("[\033[42m  \033[0m] Yay, Setup ran! ");
        }

        @Test
        public void createTripsAndAssignSelfToAllTrips(){

        }

        @Test
        public void b_populateInventoriesWithExistingMedications(){

        }

        @Test
        public void c_populateInventoriesWithCustomMedications(){

        }

        @Test
        public void d_RemoveReaddAllInventoriesMedications(){

        }

        @Test
        public void e_RemoveThenManuallyReaddAllExistingInventoriesMedications(){

        }

        @Test
        public void f_RemoveThenManuallyReaddAllCustomInventoriesMedications(){

        }


        @After
        public void teardown() {
            if(lastTestIsDone) {
                Helpers.stop(application);
            }
        }
}
