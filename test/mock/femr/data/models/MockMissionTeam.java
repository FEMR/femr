package mock.femr.data.models;

import femr.data.models.core.IMissionTeam;
import femr.data.models.mysql.MissionTrip;

import java.util.ArrayList;
import java.util.List;

public class MockMissionTeam implements IMissionTeam {

    private int id = 1;
    private String name = "Team fEMR";
    private String location = "California";
    private String description = "Medical mission team";
    private String languageCode = "en";
    private List<MissionTrip> missionTrips = new ArrayList<>();

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
    public String getLocation() {
        return location;
    }

    @Override
    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public List<MissionTrip> getMissionTrips() {
        return missionTrips;
    }

    @Override
    public void setMissionTrips(List<MissionTrip> missionTrips) {
        this.missionTrips = missionTrips;
    }

    @Override
    public String getLanguageCode() {
        return languageCode;
    }

    @Override
    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }
}
