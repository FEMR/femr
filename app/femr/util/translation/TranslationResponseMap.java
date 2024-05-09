package femr.util.translation;
import femr.ui.controllers.BackEndControllerHelper;
import java.util.*;
import play.libs.Json;


// Harrison Shu
// represents the Response item that is returned from the backend Java GET Endpoint
public class TranslationResponseMap {
    List<String> rtlLanguages = Arrays.asList("he", "ar");
    private String toLanguage;
    private String fromLanguage;
    private String text;
    Map<String, Object> responseMap = new HashMap<>();

    public TranslationResponseMap(String fromLanguage, String toLanguage, String text) {
        this.fromLanguage = fromLanguage;
        this.toLanguage = toLanguage;
        this.text = text;
        this.populateResponseMap();
    }

    private void populateResponseMap() {
        populateIsRtl();
        populateTranslation();
    }

    private void populateIsRtl() {
        responseMap.put("toLanguageIsRtl", rtlLanguages.contains(toLanguage));
        responseMap.put("fromLanguageIsRtl", rtlLanguages.contains(fromLanguage));
    }

    private void populateTranslation() {
        // if same to same (like en to en) don't translate
        if (Objects.equals(toLanguage, fromLanguage)) {
            responseMap.put("translation", "SameToSame");
        } else {
            String data = "";
            try {
                data = BackEndControllerHelper.translate(text, fromLanguage, toLanguage);
            } catch (Exception e) {
                e.printStackTrace();
            }
            responseMap.put("translation", data);
        }
    }

    private Map<String, Object> getResponseMap() {
        return this.responseMap;
    }

    public com.fasterxml.jackson.databind.JsonNode getResponseJson() {
        return Json.toJson(responseMap);
    }

}
