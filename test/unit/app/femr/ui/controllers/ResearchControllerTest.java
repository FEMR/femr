//package unit.app.femr.ui.controllers;
//
//
//import femr.ui.models.research.FilterViewModel;
//import org.junit.Before;
//import org.junit.Test;
//import org.openqa.selenium.htmlunit.HtmlUnitDriver;
//import play.mvc.Result;
//import play.libs.F;
//import play.test.FakeRequest;
//import play.test.TestBrowser;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import static org.fest.assertions.Assertions.assertThat;
//import static play.mvc.Http.Status.OK;
//import static play.test.Helpers.*;
//
//public class ResearchControllerTest{
//
//    @Before
//    public void setUp() {
//
//
//    }
//
//    @Test
//    public void indexGetCheck(){
//
//        /*
//        Result result = callAction(route());
//        assertThat(status(result)).isEqualTo(OK);
//        assertThat(contentType(result)).isEqualTo("text/html");
//        assertThat(charset(result)).isEqualTo("utf-8");
//        assertThat(contentAsString(result)).contains("hello, world");
//        */
//    }
//
//    @Test
//    public void getGraphPostCheck(){
//
//        FilterViewModel filters = new FilterViewModel();
//
//        filters.setPrimaryDataset("age");
//
//
//
//    }
//
//
//    @Test
//    public void runInBrowser() {
//
//        /*
//        Map<String, String> map = new HashMap<>();
//
//        running(testServer(9000), HtmlUnitDriver.class, new F.Callback<TestBrowser>() {
//
//            public void invoke(TestBrowser browser) {
//                browser.goTo("http://localhost:9000");
//                assertThat(browser.$("body").getTexts().get(0)).isEqualTo("hello, world");
//            }
//        });
//        */
//    }
//
//}
//
