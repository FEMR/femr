package femr.business.dtos;

import femr.common.models.IRole;

import java.util.List;

public class CurrentUser {
    private String firstName;
    private String lastName;
    private String email;
    private List<IRole> roles;

    public CurrentUser(String firstName, String lastName, String email, List<IRole> roles) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.roles = roles;
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
