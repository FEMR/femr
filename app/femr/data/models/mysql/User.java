/*
     fEMR - fast Electronic Medical Records
     Copyright (C) 2014  Team fEMR

     fEMR is free software: you can redistribute it and/or modify
     it under the terms of the GNU General Public License as published by
     the Free Software Foundation, either version 3 of the License, or
     (at your option) any later version.

     fEMR is distributed in the hope that it will be useful,
     but WITHOUT ANY WARRANTY; without even the implied warranty of
     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
     GNU General Public License for more details.

     You should have received a copy of the GNU General Public License
     along with fEMR.  If not, see <http://www.gnu.org/licenses/>. If
     you have any questions, contact <info@teamfemr.org>.
*/
package femr.data.models.mysql;

import femr.data.models.core.IMissionTrip;
import femr.data.models.core.IRole;
import femr.data.models.core.IUser;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.util.ArrayList;
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
    @Column(name = "notes")
    private String notes;
    //AJ Saclayan Password Constraints
    @Column(name = "passwordCreatedDate", nullable = false)
    private DateTime PasswordCreatedDate;
    @Column (name = "date_created", nullable = false) //Sam Zanni
    private DateTime DateCreated; //Sam Zanni
    @Column (name = "created_by", unique = true, nullable = false) //Sam Zanni
    private Integer CreatedBy; //Sam Zanni
    @ManyToMany(fetch = FetchType.LAZY, targetEntity = MissionTrip.class)
    @JoinTable(
            name = "mission_trip_users",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "mission_trip_id", referencedColumnName = "id")})
    private List<IMissionTrip> missionTrips;
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
        for (IRole role : roles) {
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

    /**
     * {@inheritDoc}
     */
    @Override
    public String getNotes() {
        return notes;
    }

    @Override
    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    @Override
    public DateTime getDateCreated() { return DateCreated; }

    @Override
    public void setDateCreated(DateTime DateCreated) { this.DateCreated = DateCreated; }

    @Override
    public Integer getCreatedBy() { return CreatedBy; }

    @Override
    public void setCreatedBy(Integer CreatedBy) { this.CreatedBy = CreatedBy; }

    @Override
    public List<IMissionTrip> getMissionTrips() {
        return missionTrips;
    }

    @Override
    public void setMissionTrips(List<IMissionTrip> missionTrips) {
        this.missionTrips = missionTrips;
    }

    @Override
    public DateTime getPasswordCreatedDate(){
        return PasswordCreatedDate;
    }

    @Override
    public void setPasswordCreatedDate(DateTime date){
        this.PasswordCreatedDate = date;
    }
}
