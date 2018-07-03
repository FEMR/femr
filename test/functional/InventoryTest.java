package functional;

import com.typesafe.config.ConfigFactory;
import forhumanconvenience.ForHumanConvenience;
import org.junit.*;
import org.junit.runners.MethodSorters;

import static org.fluentlenium.core.filter.MatcherConstructor.regex;
import static org.junit.Assert.*;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import org.fluentlenium.core.domain.*;
import static org.fluentlenium.core.filter.FilterConstructor.*;

import play.Application;
import play.Mode;
import play.api.Configuration;
import play.inject.guice.GuiceApplicationBuilder;
import play.test.*;
//import play.Logger;
import static play.test.Helpers.*;

import javax.inject.*;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

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
 * This is needed for inputs of type date, but using JS to set those is recommended.
 *
 * If tests are failing, the only errors one is likely to get from this set
 * of tests are ones along the lines of "Selenium can't find element X".
 * They will almost always be related to UI elements, even if the problem is
 * actually on the backend.
 *
 * If you are adding tests, I recommend using ChromeDriver or other webdriver with a GUI
 * so that you can see your tests in action. This also means that you have to have Chrome
 * installed in the default place on your OS. If you are using Chromedriver, then be
 * careful not to scroll/click in the browser during tests. Clicks are done by selenium in
 * a "point on screen (ex 300,200) where element is" not "send-signal to element" type of way.
 *
 * The following line is also helpful to stop
 * execution so you can look at the browser and page elements.
 * try{Thread.sleep(10000000);}catch(Exception e){}
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class InventoryTest {

    /**
     * Test User Related Fields.
     */
    private static final String DEFAULT_ADMIN_USERNAME = ConfigFactory.load().getString("default.admin.username");
    private static final String DEFAULT_ADMIN_PASSWORD = ConfigFactory.load().getString("default.admin.password");
    private static final String TEST_ADMIN_USERNAME = ConfigFactory.load().getString("test.functional.inventorytest.adminUsername");
    private static final String TEST_ADMIN_INITIAL_PASSWORD = ConfigFactory.load().getString("test.functional.inventorytest.initialAdminPassword");
    private static final String TEST_ADMIN_FIRST_NAME = ConfigFactory.load().getString("test.functional.inventorytest.adminFirstName");
    private static final String TEST_ADMIN_LAST_NAME = ConfigFactory.load().getString("test.functional.inventorytest.adminLastName");
    private static final String TEST_ADMIN_DESCRIPTION = ConfigFactory.load().getString("test.functional.inventorytest.adminDescription");
    private static final List<String> TEST_CITIES = ConfigFactory.load().getStringList("test.functional.inventorytest.testCities");
    private static final List<String> TEST_COUNTRIES = ConfigFactory.load().getStringList("test.functional.inventorytest.testCountries");
    private static final List<String> TEST_TEAM_NAMES = ConfigFactory.load().getStringList("test.functional.inventorytest.testTeamNames");
    private static final List<String> TEST_TEAM_LOCATIONS = ConfigFactory.load().getStringList("test.functional.inventorytest.testTeamLocations");
    private static final List<String> TEST_TEAM_DESCRIPTIONS = ConfigFactory.load().getStringList("test.functional.inventorytest.testTeamDescriptions");
    private static final List<String> TEST_TRIP_START_DATES = ConfigFactory.load().getStringList("test.functional.inventorytest.testTripStartDates");
    private static final List<String> TEST_TRIP_END_DATES = ConfigFactory.load().getStringList("test.functional.inventorytest.testTripEndDates");
    private static final List<String> TEST_EXISTING_MEDICATIONS = ConfigFactory.load().getStringList("test.functional.inventorytest.existingMedications");
    private static final String TEST_CUSTOM_MEDICATION_BRAND_NAME = ConfigFactory.load().getString("test.functional.inventorytest.customMedicationBrandName");
    private static final List<String> TEST_CUSTOM_MEDICATION_UNITS = ConfigFactory.load().getStringList("test.functional.inventorytest.customMedicationUnits");
    private static final String TEST_CUSTOM_MEDICATION_FORM = ConfigFactory.load().getString("test.functional.inventorytest.customMedicationForm");
    private static final String TEST_CUSTOM_MEDICATION_INITIAL_QUANTITY = ConfigFactory.load().getString("test.functional.inventorytest.customMedicationInitialQuantity");
    private static final List<String> TEST_CUSTOM_MEDICATION_INGREDIENTS = ConfigFactory.load().getStringList("test.functional.inventorytest.customMedicationIngredients");
    private static final List<String> TEST_CUSTOM_MEDICATION_INGREDIENTS_STRENGTHS = ConfigFactory.load().getStringList("test.functional.inventorytest.customMedicationIngredientsStrengths");

    /**
     * Test-Boilerplate related fields
     */
    private static Application application;
    private static Boolean noSequentialTestHasFailed;

    private static final Boolean enableAudioNotifications = ConfigFactory.load().getBoolean("test.functional.inventorytest.enableAudioNotifications");
    private static final Boolean enableVisualNotifications = ConfigFactory.load().getBoolean("test.functional.inventorytest.enableVisualNotifications");


    /**
     * JUNIT Bolerplate Methods
     */
    @BeforeClass
    public static void ultimateSetup(){
        if(enableAudioNotifications) ForHumanConvenience.playBeforeAllTestStartSound();
        if(enableVisualNotifications) ForHumanConvenience.initJframe("InventoryTest Status");
        application = new GuiceApplicationBuilder()
                .in(Mode.TEST)
                .build();
        noSequentialTestHasFailed = Boolean.TRUE;
    }

    @AfterClass
    public static void ultimateTeardown(){
        if(enableVisualNotifications) ForHumanConvenience.showTestFinished();

        if(enableAudioNotifications && noSequentialTestHasFailed) ForHumanConvenience.playAfterAllTestSuccessSound();
        else if(enableAudioNotifications && !noSequentialTestHasFailed) ForHumanConvenience.playAfterAllTestFailSound();

        Helpers.stop(application);

        if(noSequentialTestHasFailed){
            System.out.println("");
            System.out.print("\033[42m\033[1m| (b^_^)b | Inventory Looks Good.\n\033[0m");
        }
    }

    @Before
    public void singleTestSetup(){/*Do Nothing*/}

    @After
    public void singleTestTeardown(){
        if(enableAudioNotifications) ForHumanConvenience.playSingleTestEndSound();
    }

    private void sequentialTestWrapper(java.util.function.Consumer<TestBrowser> singleTestBlock) throws Exception{

        try{
            if(noSequentialTestHasFailed){
                running(testServer(), new HtmlUnitDriver(true), singleTestBlock);
            } else {
                String callingTest = Thread.currentThread().getStackTrace()[2].getMethodName();
                //Set bold text then reset formatting
                System.out.println("\033[1m" +
                        "[Test Failed] Previous Sequential Test has failed. " +
                        "Test \'" + callingTest + "\' cannot run.\033[0m");
            }
        } catch(Exception e){

            //red backbround, bold, white text. This is to make this somewhat easier to find on the terminal.
            String callingTest = Thread.currentThread().getStackTrace()[2].getMethodName();
            int lineOfExceptionOrigin = Thread.currentThread().getStackTrace()[2].getLineNumber();
            String classOfExceptionOrigin = Thread.currentThread().getStackTrace()[2].getClassName();
            System.out.println("\033[41m\033[1m\033[37m");
            System.out.println("[Test Failed] " + callingTest);
            System.out.println("[Exception Message]\n" + e.getMessage());
            System.out.println("[Stacktrace]");
            Arrays.asList(e.getStackTrace()).stream().forEach(x -> System.out.println(x.toString()));
            System.out.println("\n\033[0m\n");
            noSequentialTestHasFailed = Boolean.FALSE;

            if(enableVisualNotifications) ForHumanConvenience.showFailVisualAid();

            //throw, but do not catch exception so that JUNIT sees that the test actually failed.
            throw new Exception("Failed Test: " + callingTest);
        }
    }

    private static java.util.function.Consumer<TestBrowser> wrapLoginAndLogout(
                                                                               java.util.function.Consumer<TestBrowser> singleTestBlock,
                                                                               String username,
                                                                               String password){

        return (java.util.function.Consumer<TestBrowser>) (TestBrowser browser) -> {
                //Log back in as test administrator
                browser.goTo("/");
                browser.$("input[name='email']").fill().with(username);
                browser.$("input[name='password']").fill().with(password);
                browser.$("input[type='submit']").click();

                singleTestBlock.accept(browser); //run singleTestBlock

                browser.$("a[href*='logout']").click();
        };

    }

    private static void __private__createAdminUserAndSignInAsNewAdmin(TestBrowser browser){

        //Hit admin panel button at top of page
        browser.$("a", withText("Admin")).click();

        //Hit User button to get user menu
        browser.$("a", withText().contains("Users")).click();

        //Hit add user button
        browser.$("a", withText().contains("Add User")).click();

        //Fill and submit new user form. Give him all roles. Makes the assumption that there is only 1 form on page, so all text box inputs are for that form
        browser.$("#email").fill().with(TEST_ADMIN_USERNAME);
        browser.$("#password").fill().with(TEST_ADMIN_INITIAL_PASSWORD);
        browser.$("#passwordVerify").fill().with(TEST_ADMIN_INITIAL_PASSWORD);
        browser.$("#firstName").fill().with(TEST_ADMIN_FIRST_NAME);
        browser.$("#lastName").fill().with(TEST_ADMIN_LAST_NAME);
        browser.$("#notes").fill().with(TEST_ADMIN_DESCRIPTION);

        browser.$("input[type='checkbox']").click(); //click all inputs. This also clicks all the checkboxes and gives the test user all roles.
        browser.$("#addUserSubmitBtn").click();

        //Log out and then log it as newly created test admin user, check that it worked, then log out again. End test.
        browser.$("a[href*='logout']").click();
        browser.$("input[name='email']").fill().with(TEST_ADMIN_USERNAME);
        browser.$("input[name='password']").fill().with(TEST_ADMIN_INITIAL_PASSWORD);
        browser.$("input[type='submit']").click();

        browser.$("a", withText("Admin")).click(); //test for login by seeing if we can get admin panel. Not sure if click is needed, but you can't click something that isn't there.

    }

    private static void __private__createTripsAndAssignSelfToAllTrips(TestBrowser browser){

        //Hit admin panel button at top of page, and go to inventory page, which should show a redirect page to manage trips
        browser.$("a", withText("Admin")).click();
        browser.$("a", withText().contains("Inventory")).click();

        //User is not added to any trips, and no trips exist yet, so hit the button on inventory page to create trips.
        browser.$("a", withText().contains("Manage Trip Users")).click();

        //Hit add city button and add 3 new cities
        browser.$("a", withText().contains("Manage Cities")).click();

        for(int i = 0; i < 3; i++){
            browser.$("input", withName("newCity")).fill().with(TEST_CITIES.get(i));
            browser.$("select[name='newCityCountry']").$("option", withText(TEST_COUNTRIES.get(i))).click();
            browser.$("button[type='submit']").click();
        }

        //Hit Manage teams button and add 3 new teams
        browser.$("a", withText().contains("Manage Teams")).click();

        for(int i = 0; i < 3; i++){
            browser.$("input[name='newTeamName']").fill().with(TEST_TEAM_NAMES.get(i));
            browser.$("input[name='newTeamLocation']").fill().with(TEST_TEAM_LOCATIONS.get(i));
            browser.$("input[name='newTeamDescription']").fill().with(TEST_TEAM_DESCRIPTIONS.get(i));
            browser.$("button[type='submit']").click();
        }

        //Hit Manage trip button, add 3 new trips, each with the teams and cities given before
        browser.$("a", withText().contains("Manage Trips")).click();
        for(int i = 0; i < 3; i++) {

            browser.$("select[name='newTripTeamName']").fillSelect().withText(TEST_TEAM_NAMES.get(i));
            browser.$("select[name='newTripCity']").fillSelect().withText(TEST_CITIES.get(i));

            browser.$("input[value*='Afghanistan']");//Trip country updates automatically. Check to find element with value Ukraine. Throws expection if it can't be found, meaning that input didn't update.

            //Date inputs are finicky, and only take input as selenium Webelements as opposed to being FluentWebElements.
            //Cut to the chase and just set them with JS
            browser.executeScript("document.getElementsByName('newTripStartDate')[0].value='" + TEST_TRIP_START_DATES.get(i) +"'");
            browser.executeScript("document.getElementsByName('newTripEndDate')[0].value='" + TEST_TRIP_END_DATES.get(i) +"'");

            browser.$("button", withText().contains("Submit")).click();

        }

        //Assign self to all trips
        for(int i = 0; i < 3; i++){
            browser.$("button", withText().contains("Edit")).get(i).click(); //The edit button form action will bind to numbers starting at 1. the first form adds trips. skip it.

            browser.$("input[placeholder*='Add users here']").fill().with(TEST_ADMIN_FIRST_NAME + " " + TEST_ADMIN_LAST_NAME);
            browser.$("li", withText(TEST_ADMIN_FIRST_NAME + " " + TEST_ADMIN_LAST_NAME)).click();//relies on the UI not having an li for the user who is not yet added.

            browser.$("button[type='submit']", withText("Add")).click();

            browser.$("td", withText(TEST_ADMIN_FIRST_NAME));//Check to see that it added the test admin user. Throws exception if it's not in the table.

            browser.$("input[placeholder*='Remove users here']").fill().with(TEST_ADMIN_FIRST_NAME + " " + TEST_ADMIN_LAST_NAME);
            browser.$("li", withText(TEST_ADMIN_FIRST_NAME + " " + TEST_ADMIN_LAST_NAME)).click();//relies on the UI not having an li for the user who is not yet added.

            browser.$("button[type='submit']", withText("Remove")).click();
            try{
                Thread.sleep(1000);
            } catch (Exception e){}

            //Check to see that it removed the test user. There's only the header row if there are 0 users.
            if(browser.$("#usersTripTable tr").size() != 1) {
                //throw some exception
                assertTrue("User was not removed on trip " + (i+1) + ".",false);
            }

            browser.$("input[placeholder*='Add users here']").fill().with(TEST_ADMIN_FIRST_NAME + " " + TEST_ADMIN_LAST_NAME);
            browser.$("li", withText(TEST_ADMIN_FIRST_NAME + " " + TEST_ADMIN_LAST_NAME)).click();//relies on the UI not having an li for the user who is not yet added.
            browser.$("button[type='submit']", withText("Add")).click();
            browser.$("td", withText(TEST_ADMIN_FIRST_NAME));//Check to see that it re-added the test admin user. Throws exception if it's not in the table.

            browser.$("a", withText().contains("Trips")).click();//go back to manage trips page.
        }

    }

    private static void __private__populateAllThreeInventoriesWithExistingMedicationsThatHaveBrandNames(TestBrowser browser){

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

            existingMedicationInputTextbox.fill().with(TEST_EXISTING_MEDICATIONS.get(0).split(" ")[0]);//type first token of full medication name in to get the li's to pop up.
            browser.$("li", withText().contains(TEST_EXISTING_MEDICATIONS.get(0))).click();

            existingMedicationInputTextbox.fill().with(TEST_EXISTING_MEDICATIONS.get(1).split(" ")[0]);
            browser.$("li", withText().contains(TEST_EXISTING_MEDICATIONS.get(1))).click();

            existingMedicationInputTextbox.fill().with(TEST_EXISTING_MEDICATIONS.get(2).split(" ")[0]);
            browser.$("li", withText().contains(TEST_EXISTING_MEDICATIONS.get(2))).click();

            //submit the meds
            browser.$("#submitMedicationButton").click();

            //check that the meds were actually put there. just see if med is there, fluentium will throw exception if they're not there.
            browser.$("td", withText().contains(TEST_EXISTING_MEDICATIONS.get(0)));
            browser.$("td", withText().contains(TEST_EXISTING_MEDICATIONS.get(1)));
            browser.$("td", withText().contains(TEST_EXISTING_MEDICATIONS.get(2)));
            browser.$("td .sorting_1", withText("1"));
            browser.$("td .sorting_1", withText("2"));
            browser.$("td .sorting_1", withText("3"));
        }

    }

    private static void __private__populateInventoryWithCustomMedications(TestBrowser browser){

        //Hit admin panel button at top of page
        browser.$("a", withText("Admin")).click();

        //Hit User button to get user menu
        browser.$("a", withText().contains("Inventory")).click();
        for(int i = 0; i < 3; i++){
            browser.$("#selectTripInventory option").get(i).click();
            browser.$("button", withText().contains("Select")).click();
            browser.$("a", withText().contains("Custom Medication")).click();

            //only make one medication
            browser.$("#medicationName").fill().with(TEST_CUSTOM_MEDICATION_BRAND_NAME);
            browser.$("#addNewIngredient").click();
            browser.$("input[name*='medicationIngredient[]']").fill().with(
                    TEST_CUSTOM_MEDICATION_INGREDIENTS.get(0),
                    TEST_CUSTOM_MEDICATION_INGREDIENTS.get(1)
            );//this does it for two ingredients
            browser.$("input[name*='medicationStrength[]']").fill().with(
                    TEST_CUSTOM_MEDICATION_INGREDIENTS_STRENGTHS.get(0),
                    TEST_CUSTOM_MEDICATION_INGREDIENTS_STRENGTHS.get(1)
            );
            browser.$("select[name*='medicationUnit[]'] option", withText(TEST_CUSTOM_MEDICATION_UNITS.get(0))).get(0).click();
            browser.$("select[name*='medicationUnit[]'] option", withText(TEST_CUSTOM_MEDICATION_UNITS.get(1))).get(1).click();
            browser.$("input[name*='medicationQuantity']").fill().with(TEST_CUSTOM_MEDICATION_INITIAL_QUANTITY);
            browser.$("select[name*='medicationForm'] option", withText(TEST_CUSTOM_MEDICATION_FORM)).click();

            browser.$("#submitMedicationButton").click();

            //check that the meds were actually put there. just see if med is there, fluentium will throw exception if they're not there.
            browser.$("td .sorting_1", withText("4")); // medication id of custom med
            browser.$(".editCurrentQuantity", withText().contains("2")); //should be the only thing with quantity two

            assertFalse("Custom Med readded with new ID (seperate medication).",
                    browser.$(".sorting_1", withText().contains(regex("5"))).present()
            );
        }

    }

    private static void __private__RemoveReaddButtonOnAllInventoriesExistingMedications(TestBrowser browser){

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

            //Check it's all still there, and that the medications are actually readded, not just duplicates
            browser.$("td", withText().contains(TEST_EXISTING_MEDICATIONS.get(0)));
            browser.$("td", withText().contains(TEST_EXISTING_MEDICATIONS.get(1)));
            browser.$("td", withText().contains(TEST_EXISTING_MEDICATIONS.get(2)));
            browser.$("td", withText().contains(
                    String.join(" ",
                            new String[]{
                                    TEST_CUSTOM_MEDICATION_BRAND_NAME,
                                    TEST_CUSTOM_MEDICATION_INGREDIENTS_STRENGTHS.get(0),
                                    TEST_CUSTOM_MEDICATION_UNITS.get(0),
                                    TEST_CUSTOM_MEDICATION_INGREDIENTS.get(0),
                                    "/",
                                    TEST_CUSTOM_MEDICATION_INGREDIENTS_STRENGTHS.get(1),
                                    TEST_CUSTOM_MEDICATION_UNITS.get(1),
                                    TEST_CUSTOM_MEDICATION_INGREDIENTS.get(1),
                                    TEST_CUSTOM_MEDICATION_FORM
                    })
                    )
            );
            browser.$("td .sorting_1", withText("1"));
            browser.$("td .sorting_1", withText("2"));
            browser.$("td .sorting_1", withText("3"));
            browser.$("td .sorting_1", withText("4"));
        }

    }

    private static void __private__RemoveAllExistingMedicationsAllThreeInventories(TestBrowser browser){

        //Hit admin panel button at top of page
        browser.$("a", withText("Admin")).click();

        //Hit User button to get user menu
        browser.$("a", withText().contains("Inventory")).click();

        for(int i = 0; i < 3; i++) {
            browser.$("#selectTripInventory option").get(i).click();
            browser.$("button", withText().contains("Select")).click();

            System.out.println("B");

            List<FluentWebElement> existingMedicationRows = browser.$("#inventoryTable tr",
                    withPredicate( tr -> {
                        return tr.$(".sorting_1", withTextContent().contains(regex("1|2|3"))).present();
                    })
            );

            existingMedicationRows.stream().forEach(
                    row -> row.$("button", withText("Remove")).click()
            );

            //refresh page
            browser.goTo(browser.url());

            //Check that the medications actually were removed
            assertFalse("Existing med with medication id 1 on trip " + (i+1) + " should not be present.",
                    browser.$("td .sorting_1", withText("1")).present());
            assertFalse("Existing med with medication id 2 on trip " + (i+1) + " should not be present.",
                    browser.$("td .sorting_1", withText("2")).present());
            assertFalse("Existing med with medication id 3 on trip " + (i+1) + " should not be present.",
                    browser.$("td .sorting_1", withText("3")).present());
        }

    }

    private static void __private__ManuallyReaddExistingMedicationsAllThreeInventories(TestBrowser browser){
        __private__populateAllThreeInventoriesWithExistingMedicationsThatHaveBrandNames(browser);
    }

    private static void __private__RemoveCustomMedicationInventoriesAllThreeInventories(TestBrowser browser){

        //Hit admin panel button at top of page
        browser.$("a", withText("Admin")).click();

        //Hit User button to get user menu
        browser.$("a", withText().contains("Inventory")).click();

        for(int i = 0; i < 3; i++) {
            browser.$("#selectTripInventory option").get(i).click();
            browser.$("button", withText().contains("Select")).click();

            List<FluentWebElement> existingMedicationRows = browser.$("tr",
                    withPredicate(elem -> {
                        return elem.$(".sorting_1", withText().contains(regex("4"))).present();
                    })
            );
            existingMedicationRows.stream().forEach(
                    row -> row.$("button", withText("Remove")).click()
            );

            //refresh page
            browser.goTo(browser.url());

            //Check that the medications actually were removed
            assertFalse("Custom med with medication id 4 on trip " + (i+1) + " should not be present.",
                    browser.$(".sorting_1", withText().contains(regex("4"))).present());
        }

    }

    private static void __private__ManuallyReaddCustomMedicationInventoriesAllThreeInventories(TestBrowser browser){
        __private__populateInventoryWithCustomMedications(browser);
    }

    private static void __private__SetMedicationQuantitiesToFiveEachAllThreeInventories(TestBrowser browser){

        browser.$("a", withText("Admin")).click();

        //Hit User button to get user menu
        browser.$("a", withText().contains("Inventory")).click();

        for(int i = 0; i < 3; i++) {
            browser.$("#selectTripInventory option").get(i).click();
            browser.$("button", withText().contains("Select")).click();

            //click buttons to be able to edit, then edit initial medication quantity
            browser.$(".totalQuantity button").click();
            browser.$(".totalQuantity input[type='number']").fill().with("5");

            //click buttons to be able to edit, then edit current medication quantity
            browser.$(".currentQuantity button").click();
            browser.$(".currentQuantity input[type='number']").fill().with("5");

            //refresh page , then check to make sure that inventory quantities set actually hit the DB
            browser.goTo(browser.url());

            //loop four times to make sure all the meds we put in are still there
            List<FluentWebElement> initialQuantityTds = browser.$(".totalQuantity");
            List<FluentWebElement> currentQuantityTds = browser.$(".currentQuantity");
            for(int j = 0; j < 4; j++){
                initialQuantityTds.get(j).$("span", withText("4"));
                currentQuantityTds.get(j).$("span", withText("4"));
            }

        }

    }

    //Does Not Test that the settings actually activate, just that actually setting them does not break
    private static void __private__TurnOnAllAdminConfigOptions(TestBrowser browser){
        browser.$("a", withText("Admin")).click();

        //Hit User button to get config menu
        browser.$("a", withText().contains("Configure")).click();

        browser.$("input[type='checkbox']:not([checked='checked'])").click();

        browser.$("input[value='Save']").click();

        //hitting save redirects you off the page, so go back to config
        browser.$("a", withText().contains("Configure")).click();

        assertTrue("No admin panel config option check boxes should be checked, but >=1 of them are.",
                browser.$("input[type='checkbox']:not([checked='checked'])").size() == 0
        );

    }

    //Does Not Test that the settings actually deactivate, just that unsetting them does not break
    private static void __private__TurnOffAllAdminConfigOptions(TestBrowser browser){
        browser.$("a", withText("Admin")).click();

        //Hit User button to get user menu
        browser.$("a", withText().contains("Configure")).click();

        browser.$("input[type='checkbox'][checked='checked']").click();

        browser.$("input[value='Save']").click();

        //hitting save redirects you off the page, so go back to config
        browser.$("a", withText().contains("Configure")).click();

        assertTrue("All admin panel config option check boxes should all be checked, but they are not.",
                browser.$("input[type='checkbox'][checked='checked']").size() == 0
        );

    }

    private static void __private__CreateTestPatientThroughTriage(TestBrowser browser){}


    @Test
    public void a_createAdminUserAndSignInAsNewAdmin() throws Throwable {
        sequentialTestWrapper(
                wrapLoginAndLogout(InventoryTest::__private__createAdminUserAndSignInAsNewAdmin,
                        DEFAULT_ADMIN_USERNAME,
                        DEFAULT_ADMIN_PASSWORD
                )
        );
    }

    @Test
    public void b_createTripsAndAssignSelfToAllTrips() throws Throwable{
        sequentialTestWrapper(
                wrapLoginAndLogout(InventoryTest::__private__createTripsAndAssignSelfToAllTrips,
                        TEST_ADMIN_USERNAME,
                        TEST_ADMIN_INITIAL_PASSWORD
                )
        );
    }

    @Test
    public void c_populateAllThreeInventoriesWithExistingMedicationsThatHaveBrandNames() throws Throwable{
        sequentialTestWrapper(
                wrapLoginAndLogout(
                        InventoryTest::__private__populateAllThreeInventoriesWithExistingMedicationsThatHaveBrandNames,
                        TEST_ADMIN_USERNAME,
                        TEST_ADMIN_INITIAL_PASSWORD
                )
        );
    }

    @Test
    public void d_populateAllThreeInventoriesWithCustomMedications() throws Throwable{
        sequentialTestWrapper(
                wrapLoginAndLogout(InventoryTest::__private__populateInventoryWithCustomMedications,
                        TEST_ADMIN_USERNAME,
                        TEST_ADMIN_INITIAL_PASSWORD
                )
        );
    }

    @Test
    public void e_RemoveReaddButtonOnAllThreeInventoriesExistingMedications() throws Throwable{
        sequentialTestWrapper(
                wrapLoginAndLogout(InventoryTest::__private__RemoveReaddButtonOnAllInventoriesExistingMedications,
                        TEST_ADMIN_USERNAME,
                        TEST_ADMIN_INITIAL_PASSWORD
                )
        );
    }

    @Test
    public void f_RemoveAllExistingMedicationsAllThreeInventories() throws Throwable{
        sequentialTestWrapper(
                wrapLoginAndLogout(InventoryTest::__private__RemoveAllExistingMedicationsAllThreeInventories,
                        TEST_ADMIN_USERNAME,
                        TEST_ADMIN_INITIAL_PASSWORD
                )
        );
    }

    @Test
    public void g_ManuallyReaddExistingMedicationsAllThreeInventories() throws Throwable{
        sequentialTestWrapper(
                wrapLoginAndLogout(InventoryTest::__private__ManuallyReaddExistingMedicationsAllThreeInventories,
                        TEST_ADMIN_USERNAME,
                        TEST_ADMIN_INITIAL_PASSWORD
                )
        );
    }

    @Test
    public void h_RemoveCustomMedicationInventoriesAllThreeInventories() throws Throwable{
        sequentialTestWrapper(
                wrapLoginAndLogout(InventoryTest::__private__RemoveCustomMedicationInventoriesAllThreeInventories,
                        TEST_ADMIN_USERNAME,
                        TEST_ADMIN_INITIAL_PASSWORD)
        );
    }

    @Test
    public void i_ManuallyReaddCustomMedicationInventoriesAllThreeInventories() throws Throwable{
        sequentialTestWrapper(
                wrapLoginAndLogout(InventoryTest::__private__ManuallyReaddCustomMedicationInventoriesAllThreeInventories,
                        TEST_ADMIN_USERNAME,
                        TEST_ADMIN_INITIAL_PASSWORD
                )
        );
    }

    @Test
    public void j_SetMedicationQuantitiesToFiveEachAllThreeInventories() throws Throwable {
        sequentialTestWrapper(
                wrapLoginAndLogout(InventoryTest::__private__SetMedicationQuantitiesToFiveEachAllThreeInventories,
                        TEST_ADMIN_USERNAME,
                        TEST_ADMIN_INITIAL_PASSWORD
                )
        );
    }

    @Test
    public void l_TurnOffAllAdminConfigOptions() throws Throwable{
        sequentialTestWrapper(
                wrapLoginAndLogout(InventoryTest::__private__TurnOnAllAdminConfigOptions,
                        TEST_ADMIN_USERNAME,
                        TEST_ADMIN_INITIAL_PASSWORD
                )
        );
    }

    @Test
    public void k_TurnOnAllAdminConfigOptions() throws Throwable{
        sequentialTestWrapper(
                wrapLoginAndLogout(InventoryTest::__private__TurnOffAllAdminConfigOptions,
                        TEST_ADMIN_USERNAME,
                        TEST_ADMIN_INITIAL_PASSWORD
                )
        );
    }



//    @Test
//    public void m_CreateTestPatientThroughTriage() throws Throwable{
//
//    }
//
//    @Test
//    public void n_PrescribeAllFourMedsThrough() throws Throwable{
//
//    }

}
