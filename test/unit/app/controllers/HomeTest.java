package unit.app.controllers;

import org.junit.Test;
import play.mvc.Content;

import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.contentAsString;
import static play.test.Helpers.contentType;

public class HomeTest {
    @Test
    public void renderTemplate() {
        Content html = edu.wayne.femr.views.html.home.index.render("Home");
        assertThat(contentType(html)).isEqualTo("text/html");
        assertThat(contentAsString(html)).contains("Home");
    }
}
