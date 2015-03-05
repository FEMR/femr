package femr.ui.models.history;

import femr.common.models.PatientEncounterItem;
import femr.common.models.PatientItem;

/**
 * Created by amneyiskandar on 3/2/15.
 */
public class fieldValueViewModel {
    private Integer id;
    private Integer value;

    public Integer getFieldName() {
        return id;
    }

    public void setFieldName(Integer id) {
        this.id = id;
    }

    public Integer  getFieldValue() {
        return value;
    }

    public void setFieldValue(Integer value) {
        this.value = value;
    }
}
