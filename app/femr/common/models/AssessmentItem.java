package femr.common.models;

/**
 * Created by amneyiskandar on 3/2/15.
 */
public class AssessmentItem {

    private Integer id;
    private String value;

    public Integer getFieldName() {
        return id;
    }

    public void setFieldName(Integer id) {
        this.id = id;
    }

    public String  getFieldValue() {
        return value;
    }

    public void setFieldValue(String value) {
        this.value = value;
    }
}