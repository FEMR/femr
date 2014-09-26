package femr.ui.models.medical.json;

/**
 * Used to get custom tab fields in addition to hpi fields
 * when multiple chief complaints exist
 */
public class JCustomField {
    String name;
    String value;

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}
