package mock.femr.data.models;

import femr.data.models.core.IMissionCity;
import femr.data.models.core.IMissionCountry;

public class MockMissionCity implements IMissionCity {

    private int id = 1;
    private String name = "Antigua";
    private IMissionCountry missionCountry = new MockMissionCountry();

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
    public IMissionCountry getMissionCountry() {
        return missionCountry;
    }

    @Override
    public void setMissionCountry(IMissionCountry missionCountry) {
        this.missionCountry = missionCountry;
    }
}
