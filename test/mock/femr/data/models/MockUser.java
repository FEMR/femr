package mock.femr.data.models;

import femr.common.models.IRole;
import femr.common.models.IUser;

import java.util.List;

public class MockUser implements IUser {

    public int id = 0;
    public String firstName = "";
    public String lastName = "";
    public String email = "";
    public String password = "";
    public List<IRole> roles;

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
    public void setRoles(List<IRole> roles) {
        this.roles = roles;
    }

    @Override
    public void addRole(IRole role) {
        roles.add(role);
    }
}
