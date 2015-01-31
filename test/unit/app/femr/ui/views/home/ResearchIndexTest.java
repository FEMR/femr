package unit.app.femr.ui.views.home;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import play.libs.F;
import play.test.FakeApplication;
import play.test.TestBrowser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static play.test.Helpers.*;

public class ResearchIndexTest {

    public static FakeApplication app;

    private WebDriver driver;
    private String baseUrl;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();

    List<String> primaryDatasets;
    List<String> secondaryDatasets;
    List<String> graphTypes;

    public ResearchIndexTest(){

        primaryDatasets = new ArrayList<>();
        primaryDatasets.add("age");
        primaryDatasets.add("gender");
        primaryDatasets.add("height");
        primaryDatasets.add("weight");
        primaryDatasets.add("pregnancyStatus");
        primaryDatasets.add("pregnancyTime");
        primaryDatasets.add("prescribedMeds");
        primaryDatasets.add("dispensedMeds");
        primaryDatasets.add("temperature");
        primaryDatasets.add("bloodPressureSystolic");
        primaryDatasets.add("bloodPressureDiastolic");
        primaryDatasets.add("heartRate");
        primaryDatasets.add("respirations");
        primaryDatasets.add("oxygenSaturation");
        primaryDatasets.add("glucose");

        secondaryDatasets = new ArrayList<>();
        secondaryDatasets.add("age");
        secondaryDatasets.add("gender");
        secondaryDatasets.add("pregnancyStatus");

        graphTypes = new ArrayList<>();
        graphTypes.add("bar");
        graphTypes.add("pie");
        graphTypes.add("line");
        graphTypes.add("scatter");
        graphTypes.add("stacked-bar");
        graphTypes.add("grouped-bar");
        graphTypes.add("table");

    }


    @Before
    public void setUp() throws Exception {

        Map<String, String> settings = new HashMap<String, String>();
        settings.put("db.default.url", "jdbc:mysql://localhost/femr?characterEncoding=UTF-8");
        settings.put("db.default.user", "femr");
        settings.put("db.default.password", "PnhcTUQ9xpraJf7e");

        app = fakeApplication(settings);

        baseUrl = "http://localhost:3333/";
        System.setProperty("webdriver.chrome.driver", "/Users/dev/Desktop/Selenium/chromedriver");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

        running(testServer(3333, app), driver, new F.Callback<TestBrowser>() {
            public void invoke(TestBrowser browser) {

                runAllTests();
            }

        });


    }


    @Test
    public void sampleTest(){


    }

    public void runAllTests() {

        try {
            testLogin();
            testSingleDataset();
            testDoubleDataSet();
        }
        catch (Exception e){

            System.out.println("** Exception Found **");
            System.out.println(e.getMessage());
        }
    }

    public void testLogin() throws Exception {

        driver.get(baseUrl);
        driver.findElement(By.name("email")).sendKeys("kdunlap4918@gmail.com");
        driver.findElement(By.name("password")).sendKeys("password");
        driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();

        WebDriverWait driverWait = new WebDriverWait(driver, 30);
        driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Research")));

        driver.findElement(By.linkText("Research")).click();
        assertEquals("Research | fEMR - The free EMR", driver.getTitle());

        driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Primary Dataset")));
    }

    public void testSingleDataset() throws Exception {

        for( String name : primaryDatasets ){

            try {
                driver.findElement(By.linkText("Primary Dataset")).click();
                WebElement primaryLink = driver.findElement(By.xpath("//a[@data-dname1=\"" + name + "\" and (not(@class) or @class!=\"disabled\")]"));
                primaryLink.click();
            }
            catch(NoSuchElementException e){

                driver.findElement(By.linkText("Primary Dataset")).click();
                continue;
            }

            for( String graph : graphTypes ) {

                driver.findElement(By.linkText("Graph Type")).click();
                WebElement graphNode = null;
                try{

                    graphNode = driver.findElement(By.xpath("//a[@data-gtype=\"" + graph + "\" and (not(@class) or @class!=\"disabled\")]"));
                }
                catch(NoSuchElementException e){

                    driver.findElement(By.linkText("Graph Type")).click();
                    continue;
                }

                if (graphNode != null) {

                    graphNode.click();
                    driver.findElement(By.id("submit-button")).click();

                    if (!graph.equals("table")){

                        // Wait for graph to load - g elements will show up?
                        WebDriverWait driverWait = new WebDriverWait(driver, 10);
                        driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("g")));

                        driver.findElement(By.id("save-button")).click();
                        driver.findElement(By.xpath("//a[@data-imagesize=\"small\"]")).click();
                    }
                }

            }
        }

    }

    public void testDoubleDataSet(){

        for( String name : primaryDatasets ){

            try {
                driver.findElement(By.linkText("Primary Dataset")).click();
                WebElement primaryLink = driver.findElement(By.xpath("//a[@data-dname1=\"" + name + "\" and (not(@class) or @class!=\"disabled\")]"));
                primaryLink.click();
            }
            catch(NoSuchElementException e){

                System.out.println("Primary Not Found");
                driver.findElement(By.linkText("Primary Dataset")).click();
                continue;
            }

            for( String name2 : secondaryDatasets ){

                try {

                    driver.findElement(By.linkText("Secondary Dataset")).click();
                    WebElement secondaryLink = driver.findElement(By.xpath("//a[@data-dname2=\"" + name2 + "\" and (not(@class) or @class!=\"disabled\")]"));
                    secondaryLink.click();
                }
                catch(NoSuchElementException e){

                    System.out.println("Secondary Not Found");
                    driver.findElement(By.linkText("Secondary Dataset")).click();
                    continue;
                }

                for( String graph : graphTypes ) {


                    driver.findElement(By.linkText("Graph Type")).click();
                    WebElement graphNode = null;
                    try{

                        graphNode = driver.findElement(By.xpath("//a[@data-gtype=\"" + graph + "\" and (not(@class) or @class!=\"disabled\")]"));
                    }
                    catch(NoSuchElementException e){

                        System.out.println("Graph Not Found");
                        driver.findElement(By.linkText("Graph Type")).click();
                        continue;
                    }

                    if (graphNode != null) {

                        graphNode.click();
                        driver.findElement(By.id("submit-button")).click();

                        if (!graph.equals("table")){
                            // Wait for graph to load - g elements will show up?
                            WebDriverWait driverWait = new WebDriverWait(driver, 10);
                            driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("g")));

                            driver.findElement(By.id("save-button")).click();
                            driver.findElement(By.xpath("//a[@data-imagesize=\"small\"]")).click();
                        }
                    }
                }

            }
        }
    }


    @After
    public void tearDown() throws Exception {

        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }

        //Helpers.stop(app);
        driver.quit();
    }

    private boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    private String closeAlertAndGetItsText() {
        try {
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            if (acceptNextAlert) {
                alert.accept();
            } else {
                alert.dismiss();
            }
            return alertText;
        } finally {
            acceptNextAlert = true;
        }
    }

}
