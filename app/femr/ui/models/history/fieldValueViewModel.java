package femr.ui.models.history;

/**
 * Created by amneyiskandar on 3/2/15.
 */
public class fieldValueViewModel {
    private String name;
    private String value;
    private Integer chiefComplaintID;
    private String chiefComplaintName;

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

    public Integer getChiefComplaintID() {
        return chiefComplaintID;
    }

    public void setChiefComplaintID(Integer chiefComplaintID) {
        this.chiefComplaintID = chiefComplaintID;
    }

    public String getChiefComplaintName() {
        return chiefComplaintName;
    }

    public void setChiefComplaintName(String chiefComplaintName) {
        this.chiefComplaintName = chiefComplaintName;
    }
}
