package functional;

import forhumanconvenience.ForHumanConvenience;
import org.junit.*;
import org.junit.runners.MethodSorters;
import static org.junit.Assert.*;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import org.fluentlenium.core.domain.FluentWebElement;
import static org.fluentlenium.core.filter.FilterConstructor.*;

import play.Application;
import play.Mode;
import play.api.Configuration;
import play.inject.guice.GuiceApplicationBuilder;
import play.test.*;
import static play.test.Helpers.*;

import javax.inject.*;

/**
 * What is this?
 * This is a set of functional tests for Admin panel functionality.
 * This does not check all possible cases, or for invalid inputs,
 * but rather is meant to test that what given proper usage, what is supposed
 * to work actually works.
 *
 * What you should be aware of if you are developing tests:
 * If the UI is modified, this probably needs to have some lines changed.
 * The reason being is that this uses Selenium-based testing, so
 * the way it actually progresses the tests is by clicking the buttons,
 * and filling form inputs, which are located by name, type, id, class, etc.
 *
 * Note, fluentium is built on top of selenium. You can take a FluentWebElement
 * and convert it down to a selenium WebElement, where there's a little less abstraction.
 * This is needed for inputs of type date.
 *
 * If tests are failing, the only errors one is likely to get from this set
 * of tests are ones along the lines of "Selenium can't find element X".
 * They will almost always be related to UI elements, even if the problem is
 * actually on the backend.
 *
 * If you are adding tests, I recommend using ChromeDriver or other webdriver with a GUI
 * so that you can see your tests in action. The following line is also helpful to stop
 * execution so you can look at the browser and page elements.
 * try{Thread.sleep(10000000);}catch(Exception e){}
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class InventoryTest {

    /**
     * Test User Related Fields
     */
    @Inject
    private static Configuration config;
//    private static final String TEST_ADMIN_USERNAME = "test_admin_user@example.org";
//    private static String TEST_ADMIN_PASSWORD = "Test_Admin_User_Password1";
//    private static final String[] TEST_CITIES = {
//            "TestCity1",
//            "TestCity2",
//            "TestCity3"
//    };

    /**
     * Test-Boilerplate related fields
     */
    private static Application application;
    private static Boolean noSequentialTestHasFailed;



        @BeforeClass
        public static void ultimateSetup(){
            application = new GuiceApplicationBuilder()
                    .in(Mode.TEST)
                    .build();
            noSequentialTestHasFailed = Boolean.TRUE;
        }

        @AfterClass
        public static void ultimateTeardown(){
            Helpers.stop(application);
        }

        @Before
        public void singleTestSetup(){}

        @After
        public void singleTestTeardown(){}

        private void sequentialTestWrapper(java.util.function.Consumer<TestBrowser> singleTestBlock){
            try{
                if(noSequentialTestHasFailed){
                    running(testServer(), new HtmlUnitDriver(true), singleTestBlock);
                } else {
                    throw new Exception("Previous Sequential Test has failed. This test cannot run.");
                }
            } catch(Exception e){
                e.printStackTrace();
                noSequentialTestHasFailed = Boolean.FALSE;
            }
        }

        private static void __private__createAdminUserAndSignInAsNewAdmin(TestBrowser browser){
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
            browser.$("#email").fill().with(TEST_ADMIN_USERNAME);
            browser.$("#password").fill().with(TEST_ADMIN_PASSWORD);
            browser.$("#passwordVerify").fill().with(TEST_ADMIN_PASSWORD);
            browser.$("#firstName").fill().with("TestAdminFirstName");
            browser.$("#lastName").fill().with("TestAdminLastName");
            browser.$("#notes").fill().with("About User: Test Admin");

            browser.$("input[type='checkbox']").click(); //click all inputs. This also clicks all the checkboxes and gives the test user all roles.
            browser.$("#addUserSubmitBtn").click();

            //Log out and then log it as newly created test admin user, check that it worked, then log out again. End test.
            browser.$("a[href*='logout']").click();
            browser.$("input[name='email']").fill().with(TEST_ADMIN_USERNAME);
            browser.$("input[name='password']").fill().with(TEST_ADMIN_PASSWORD);
            browser.$("input[type='submit']").click();

            browser.$("a", withText("Admin")).click(); //test for login by seeing if we can get admin panel. Not sure if click is needed, but you can't click something that isn't there.
            browser.$("a[href*='logout']").click();
        }

        private static void __private__createTripsAndAssignSelfToAllTrips(TestBrowser browser){
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

            for(int i = 0; i < 3; i++){
                browser.$("input", withName("newCity")).fill().with("testCity" + (i+1));
                browser.$("select[name='newCityCountry']").$("option", withText("Afghanistan")).click();
                browser.$("button[type='submit']").click();
            }

            //Hit Manage teams button and add 3 new teams
            browser.$("a", withText().contains("Manage Teams")).click();

            for(int i = 0; i < 3; i++){
                browser.$("input[name='newTeamName']").fill().with("testTeamName" + (i+1));
                browser.$("input[name='newTeamLocation']").fill().with("testTeamOrigin" + (i+1));
                browser.$("input[name='newTeamDescription']").fill().with("testTeamDescription" + (i+1));
                browser.$("button[type='submit']").click();
            }

            //Hit Manage trip button, add 3 new trips, each with the teams and cities given before
            browser.$("a", withText().contains("Manage Trips")).click();
            for(int i = 0; i < 3; i++) {

//                    browser.$("select[name='newTripTeamName']")/*.fillSelect().withText("testTeamName" + (i+1));//*/.$("option", withText("testTeamName" + (i+1))).click();
                browser.$("select[name='newTripTeamName']").fillSelect().withText("testTeamName" + (i + 1));
                browser.$("select[name='newTripCity']").fillSelect().withText("testCity" + (i + 1));

                browser.$("input[value*='Afghanistan']");//Trip country updates automatically. Check to find element with value Ukraine. Throws expection if it can't be found, meaning that input didn't update.

                //Date inputs are finicky, and only take input as selenium Webelements as opposed to being FuentWebElements.
                //Only alternative is to use JS, but you can't do that if you want to use HTMLUNIT driver
                browser.executeScript("document.getElementsByName('newTripStartDate')[0].value='201" + i + "-04-01'");
                browser.executeScript("document.getElementsByName('newTripEndDate')[0].value='201" + i + "-05-01'");

                browser.$("button", withText().contains("Submit")).click();

            }

            //Assign self to all trips
            for(int i = 0; i < 3; i++){
                browser.$("button", withText().contains("Edit")).get(i).click(); //The edit button form action will bind to numbers starting at 1. the first form adds trips. skip it.

                browser.$("input[placeholder*='Add users here']").fill().with("TestAdminFirstName TestAdminLastName");
                browser.$("option", withText("TestAdminFirstName TestAdminLastName")).click();//relies on the UI not having an li for the user who is not yet added.

                browser.$("button[type='submit']", withText("Add")).click();

                browser.$("td", withText("TestAdminFirstName"));//Check to see that it added the test admin user. Throws exception if it's not in the table.

                browser.$("input[placeholder*='Remove users here']").fill().with("TestAdminFirstName TestAdminLastName");
                browser.$("option", withText("TestAdminFirstName TestAdminLastName")).click();//relies on the UI not having an li for the user who is not yet added.

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
                browser.$("option", withText("TestAdminFirstName TestAdminLastName")).click();//relies on the UI not having an li for the user who is not yet added.
                browser.$("button[type='submit']", withText("Add")).click();
                browser.$("td", withText("TestAdminFirstName"));//Check to see that it re-added the test admin user. Throws exception if it's not in the table.

                browser.$("a", withText().contains("Trips")).click();//go back to manage trips page.
            }
            browser.$("a[href*='logout']").click();
        }

        private static void __private__populateAllThreeInventoriesWithExistingMedicationsThatHaveBrandNames(TestBrowser browser){
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

            //get select options for each of the three trips. We don't need loops for this - loops are so 1960.
            for(int i = 0; i < 3; i++){
                browser.$("#selectTripInventory option").get(i).click();
                browser.$("button", withText().contains("Select")).click();

                browser.$("a", withText().contains("Existing Medication")).click();

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

                //check that the meds were actually put there. just see if med is there, fluentium will throw exception if they're not there.
                browser.$("td", withText().contains("Pepto Bismol 262 mg bismuth subsalicylate (tab chew)"));
                browser.$("td", withText().contains("Advil 200 mg ibuprofen (tabs)"));
                browser.$("td", withText().contains("Proventil 90 mcg albuterol (MDI)"));
                browser.$("td", withText("1"));
                browser.$("td", withText("2"));
                browser.$("td", withText("3"));

            }

            browser.$("a[href*='logout']").click();

        }

        private static void __private__populateInventoryWithCustomMedications(TestBrowser browser){
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

                browser.$("a", withText().contains("Custom Medication")).click();

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

            }

            browser.$("a[href*='logout']").click();
        }

        private static void __private__RemoveReaddButtonOnAllInventoriesExistingMedications(TestBrowser browser){
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

                //refresh page
                browser.goTo(browser.url());
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
        }

        @Test
        public void a_createAdminUserAndSignInAsNewAdmin() {
            sequentialTestWrapper(InventoryTest::__private__createAdminUserAndSignInAsNewAdmin);
        }

        @Test
        public void b_createTripsAndAssignSelfToAllTrips(){
            sequentialTestWrapper(InventoryTest::__private__createTripsAndAssignSelfToAllTrips);
        }

        @Test
        public void c_populateAllThreeInventoriesWithExistingMedicationsThatHaveBrandNames(){
            sequentialTestWrapper(InventoryTest::__private__populateAllThreeInventoriesWithExistingMedicationsThatHaveBrandNames);
        }

        @Test
        public void d_populateInventoryWithCustomMedications(){
            sequentialTestWrapper(InventoryTest::__private__populateInventoryWithCustomMedications);
        }

        @Test
        public void e_RemoveReaddButtonOnAllInventoriesExistingMedications(){
            sequentialTestWrapper(InventoryTest::__private__RemoveReaddButtonOnAllInventoriesExistingMedications);
        }

//        @Test
//        public void e_RemoveThenManuallyReaddAllExistingInventoriesMedications(){
//
//        }
//
//        @Test
//        public void f_RemoveThenManuallyReaddAllCustomInventoriesMedications(){
//
//        }

}
