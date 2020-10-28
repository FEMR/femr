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

import femr.common.models.MissionTripItem;
import femr.util.stringhelpers.StringUtils;
import play.data.validation.Constraints;
import play.data.validation.ValidationError;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

@Constraints.Validate
public class EditViewModel implements Constraints.Validatable<List<ValidationError>> {
    private Integer userId;
    private String firstName;
    private String lastName;
    private String email;
    private String passwordReset;
    private String newPassword;
    private String newPasswordVerify;
    private String notes;
    private List<String> roles;
    private List<MissionTripItem> missionTripItems;

    @Override
    public List<ValidationError> validate(){
        Pattern hasLowercase = Pattern.compile("[a-z]");    // Aditya Nerella

        Pattern hasUppercase = Pattern.compile("[A-Z]");
        Pattern hasNumber = Pattern.compile("\\d");
        List<ValidationError> errors = new ArrayList<>();
        if (StringUtils.isNullOrWhiteSpace(firstName))
            errors.add(new ValidationError("firstName", "first name is a required field"));
        if (StringUtils.isNullOrWhiteSpace(email))
            errors.add(new ValidationError("email", "email is a required field"));
        if (!newPassword.equals(newPasswordVerify))
            errors.add(new ValidationError("newPassword", "passwords do not match"));

        // **assuming if someone entered at least one character in password field field
        // then they want to actually change the password, if it is 0, the old password will still remain
        if(newPassword.length() > 0)
        {
            if (newPassword.length() < 8)
                errors.add(new ValidationError("newPassword", "password must be at least 8 characters long"));
            if (!hasUppercase.matcher(newPassword).find())
                errors.add(new ValidationError("newPassword", "password must have at least one uppercase character"));
            if (!hasNumber.matcher(newPassword).find())
                errors.add(new ValidationError("newPassword", "password must have at least one number"));
            if (!hasLowercase.matcher(newPassword).find()) //AJ Saclayan & Aditya Nerella Password Constraints
                errors.add(new ValidationError("newPassword", "password must have at least one lowercase character"));
        }
        
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

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<MissionTripItem> getMissionTripItems() {
        return missionTripItems;
    }

    public void setMissionTripItems(List<MissionTripItem> missionTripItems) {
        this.missionTripItems = missionTripItems;
    }
}
