package femr.ui.models.history;

import femr.common.models.PatientEncounterItem;
import femr.common.models.PatientItem;

/**
 * Created by amneyiskandar on 3/2/15.
 */
public class fieldValueViewModel {
    private String name;
    private String value;

    public String getFieldName() {
        return name;
    }

    public void setFieldName(String name) {
        this.name = name;
    }

    public String  getFieldValue() {
        return value;
    }

    public void setFieldValue(String value) {
        this.value = value;
    }
}
