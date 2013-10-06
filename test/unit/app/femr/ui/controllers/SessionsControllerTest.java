//package unit.app.femr.ui.controllers;
//
//import femr.ui.controllers.SessionsController;
//import femr.ui.models.sessions.CreateViewModel;
//import femr.ui.views.html.sessions.create;
//import mock.femr.business.services.MockSessionsService;
//import org.junit.Before;
//import org.junit.Test;
//import play.api.templates.Html;
//import play.data.Form;
//import play.mvc.Result;
//import play.test.Helpers;
//
//import static org.fest.assertions.Assertions.assertThat;
//import static play.test.Helpers.*;
//
//public class SessionsControllerTest {
//
//    public SessionsController sessionsController;
//    public MockSessionsService sessionsService;
//
//    @Before
//    public void setup() {
//        sessionsService = new MockSessionsService();
//        sessionsController = new SessionsController(sessionsService);
//    }
//
//    @Test
//    public void testGetCreateActionRendersAResult() {
//        Result result = sessionsController.createPost();
//        assertThat(result).isNotNull();
//    }
//
//    @Test
//    public void testGetCreateActionRendersIndexView() {
//        Html expectedHtml = create.render(Form.form(CreateViewModel.class));
//
//        Result result = sessionsController.createGet();
//
//        assertThat(result);
//        assertThat(contentAsString(result)).isEqualTo(contentAsString(expectedHtml));
//    }
//
//    @Test
//    public void testGetCreateActionReturnsStatusCode200() {
//        Result result = sessionsController.createGet();
//
//        int expectedStatus = Helpers.OK;
//
//        assertThat(status(result)).isEqualTo(expectedStatus);
//    }
//
//    @Test
//    public void testGetCreateActionReturnsProperHeaders() {
//        Result result = sessionsController.createGet();
//
//        String expectedCharset = "utf-8";
//        String expectedContentType = "text/html";
//
//        assertThat(headers(result)).isNotEmpty();
//        assertThat(headers(result).size()).isEqualTo(1);
//        assertThat(headers(result).containsKey("Content-Type")).isTrue();
//        assertThat(charset(result)).isEqualTo(expectedCharset);
//        assertThat(contentType(result)).isEqualTo(expectedContentType);
//    }
//}
