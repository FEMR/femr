package functional;

import com.google.inject.Inject;

//import jdk.nashorn.internal.ir.annotations.Immutable;
import forhumanconvenience.ForHumanConvenience;
import io.ebean.Ebean;
import io.ebean.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.junit.FixMethodOrder;
import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import play.Application;

import java.util.Map;
import java.util.HashMap;
import java.util.function.Function;
import java.util.stream.IntStream;

import play.Mode;
import play.db.Database;
import play.inject.guice.GuiceApplicationBuilder;
import play.test.*;
//import play.db.*;
import static play.test.Helpers.*;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import org.fluentlenium.core.domain.FluentWebElement;
import static org.fluentlenium.core.filter.FilterConstructor.*;

/**
 * This is a set of functional tests for Admin panel functionality.
 * This does not check all possible cases, or for invalid inputs,
 * but rather is meant to test that what given proper usage, what is supposed
 * to work actually works.
 *
 * If the UI is modified, this probably needs to have some lines changed.
 * The reason being is that this uses Selenium-based testing, so
 * the way it actually progresses the tests is by clicking the buttons,
 * and filling form inputs, which are located by name, type, id, class, etc.
 *
 * If tests are failing, the only errors one is likely to get from this set
 * of tests are ones along the lines of "Selenium can't find element X".
 * They will almost always be related to UI elements, even if the problem is
 * actually on the backend
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class InventoryTest/* extends FluentTest*/{

        private static Boolean setupIsDone = false;
        private static Boolean sequentialTestHasFailed = false;
        private static Boolean lastTestIsDone = false;
        private WebDriver driver;
        private static final String TEST_ADMIN_USERNAME = "test_admin_user@example.org";
        //password requirements as of 2.4.0: >= 8 chars long, >= 1 special char, >= 1 number, >= 1 uppercase, >= 1 lowercase
        private static String TEST_ADMIN_PASSWORD = "Test_Admin_User_Password1";
        Application application;
        TestServer server;


        public InventoryTest() {

            // Sets the location of the chrome driver
            System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");

            // Build an application in Test mode
            // conf/application.test.conf is loaded by default (defined in build.sbt)
            this.application = new GuiceApplicationBuilder()
                    .in(Mode.TEST)
                    .build();

            //Helpers.start(this.application);
            server = testServer(19001, application);
        }

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
        private void failSequentialTest(Function someTest, Boolean isSequential){
            try{
                someTest.apply(new Object());
            } catch (Throwable t){
            }

        }
        private void failIndependentTest(){}

        private void testWrapper(){

        }



        @Before
        public void setup() {
            if(!setupIsDone){



                ForHumanConvenience.playBeforeAllTestStartSound();
//                Class.forName(jdbcDriver);
                try {
                    // Transaction txn = Ebean.beginTransaction();
                    // Connection conn = txn.getConnection();
                    // Statement s = conn.createStatement();
                    // s.executeUpdate("DROP DATABASE IF EXISTS femr_test");
                    // s.executeUpdate("CREATE DATABASE IF NOT EXISTS femr_test");
                    // Ebean.commitTransaction();
                    // Ebean.endTransaction();
                    // Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/?user=root&password=8Mary2BOO89&useSSL=false");
                    // Statement s = conn.createStatement();
                    // s.executeUpdate("DROP DATABASE IF EXISTS femr_test");
                    // s.executeUpdate("CREATE DATABASE IF NOT EXISTS femr_test");

                }
                catch(Exception e){
                    e.printStackTrace();
                    System.exit(1);
                }
                //Map<String, String> h2OptionMap = new HashMap<String, String>();
                //h2OptionMap.put("MODE", "MYSQL");
                //application = fakeApplication(Helpers.inMemoryDatabase("femr", h2OptionMap));

                // Helpers.start(application);
                setupIsDone = true;
            }
        }

        @Test
        public void a_createAdminUserAndSignInAsNewAdmin() {
            failIfOtherTestsFailed();
            //Use Chromedriver, because if a test ever fails, you'd see it happen if you were watching

            running(server, new ChromeDriver(), browser -> {
                //Sign in as default admin
                browser.goTo("/");
                browser.$("input[name='email']").fill().with("admin");
                browser.$("input[name='password']").fill().with("admin");
                browser.$("input[type='submit']").click();

                //Hit admin panel button at top of page
                browser.$("a", withText("Admin")).click();

                //Hit User button to get user menu
                browser.$("a", withText().contains("Users")).click();

                //Hit add user button
                browser.$("a", withText().contains("Add User")).click();

                //Fill and submit new user form. Give him all roles. Makes the assumption that there is only 1 form on page, so all text box inputs are for that form
                System.out.println("A1");
                browser.$("#email").fill().with(TEST_ADMIN_USERNAME);
                browser.$("#password").fill().with(TEST_ADMIN_PASSWORD);
                browser.$("#passwordVerify").fill().with(TEST_ADMIN_PASSWORD);
                browser.$("#firstName").fill().with("TestAdminFirstName");
                browser.$("#lastName").fill().with("TestAdminLastName");
                browser.$("#notes").fill().with("About User: Test Admin");

                browser.$("input[type='checkbox']").click(); //click all inputs. This also clicks all the checkboxes and gives the test user all roles.
                browser.$("#addUserSubmitBtn").click();
                System.out.println("A4");

                //Log out and then log it as newly created test admin user, check that it worked, then log out again. End test.
                browser.$("a[href*='logout']").click();
                browser.$("input[name='email']").fill().with(TEST_ADMIN_USERNAME);
                browser.$("input[name='password']").fill().with(TEST_ADMIN_PASSWORD);
                browser.$("input[type='submit']").click();

//                try{
//                    Thread.sleep(1000000000);
//                } catch(Exception e){
//
//                }

                browser.$("a", withText("Admin")).click(); //test for login by seeing if we can get admin panel. Not sure if click is needed, but you can't click something that isn't there.
                browser.$("a[href*='logout']").click();
            });
        }

        @Test
        public void b_createTripsAndAssignSelfToAllTrips(){

            running(server, new ChromeDriver(), browser -> {
                //Get login page
                browser.goTo("/");

                //Log back in as test administrator
                browser.$("input[name='email']").fill().with(TEST_ADMIN_USERNAME);
                browser.$("input[name='password']").fill().with(TEST_ADMIN_PASSWORD);
                browser.$("input[type='submit']").click();

                //Hit admin panel button at top of page, and go to inventory page, which should show a redirect page to manage trips
                browser.$("a", withText("Admin")).click();
                browser.$("a", withText().contains("Inventory")).click();

                //User is not added to any trips, and no trips exist yet, so hit the button on inventory page to create trips.
                browser.$("a", withText().contains("Manage Trip Users")).click();

                //Hit add city button and add 3 new cities
                browser.$("a", withText().contains("Manage Cities")).click();
                System.out.println("MANAGE CITY");

                for(int i = 0; i < 3; i++){
                    browser.$("input", withName("newCity")).fill().with("testCity" + (i+1));
                    System.out.println("MANAGE CITY NAME");
                    browser.$("select[name='newCityCountry']").$("option", withText("Afghanistan")).click();
                    browser.$("button[type='submit']").click();
                }

                //Hit Manage teams button and add 3 new teams
                browser.$("a", withText().contains("Manage Teams")).click();

                for(int i = 0; i < 3; i++){
                    browser.$("input", withName("newTeamName")).fill().with("testTeamName" + (i+1));
                    browser.$("input", withName("newTeamLocation")).fill().with("testTeamOrigin" + (i+1));
                    browser.$("input", withName("newTeamDescription")).fill().with("testTeamOrigin" + (i+1));
                    browser.$("button[type='submit']").click();
                }
                System.out.println("MADE ALL TEAMS");

                //Hit Manage trip button, add 3 new trips, each with the teams and cities given before
                browser.$("a", withText().contains("Manage Trips")).click();
                System.out.println("Manage trips clicked");
                for(int i = 0; i < 3; i++){
                    browser.$("select[name='newTripTeamName']").$("option", withText("testTeamName" + (i+1))).click();
                    System.out.println("new tripteamname");
                    browser.$("select[name='newTripCity']").$("option", withText("testCity" + (i+1))).click();
                    System.out.println("new tripCity");

                    System.out.println("city and name");

                    browser.$("input[value*='Afghanistan']");//Trip country updates automatically. Check to find element with value Ukraine. Throws expection if it can't be found, meaning that input didn't update.
                    System.out.println("countrycheck");

                    browser.executeScript("document.getElementsByName('newTripStartDate')[0].value='201" + i + "-04-01'");//Date inputs are finicky, so just use JS to set their input.
                    browser.executeScript("document.getElementsByName('newTripEndDate')[0].value='201" + i + "-05-01'");
                    System.out.println("dates inputted");
                    browser.$("form[action='/admin/trips']").submit();
                    System.out.println("form" + i + "submitted");
                }

                //Assign self to all trips
                for(int i = 0; i < 3; i++){
                    browser.$("form").get(i+1).click(); //The edit button form action will bind to numbers starting at 1. the first form adds trips. skip it.
                    browser.$("input[placeholder*='Add users here']").fill().with("TestAdminFirstName TestAdminLastName");
                    browser.$("li", withText("TestAdminFirstName TestAdminLastName")).click();//relies on the UI not having an li for the user who is not yet added.
                    browser.$("button[type='submit']", withText("Add")).click();

                    browser.$("td", withText("TestAdminFirstName"));//Check to see that it added the test admin user. Throws exception if it's not in the table.

                    browser.$("input[placeholder*='Remove users here']").fill().with("TestAdminFirstName TestAdminLastName");
                    browser.$("li", withText("TestAdminFirstName TestAdminLastName")).click();//relies on the UI not having an li for the user who is already added.
                    browser.$("button[type='submit']", withText("Remove")).click();

                    try{
                        Thread.sleep(1000);
                    } catch (Exception e){}
                    //Check to see that it removed the test user. There's only the header row if there are 0 users.
                    if(browser.$("#usersTripTable tr").size() != 1) {
                        //throw some exception
                        assertTrue(false);
                    }

                    browser.$("input[placeholder*='Add users here']").fill().with("TestAdminFirstName TestAdminLastName");
                    browser.$("li", withText("TestAdminFirstName TestAdminLastName")).click();//relies on the UI not having an li for the user who is not yet added.
                    browser.$("button[type='submit']", withText("Add")).click();

                    browser.$("td", withText("TestAdminFirstName"));//Check to see that it re-added the test admin user. Throws exception if it's not in the table.

                    browser.$("a", withText().contains("Trips")).click();//go back to manage trips page.
                    System.out.println("Did " + i);
                }
                browser.$("a[href*='logout']").click();
                System.out.println("DONE WITH ADD TRIPS");

            });


        }

        @Test
        public void c_populateAllThreeInventoriesWithExistingMedicationsThatHaveBrandNames(){
            running(server, new ChromeDriver(), browser ->{
                //Get login page
                browser.goTo("/");

                //Log back in as test administrator
                browser.$("input[name='email']").fill().with(TEST_ADMIN_USERNAME);
                browser.$("input[name='password']").fill().with(TEST_ADMIN_PASSWORD);
                browser.$("input[type='submit']").click();

                //Hit admin panel button at top of page
                browser.$("a", withText("Admin")).click();

                //Hit User button to get user menu
                browser.$("a", withText().contains("Inventory")).click();

                System.out.println("in inventory");

                //get select options for each of the three trips. We don't need loops for this - loops are so 1960.
                for(int i = 0; i < 3; i++){
                    browser.$("#selectTripInventory option").get(i).click();
                    browser.$("button", withText().contains("Select")).click();

                    System.out.println("TRIP");

                    browser.$("a", withText().contains("Existing Medication")).click();
                    System.out.println("EXISTING");

                    //Add medications
                    FluentWebElement existingMedicationInputTextbox = browser.$("input[placeholder*='Search medicine:']").get(0);
                    existingMedicationInputTextbox.fill().with("pepto bismol");//type something in to get the li's to pop up.
                    browser.$("li", withText().contains("Pepto Bismol 262 mg bismuth subsalicylate (tab chew)")).click();

                    existingMedicationInputTextbox.fill().with("advil");
                    browser.$("li", withText().contains("Advil 200 mg ibuprofen (tabs)")).click();

                    existingMedicationInputTextbox.fill().with("proventil");
                    browser.$("li", withText().contains("Proventil 90 mcg albuterol (MDI)")).click();

                    //submit the meds
                    browser.$("#submitMedicationButton").click();

                    System.out.println("TRIPMEDS ADDED");

                    //check that the meds were actually put there. just see if med is there, fluentium will throw exception if they're not there.
                    browser.$("td", withText().contains("Pepto Bismol 262 mg bismuth subsalicylate (tab chew)"));
                    browser.$("td", withText().contains("Advil 200 mg ibuprofen (tabs)"));
                    browser.$("td", withText().contains("Proventil 90 mcg albuterol (MDI)"));
                    browser.$("td", withText("1"));
                    browser.$("td", withText("2"));
                    browser.$("td", withText("3"));

                    System.out.println("MEDSTHERE");
                }

//                ForHumanConvenience.playTestFailSound();
//                                try{
//                    Thread.sleep(1000000000);
//                } catch(Exception e){
//
//                }
                browser.$("a[href*='logout']").click();

            });

        }

        @Test
        public void d_populateInventoryWithCustomMedications(){
            running(server, new ChromeDriver(), browser -> {
                //Get login page
                browser.goTo("/");

                //Log back in as test administrator
                browser.$("input[name='email']").fill().with(TEST_ADMIN_USERNAME);
                browser.$("input[name='password']").fill().with(TEST_ADMIN_PASSWORD);
                browser.$("input[type='submit']").click();

                //Hit admin panel button at top of page
                browser.$("a", withText("Admin")).click();

                //Hit User button to get user menu
                browser.$("a", withText().contains("Inventory")).click();
                for(int i = 0; i < 3; i++){
                    browser.$("#selectTripInventory option").get(i).click();
                    browser.$("button", withText().contains("Select")).click();

                    System.out.println("TRIP");

                    browser.$("a", withText().contains("Custom Medication")).click();
                    System.out.println("CUSTOM");

                    //only make one medication
                    browser.$("#medicationName").fill().with("testCustomMedName");
                    browser.$("#addNewIngredient").click();
                    browser.$("input[name*='medicationIngredient[]']").fill().with("testGeneric1", "testGeneric2");//this does it for two ingredients
                    browser.$("input[name*='medicationStrength[]']").fill().with("40", "60");
                    browser.$("select[name*='medicationUnit[]'] option", withText("mg")).click();
                    browser.$("input[name*='medicationQuantity']").fill().with("2");
                    browser.$("select[name*='medicationForm'] option", withText("caps")).click();

                    browser.$("#submitMedicationButton").click();

                    //check that the meds were actually put there. just see if med is there, fluentium will throw exception if they're not there.
                    browser.$("td", withText().contains("4"));
                    browser.$(".editCurrentQuantity", withText().contains("2")); //should be the only thing with quantity two


                    System.out.println("MEDSTHERE");
                }

                browser.$("a[href*='logout']").click();

            });

        }
//
        @Test
        public void e_RemoveReaddButtonOnAllInventoriesExistingMedications(){
            running(server, new ChromeDriver(), browser->{
                //Get login page
                browser.goTo("/");

                //Log back in as test administrator
                browser.$("input[name='email']").fill().with(TEST_ADMIN_USERNAME);
                browser.$("input[name='password']").fill().with(TEST_ADMIN_PASSWORD);
                browser.$("input[type='submit']").click();

                //Hit admin panel button at top of page
                browser.$("a", withText("Admin")).click();

                //Hit User button to get user menu
                browser.$("a", withText().contains("Inventory")).click();

                for(int i = 0; i < 3; i++) {
                    browser.$("#selectTripInventory option").get(i).click();
                    browser.$("button", withText().contains("Select")).click();

                    //remove all meds
                    browser.$("button", withText("Remove")).click();

                    //readd all meds
                    browser.$("button", withText("Undo")).click();

                    System.out.println("B4 REFRESH");
                    //refresh page
                    browser.goTo(browser.url());
                    System.out.println("REFRESHED");
                }



                //Check it's all still there, and that the medications are actually readded, not just duplicates
                browser.$("td", withText().contains("Pepto Bismol 262 mg bismuth subsalicylate (tab chew)"));
                browser.$("td", withText().contains("Advil 200 mg ibuprofen (tabs)"));
                browser.$("td", withText().contains("Proventil 90 mcg albuterol (MDI)"));
                browser.$("td", withText().contains("testCustomMedName 40 mg testGeneric1 / 60 mg testGeneric2 (B/S)"));
                browser.$("td", withText("1"));
                browser.$("td", withText("2"));
                browser.$("td", withText("3"));
                browser.$("td", withText("4"));

//                ForHumanConvenience.playAfterAllTestSuccessSound();
//                try{ Thread.sleep(1000000000); } catch(Exception e){}

                browser.$("a[href*='logout']").click();

            });

            lastTestIsDone=Boolean.valueOf(true);
        }
//
//        @Test
//        public void e_RemoveThenManuallyReaddAllExistingInventoriesMedications(){
//
//        }
//
//        @Test
//        public void f_RemoveThenManuallyReaddAllCustomInventoriesMedications(){
//
//        }


        @After
        public void teardown() {
            if(lastTestIsDone || sequentialTestHasFailed) {
                ForHumanConvenience.playAfterAllTestSuccessSound();
                System.out.println("In Teardown");
                try {
                    ForHumanConvenience.playBeforeAllTestStartSound();
                   // Transaction txn = Ebean.beginTransaction();
                   // Connection conn = txn.getConnection();
                   // Statement s = conn.createStatement();
                   // s.executeUpdate("DROP DATABASE IF EXISTS femr_test");
                   // Ebean.commitTransaction();
                   // Ebean.endTransaction();
                  //  Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/femr_test?user=root&password=8Mary2BOO89&useSSL=false");
                  //  Statement s = conn.createStatement();
                  //  s.executeUpdate("DROP DATABASE IF EXISTS femr_test");
                    //tear it all down and hope it doesn't take the test with it
                    Helpers.stop(application); //This is throwing nullptr for some reason, but it's at least caught. When process dies, so will this.

                } catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
}
