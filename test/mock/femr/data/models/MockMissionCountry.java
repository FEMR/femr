package mock.femr.data.models;

import femr.data.models.core.IMissionCountry;

public class MockMissionCountry implements IMissionCountry {

    private int id = 1;
    private String name = "Guatemala";

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
}