package unit.app.ui.views.home;

import org.junit.Before;
import org.junit.Test;
import play.mvc.Content;

import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.contentAsString;

public class IndexTest {

    public Content html;

    @Before
    public void before() {
        html = edu.wayne.femr.ui.views.html.home.index.render(null);
    }

    @Test
    public void testPassesCorrectPageTitleToLayout() {
        assertThat(contentAsString(html)).contains("<title>Home | fEMR - The free EMR</title>");
    }

    @Test
    public void testPageContent() {
        assertThat(contentAsString(html)).contains("<p>fEMR - The free EMR</p>");
    }
}
