package femr.common.models;

public class PrescriptionItem {
    private int id;
    private String name;
    private Integer replacementId;


    public PrescriptionItem(String name){
        this.name = name;
    }

    public PrescriptionItem() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getReplacementId() {
        return replacementId;
    }

    public void setReplacementId(Integer replacementId) {
        this.replacementId = replacementId;
    }
}
