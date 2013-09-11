package unit.app.controllers;

import edu.wayne.femr.controllers.SessionsController;
import edu.wayne.femr.views.html.sessions.create;
import mock.edu.wayne.femr.business.services.MockSessionsService;
import org.junit.Before;
import org.junit.Test;
import play.api.templates.Html;
import play.mvc.Result;
import play.test.Helpers;

import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.*;

public class SessionsControllerTest {

    public SessionsController sessionsController;
    public MockSessionsService sessionsService;

    @Before
    public void before() {
        sessionsService = new MockSessionsService();
        sessionsController = new SessionsController(sessionsService);
    }

    @Test
    public void testCreateActionRendersAResult() {
        Result result = sessionsController.create();
        assertThat(result).isNotNull();
    }

    @Test
    public void testCreateActionRendersIndexView() {
        Html expectedHtml = create.render();

        Result result = sessionsController.create();

        assertThat(result);
        assertThat(contentAsString(result)).isEqualTo(contentAsString(expectedHtml));
    }

    @Test
    public void testCreateActionReturnsStatusCode200() {
        Result result = sessionsController.create();

        int expectedStatus = Helpers.OK;

        assertThat(status(result)).isEqualTo(expectedStatus);
    }

    @Test
    public void testCreateActionReturnsProperHeaders() {
        Result result = sessionsController.create();


        String expectedCharset = "utf-8";
        String expectedContentType = "text/html";

        assertThat(headers(result)).isNotEmpty();
        assertThat(headers(result).size()).isEqualTo(1);
        assertThat(headers(result).containsKey("Content-Type")).isTrue();
        assertThat(charset(result)).isEqualTo(expectedCharset);
        assertThat(contentType(result)).isEqualTo(expectedContentType);
    }
}
