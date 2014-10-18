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
package femr.ui.models.admin.users;

import femr.util.stringhelpers.StringUtils;
import play.data.validation.ValidationError;
import java.util.ArrayList;
import java.util.List;

public class CreateViewModel {
    private Integer userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Boolean isDeleted;
    private String passwordReset;
    private List<String> roles;
    private String newPassword;
    private String newPasswordVerify;
    private String notes;

    public List<ValidationError> validate(){
        List<ValidationError> errors = new ArrayList<>();
        if (StringUtils.isNullOrWhiteSpace(firstName))
            errors.add(new ValidationError("firstName", "first name is a required field"));
        if (StringUtils.isNullOrWhiteSpace(email))
            errors.add(new ValidationError("email", "email is a required field"));
        if (StringUtils.isNullOrWhiteSpace(password))
            errors.add(new ValidationError("password", "password is a required field"));
        if (roles == null || roles.size() < 1)
            errors.add(new ValidationError("roles", "a user needs at least one role"));

        return errors.isEmpty() ? null : errors;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public String getPasswordReset() {
        return passwordReset;
    }

    public void setPasswordReset(String passwordReset) {
        this.passwordReset = passwordReset;
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

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}

