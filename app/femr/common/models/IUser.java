package femr.common.models;

import java.util.List;

public interface IUser {

    int getId();

    String getFirstName();

    void setFirstName(String firstName);

    String getLastName();

    void setLastName(String lastName);

    String getEmail();

    void setEmail(String email);

    String getPassword();

    void setPassword(String password);

    List<IRole> getRoles();

    void setRoles(List<IRole> roles);

    void addRole(IRole role);
}
