package unit.app.femr.business.services;

import femr.ui.controllers.MedicalController;
import femr.util.translation.TranslationResponseMap;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import com.fasterxml.jackson.databind.JsonNode;
import play.libs.Json;
import java.util.Map;
import static org.junit.Assert.*;

public class TranslationResponseMapTest {

    private TranslationResponseMap translationResponseMap;
    private MockedStatic<MedicalController> medicalControllerMock;

    @Before
    public void setUp() {
        // Initialize the mock for the static method
        medicalControllerMock = Mockito.mockStatic(MedicalController.class);
    }

    @After
    public void tearDown() {
        // Close the static mock after the tests
        medicalControllerMock.close();
    }

    @Test
    public void testSameLanguage_noTranslation() {
        String fromLanguage = "en";
        String toLanguage = "en";
        String text = "Hello";

        translationResponseMap = new TranslationResponseMap(fromLanguage, toLanguage, text);

        Map<String, Object> responseMap = translationResponseMap.getResponseMap();
        JsonNode responseJson = translationResponseMap.getResponseJson();

        assertEquals("SameToSame", responseMap.get("translation"));
        assertEquals(Json.toJson(responseMap), responseJson);
    }

    @Test
    public void testDifferentLanguages_translationOccurs() {
        String fromLanguage = "en";
        String toLanguage = "es";
        String text = "Hello";
        String expectedTranslation = "Hola";

        // mock the static translate method output
        medicalControllerMock.when(() -> MedicalController.translate(text, fromLanguage, toLanguage)).thenReturn(expectedTranslation);

        translationResponseMap = new TranslationResponseMap(fromLanguage, toLanguage, text);

        Map<String, Object> responseMap = translationResponseMap.getResponseMap();
        JsonNode responseJson = translationResponseMap.getResponseJson();

        assertEquals(expectedTranslation, responseMap.get("translation"));
        assertEquals(Json.toJson(responseMap), responseJson);
    }

    @Test
    public void testRtlLanguages() {
        String fromLanguage = "en";
        String toLanguage = "ar";
        String text = "Hello";
        String expectedTranslation = "مرحبا";

        // mock the static translate method output
        medicalControllerMock.when(() -> MedicalController.translate(text, fromLanguage, toLanguage)).thenReturn(expectedTranslation);

        translationResponseMap = new TranslationResponseMap(fromLanguage, toLanguage, text);

        Map<String, Object> responseMap = translationResponseMap.getResponseMap();
        JsonNode responseJson = translationResponseMap.getResponseJson();

        assertTrue((Boolean) responseMap.get("toLanguageIsRtl")); // ar isRtl is True
        assertFalse((Boolean) responseMap.get("fromLanguageIsRtl")); // en isRtl is False
        assertEquals(expectedTranslation, responseMap.get("translation"));
        assertEquals(Json.toJson(responseMap), responseJson);
    }

}
