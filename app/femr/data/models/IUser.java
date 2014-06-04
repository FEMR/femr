package femr.data.models;

import femr.data.models.IRole;
import org.joda.time.DateTime;

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

    DateTime getLastLogin();

    void setLastLogin(DateTime lastLogin);

    Boolean getDeleted();

    void setDeleted(Boolean deleted);

    Boolean getPasswordReset();

    void setPasswordReset(Boolean passwordReset);
}
