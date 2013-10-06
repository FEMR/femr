package femr.business.dtos;

import femr.common.models.IRole;

import java.util.List;

public class CurrentUser {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private List<IRole> roles;

    public CurrentUser(int id, String firstName, String lastName, String email, List<IRole> roles) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.roles = roles;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public List<IRole> getRoles() {
        return roles;
    }
}
