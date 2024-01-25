package femr.util;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TranslationJson {
    public final String translatedText;

    @JsonCreator
    private TranslationJson(@JsonProperty("translate_data") String translatedText){
        this.translatedText = translatedText;
    }
}
