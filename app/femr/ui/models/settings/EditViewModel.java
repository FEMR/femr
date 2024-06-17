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

package femr.ui.models.settings;

import femr.util.stringhelpers.StringUtils;
import play.data.validation.Constraints;
import play.data.validation.ValidationError;

import java.util.ArrayList;
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

    private String languageCode;

    @Override
    public List<ValidationError> validate() {
        Pattern hasLowercase = Pattern.compile("[a-z]");
        Pattern hasUppercase = Pattern.compile("[A-Z]");
        Pattern hasNumber = Pattern.compile("\\d");
        List<ValidationError> errors = new ArrayList<>();

        if (StringUtils.isNullOrWhiteSpace(firstName)) {
            errors.add(new ValidationError("firstName", "First name is a required field"));
        }
        if (StringUtils.isNullOrWhiteSpace(email)) {
            errors.add(new ValidationError("email", "Email is a required field"));
        }
        if (!newPassword.equals(newPasswordVerify)) {
            errors.add(new ValidationError("newPassword", "Passwords do not match"));
        }

        if (newPassword.length() > 0) {
            if (newPassword.length() < 8) {
                errors.add(new ValidationError("newPassword", "Password must be at least 8 characters long"));
            }
            if (!hasUppercase.matcher(newPassword).find()) {
                errors.add(new ValidationError("newPassword", "Password must have at least one uppercase character"));
            }
            if (!hasNumber.matcher(newPassword).find()) {
                errors.add(new ValidationError("newPassword", "Password must have at least one number"));
            }
            if (!hasLowercase.matcher(newPassword).find()) {
                errors.add(new ValidationError("newPassword", "Password must have at least one lowercase character"));
            }
        }

        return errors.isEmpty() ? null : errors;
    }

    // Getters and setters

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }
}
