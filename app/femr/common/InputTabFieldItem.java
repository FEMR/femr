package femr.common;

public class InputTabFieldItem {
    private final String name;
    private final String type;
    private final String size;
    private final Integer order;
    private final String placeholder;
    private final String value;
    private final String chiefComplaint;
    private final boolean isCustom;

    /**
     * @param name           the name of the field, not null
     * @param type           the fields type e.g. number, text, may be null
     * @param size           the size of the field e.g. small, med, large, may be null
     * @param order          sorting order for the field, may be null
     * @param placeholder    placeholder text for the field, may be null
     * @param value          current value of the field, may be null
     * @param chiefComplaint what chief complaint the field belongs to,, may be null
     * @param isCustom       identifies if the tabfielditem is custom made, not null
     */
    public InputTabFieldItem(String name, String type, String size, Integer order, String placeholder, String value, String chiefComplaint, boolean isCustom) {
        this.name = name;
        this.type = type;
        this.size = size;
        this.order = order;
        this.placeholder = placeholder;
        this.value = value;
        this.chiefComplaint = chiefComplaint;
        this.isCustom = isCustom;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getSize() {
        return size;
    }

    public Integer getOrder() {
        return order;
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public String getValue() {
        return value;
    }

    public String getChiefComplaint() {
        return chiefComplaint;
    }

    public boolean isCustom() {
        return isCustom;
    }
}
