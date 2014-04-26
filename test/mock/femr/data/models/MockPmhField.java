package mock.femr.data.models;

import femr.common.models.IPmhField;

public class MockPmhField implements IPmhField {
    private int id = 0;
    private String name = "";
    @Override
    public int getId() {
        return id;
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

    public void setId(int id) {
        this.id = id;
    }
}
