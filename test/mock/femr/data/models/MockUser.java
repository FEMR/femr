package mock.femr.data.models;

import femr.data.models.core.IMissionTrip;
import femr.data.models.core.IRole;
import femr.data.models.core.IUser;
import org.joda.time.DateTime;

import java.util.List;

public class MockUser implements IUser {

    private int id = -1;
    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    @Override
    public String getFirstName() {
        return null;
    }

    @Override
    public void setFirstName(String firstName) {

    }

    @Override
    public String getLastName() {
        return null;
    }

    @Override
    public void setLastName(String lastName) {

    }

    @Override
    public String getEmail() {
        return null;
    }

    @Override
    public void setEmail(String email) {

    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public void setPassword(String password) {

    }

    @Override
    public List<IRole> getRoles() {
        return null;
    }

    @Override
    public void setRoles(List<? extends IRole> roles) {

    }

    @Override
    public void addRole(IRole role) {

    }

    @Override
    public DateTime getLastLogin() {
        return null;
    }

    @Override
    public void setLastLogin(DateTime lastLogin) {

    }

    @Override
    public Boolean getDeleted() {
        return null;
    }

    @Override
    public void setDeleted(Boolean deleted) {

    }

    @Override
    public Boolean getPasswordReset() {
        return null;
    }

    @Override
    public void setPasswordReset(Boolean passwordReset) {

    }

    @Override
    public String getNotes() {
        return null;
    }

    @Override
    public void setNotes(String notes) {

    }

    @Override
    public DateTime getPasswordCreatedDate() {
        return null;
    }

    @Override
    public void setPasswordCreatedDate(DateTime date) {

    }

    @Override
    public List<IMissionTrip> getMissionTrips() {
        return null;
    }

    @Override
    public void setMissionTrips(List<IMissionTrip> missionTrips) {

    }

    @Override
    public DateTime getDateCreated() {
        return null;
    }

    @Override
    public void setDateCreated(DateTime DateCreated) {

    }

    @Override
    public Integer getCreatedBy() {
        return null;
    }

    @Override
    public void setCreatedBy(Integer CreatedBy) {

    }
}
