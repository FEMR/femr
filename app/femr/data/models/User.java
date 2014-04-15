package femr.data.models;

import femr.common.models.IRole;
import femr.common.models.IUser;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class User implements IUser {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    private int id;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column(name = "email", unique = true, nullable = false)
    private String email;
    @Column(name = "password", nullable = false)
    private String password;
    @ManyToMany(fetch = FetchType.LAZY, targetEntity = Role.class, cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_roles",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    private List<IRole> roles;
    @Column(name = "last_login", nullable = false)
    private DateTime lastLogin;
    @Column(name = "isDeleted", nullable = false)
    private Boolean deleted;
    @Column(name = "isPasswordReset", nullable = false)
    private Boolean passwordReset;

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
