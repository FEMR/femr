package mock.femr.data.models;

import femr.data.models.core.IMissionCity;
import femr.data.models.core.IMissionTeam;
import femr.data.models.core.IMissionTrip;
import femr.data.models.core.IUser;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MockMissionTrip implements IMissionTrip {

    private int id = 1;
    private IMissionTeam missionTeam = new MockMissionTeam();
    private IMissionCity missionCity = new MockMissionCity();
    private Date startDate;
    private Date endDate;
    private List<IUser> users = new ArrayList<>();

    public MockMissionTrip() {
        Calendar cal = Calendar.getInstance();
        cal.set(2024, Calendar.MARCH, 1);
        this.startDate = cal.getTime();
        cal.set(2024, Calendar.MARCH, 15);
        this.endDate = cal.getTime();
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public IMissionTeam getMissionTeam() {
        return missionTeam;
    }

    @Override
    public void setMissionTeam(IMissionTeam missionTeam) {
        this.missionTeam = missionTeam;
    }

    @Override
    public IMissionCity getMissionCity() {
        return missionCity;
    }

    @Override
    public void setMissionCity(IMissionCity missionCity) {
        this.missionCity = missionCity;
    }

    @Override
    public Date getStartDate() {
        return startDate;
    }

    @Override
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Override
    public Date getEndDate() {
        return endDate;
    }

    @Override
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public List<IUser> getUsers() {
        return users;
    }

    @Override
    public void setUsers(List<IUser> users) {
        this.users = users;
    }

    @Override
    public void addUser(IUser user) {
        this.users.add(user);
    }

    @Override
    public void removeUser(int userId) {
        this.users.removeIf(user -> user.getId() == userId);
    }
}