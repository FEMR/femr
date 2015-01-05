package mock.femr.data.models;

import femr.data.models.core.IRole;
import femr.data.models.core.IUser;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

public class MockUser implements IUser {

    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private List<IRole> roles;
    private DateTime lastLogin;
    private Boolean deleted;
    private Boolean passwordReset;
    private String notes;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public List<IRole> getRoles() {
        return roles;
    }

    @Override
    public void setRoles(List<? extends IRole> roles) {
        this.roles = new ArrayList<>();
        for (IRole role : roles){
            this.roles.add(role);
        }
    }

    @Override
    public void addRole(IRole role) {
        roles.add(role);
    }

    @Override
    public DateTime getLastLogin() {
        return lastLogin;
    }

    @Override
    public void setLastLogin(DateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    @Override
    public Boolean getDeleted() {
        return deleted;
    }

    @Override
    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public Boolean getPasswordReset() {
        return passwordReset;
    }

    @Override
    public void setPasswordReset(Boolean passwordReset) {
        this.passwordReset = passwordReset;
    }

    @Override
    public String getNotes() {
        return notes;
    }

    @Override
    public void setNotes(String notes) {
        this.notes = notes;
    }
}
