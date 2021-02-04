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
package femr.common.models;

import java.util.ArrayList;
import java.util.List;

public class UserItem {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private List<String> roles;
    private String lastLoginDate;
    private String notes;
    private boolean isDeleted;
    private boolean isPasswordReset;
    private String PasswordCreatedDate;
    private String DateCreated; //Sam Zanni
    private Integer CreatedBy; //Sam Zanni

    public UserItem() {
        this.roles = new ArrayList<>();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Roles the user currently has
     */
    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public void addRole(String role) {
        this.roles.add(role);
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(String lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isPasswordReset() {
        return isPasswordReset;
    }

    public void setPasswordReset(boolean isPasswordReset) {
        this.isPasswordReset = isPasswordReset;
    }

    //AJ Saclayan Password Constraints
    public String getPasswordCreatedDate() {
        return PasswordCreatedDate;
    }

    public void setPasswordCreatedDate(String PasswordCreatedDate) {
        this.PasswordCreatedDate = PasswordCreatedDate;
    }

    @Override
    public boolean equals(final Object obj) {

        if (obj == null || obj == this || !(obj instanceof UserItem)) {
            return false;
        }

        UserItem otherUserItem = (UserItem) obj;

        if (!otherUserItem.getEmail().equals(this.getEmail())) return false;
        if (otherUserItem.getId() != this.getId()) return false;

        return true;
    }

    public String getDateCreated() {return DateCreated; } //Sam Zanni

    public void setDateCreated (String DateCreated) { this.DateCreated = DateCreated; } //Sam Zanni

    public Integer getCreatedBy() {return CreatedBy; } //Sam Zanni

    public void setCreatedBy (Integer CreatedBy) { this.CreatedBy = CreatedBy; } //Sam Zanni

 }
