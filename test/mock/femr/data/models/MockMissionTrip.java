package mock.femr.data.models;

import femr.data.models.core.IMissionCity;
import femr.data.models.core.IMissionTeam;
import femr.data.models.core.IMissionTrip;
import femr.data.models.core.IUser;

import java.util.Date;
import java.util.List;

public class MockMissionTrip implements IMissionTrip {


    @Override
    public int getId() {
        return 1;
    }

    @Override
    public IMissionTeam getMissionTeam() {
        return null;
    }

    @Override
    public void setMissionTeam(IMissionTeam missionTeam) {

    }

    @Override
    public IMissionCity getMissionCity() {
        return null;
    }

    @Override
    public void setMissionCity(IMissionCity missionCity) {

    }

    @Override
    public Date getStartDate() {
        return null;
    }

    @Override
    public void setStartDate(Date startDate) {

    }

    @Override
    public Date getEndDate() {
        return null;
    }

    @Override
    public void setEndDate(Date endDate) {

    }

    @Override
    public List<IUser> getUsers() {
        return null;
    }

    @Override
    public void setUsers(List<IUser> users) {

    }

    @Override
    public void addUser(IUser user) {

    }

    @Override
    public void removeUser(int userId) {

    }
}
