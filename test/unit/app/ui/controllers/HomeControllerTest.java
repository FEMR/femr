package unit.app.ui.controllers;

import edu.wayne.femr.ui.controllers.HomeController;
import edu.wayne.femr.ui.views.html.home.index;
import mock.edu.wayne.femr.business.services.MockSessionsService;
import org.junit.Before;
import org.junit.Test;
import play.api.templates.Html;
import play.mvc.Result;
import play.test.Helpers;

import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.*;

public class HomeControllerTest {

    public HomeController homeController;
    public MockSessionsService mockSessionService;

    @Before
    public void setup() {
        mockSessionService = new MockSessionsService();
        homeController = new HomeController(mockSessionService);
    }

    @Test
    public void testIndexActionRendersAResult() {
        Result result = homeController.index();
        assertThat(result).isNotNull();
    }

    @Test
    public void testIndexActionRendersIndexView() {
        Html expectedHtml = index.render(null);

        Result result = homeController.index();

        assertThat(result);
        assertThat(contentAsString(result)).isEqualTo(contentAsString(expectedHtml));
    }

    @Test
    public void testIndexActionReturnsStatusCode200() {
        Result result = homeController.index();

        int expectedStatus = Helpers.OK;

        assertThat(status(result)).isEqualTo(expectedStatus);
    }

    @Test
    public void testIndexActionReturnsProperHeaders() {
        Result result = homeController.index();


        String expectedCharset = "utf-8";
        String expectedContentType = "text/html";

        assertThat(headers(result)).isNotEmpty();
        assertThat(headers(result).size()).isEqualTo(1);
        assertThat(headers(result).containsKey("Content-Type")).isTrue();
        assertThat(charset(result)).isEqualTo(expectedCharset);
        assertThat(contentType(result)).isEqualTo(expectedContentType);
    }
}
