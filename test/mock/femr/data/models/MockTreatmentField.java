package mock.femr.data.models;

import femr.common.models.ITreatmentField;

/**
 * Created by Kevin on 2/9/14.
 */
public class MockTreatmentField implements ITreatmentField {
    private int id = 0;
    private String name = "";

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Boolean getDeleted() {
        return null;
    }

    @Override
    public void setDeleted(Boolean deleted) {

    }
}
