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
package femr.ui.models.sessions;

import femr.util.stringhelpers.StringUtils;
import play.data.validation.ValidationError;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class CreateViewModel {
    private String email;
    private String password;
    //used for password reset
    private String newPassword;
    private String newPasswordVerify;
    private String firstName;
    private String lastName;
    private String language;
    private List<String> roles;
    private String notes;
    private String DateCreated; //Sam Zanni

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getNewPasswordVerify() {
        return newPasswordVerify;
    }

    public void setNewPasswordVerify(String newPasswordVerify) {
        this.newPasswordVerify = newPasswordVerify;
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

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public String getDateCreated() { return DateCreated; } //Sam Zanni

    public void setDateCreated(String DateCreated) { this.DateCreated = DateCreated; } //Sam Zanni
}
