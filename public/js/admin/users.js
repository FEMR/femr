$(document).ready(function () {
    var urlPieces = window.location.href.split('/');

    if ($.inArray("edit", urlPieces) !== -1) {
        editUsers.bindSubmitButton();
    } else if ($.inArray("create", urlPieces) !== -1 || $.inArray("register", urlPieces) !== -1 ) {
        createUsers.bindSubmitButton();
        createUsers.bindTripHistoryControls();
    } else {
        manageUsers.bindUserToggleButtons();
    }

    const $userTable = $("#userTable");
    if ($userTable.length) {
        $userTable.DataTable({
            columnDefs: [
                { orderable: false, targets: [2, 3, 7] }
            ]
        });
    }
});

var manageUsers = {
    bindUserToggleButtons: function () {
        $(document).on('click', '.toggleBtn', function (event) {
            event.preventDefault();
            manageUsers.toggleUser(this);
        });
    },
    setButtonState: function (btn, variant, label, icon) {
        var $btn = $(btn);
        $btn.removeClass('admin-button--success admin-button--danger');
        if (variant) {
            $btn.addClass('admin-button--' + variant);
        }
        $btn.html('<span class="material-symbols-outlined" aria-hidden="true">' + icon + '</span><span>' + label + '</span>');
    },
    toggleUser: function (btn) {
        //user ID
        var id = $(btn).attr('data-id');

        $.ajax({
            url: '/admin/users/toggle/' + id,
            type: 'POST',
            data: {},
            dataType: 'text',
            success: function (isDeleted) {
                //on success, toggle button to reflect current state of the user
                if (isDeleted === "false") {
                    manageUsers.setButtonState(btn, 'danger', 'Deactivate', 'block');

                } else {
                    manageUsers.setButtonState(btn, 'success', 'Activate', 'check_circle');
                }
            },
            error: function () {
                //don't change button - implies an error
            }
        });
    },


};

//validates firstname, email, and password for both create and edit user pages
var createAndEditUsers = {
    elements: {
        firstName: $("#firstName"),
        email: $("#email"),
        roles: $("#roleWrap"),
        summary: $("#createUserFormSummary")
    },
    setFieldInvalid: function ($field, message) {
        $field.addClass("admin-form__field--invalid");
        $field.find(".errors").first().text(message);
    },
    clearFieldInvalid: function ($field) {
        $field.removeClass("admin-form__field--invalid");
        $field.find(".errors").first().text("");
    },
    setSummary: function (message) {
        if (createAndEditUsers.elements.summary.length) {
            createAndEditUsers.elements.summary.text(message).show();
        }
    },
    clearSummary: function () {
        if (createAndEditUsers.elements.summary.length) {
            createAndEditUsers.elements.summary.text("").hide();
        }
    },
    validateElements: function () {
        var pass = true;
        createAndEditUsers.clearSummary();
        createAndEditUsers.clearFieldInvalid(createAndEditUsers.elements.email.closest(".admin-form__field"));
        createAndEditUsers.clearFieldInvalid(createAndEditUsers.elements.firstName.closest(".admin-form__field"));

        if ($.trim(document.forms["createForm"]["email"].value) === "") {
            createAndEditUsers.setFieldInvalid(createAndEditUsers.elements.email.closest(".admin-form__field"), "Email is a required field.");
            pass = false;
        }

        if ($.trim(document.forms["createForm"]["firstName"].value) === "") {
            createAndEditUsers.setFieldInvalid(createAndEditUsers.elements.firstName.closest(".admin-form__field"), "First name is a required field.");
            pass = false;
        }
        //check roles

        if (pass === false) {
            createAndEditUsers.setSummary("Please fix the highlighted fields and try again.");
        }

        return pass;
    }
};

var createUsers = {
    elements: {
        password: $("#password"),
        passwordVerify: $("#passwordVerify")
    },
    setFieldInvalid: function ($field, message) {
        $field.addClass("admin-form__field--invalid");
        $field.find(".errors").first().text(message);
    },
    clearFieldInvalid: function ($field) {
        $field.removeClass("admin-form__field--invalid");
        $field.find(".errors").first().text("");
    },
    validateRolesAndPassword: function () {
        var pass = true;
        // Adding password constraint!
        var passwordErrors = "";
        var password = $.trim(document.forms["createForm"]["password"].value);
        var passwordVerify = $.trim(document.forms["createForm"]["passwordVerify"].value);
        var $passwordField = createUsers.elements.password.closest(".admin-form__field");
        var $passwordVerifyField = createUsers.elements.passwordVerify.closest(".admin-form__field");

        createUsers.clearFieldInvalid($passwordField);
        createUsers.clearFieldInvalid($passwordVerifyField);

        if (password  === "") {
            passwordErrors = "Please assign this user a password.";
            pass = false;
        }
        else{
            if(password.length < 8){
                pass = false;
            }
            var re = /(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[^a-zA-Z0-9])[a-zA-Z\d!@#$%\^&*()_\-+=|\\\"\':;?\/\{\}\[\]<>,\.~` \t]{8,}/;
            if(!re.test(password)) {
                pass = false;
            }
        }

        if (password !== "" && password !== passwordVerify) {
            pass = false;
            createUsers.setFieldInvalid($passwordVerifyField, "Passwords do not match.");
            createUsers.setFieldInvalid($passwordField, "Passwords do not match.");
        }

        if(pass === false)
        {
            if (passwordErrors !== "")
                createUsers.setFieldInvalid($passwordField, passwordErrors);
            else
                createUsers.setFieldInvalid($passwordField, "Password must have at least one uppercase, one lowercase, one digit, one symbol, and be at least 8 characters long.");
        }
        else{
            createUsers.clearFieldInvalid($passwordField);
        }

        //validate roles
        var isARoleChecked = false;
        var $roleField = createAndEditUsers.elements.roles.closest(".admin-form__field");
        createAndEditUsers.clearFieldInvalid($roleField);
        $.each(document.forms["createForm"].elements["roles[]"], function () {
            if ($(this).is(':checked')) {
                isARoleChecked = true;
            }
        });
        if (typeof document.forms["createForm"].elements["roles[]"] === 'undefined') {
            pass = false;
        }
        if (isARoleChecked === false) {
            pass = false;
            createAndEditUsers.setFieldInvalid($roleField, "Select at least one role.");
        } else {
            createAndEditUsers.clearFieldInvalid($roleField);
        }
        return pass;
    },
    bindSubmitButton: function () {
        $('#addUserSubmitBtn').click(function () {
            var roleAndPasswordValidation = createUsers.validateRolesAndPassword();
            var elementValidation = createAndEditUsers.validateElements();
            if (!roleAndPasswordValidation || !elementValidation) {
                createAndEditUsers.setSummary("Please fix the highlighted fields and try again.");
            }
            return roleAndPasswordValidation && elementValidation;
        });
    },
    escapeHtml: function (value) {
        return $('<div/>').text(value || '').html();
    },
    updateTripEmptyState: function () {
        var $emptyState = $('#createTripEmptyState');
        if (!$emptyState.length) {
            return;
        }

        if ($('#createTripTableBody tr').length === 0) {
            $emptyState.show();
        } else {
            $emptyState.hide();
        }
    },
    addTripRow: function () {
        var $tripSelect = $('#createTripSelect');
        var selectedTripId = $tripSelect.val();
        if (!selectedTripId) {
            return;
        }

        if ($('#createTripTableBody tr[data-trip-id="' + selectedTripId + '"]').length > 0) {
            return;
        }

        var $option = $tripSelect.find('option:selected');
        var team = createUsers.escapeHtml($option.data('team'));
        var country = createUsers.escapeHtml($option.data('country'));
        var date = createUsers.escapeHtml($option.data('date'));
        var title = createUsers.escapeHtml($option.data('title'));

        var rowHtml = '<tr data-trip-id="' + selectedTripId + '">' +
            '<td>' + team + '</td>' +
            '<td>' + country + '</td>' +
            '<td>' + date + '</td>' +
            '<td>' +
            '<input type="hidden" name="tripIds[]" value="' + selectedTripId + '" />' +
            '<button type="button" class="admin-button admin-button--danger js-remove-create-trip" aria-label="Remove ' + title + '">Remove</button>' +
            '</td>' +
            '</tr>';

        $('#createTripTableBody').append(rowHtml);
        createUsers.updateTripEmptyState();
    },
    bindTripHistoryControls: function () {
        $('#createAddTripBtn').off('click').on('click', function () {
            createUsers.addTripRow();
        });

        $(document).off('click.createTripRemove', '.js-remove-create-trip').on('click.createTripRemove', '.js-remove-create-trip', function () {
            $(this).closest('tr').remove();
            createUsers.updateTripEmptyState();
        });

        createUsers.updateTripEmptyState();
    }
};

var editUsers = {
    elements: {
        passwordTextBox: $('#newPassword')
    },
    /**
     * Validates the password for edit user page
     */
    validatePassword: function () {
        var pass = true;
        //validate passwords
        var newPassword = $.trim(document.forms["createForm"]["newPassword"].value);
        var newPasswordVerify = $.trim(document.forms["createForm"]["newPasswordVerify"].value);
        var passwordErrors = [];

        if (newPassword.length > 0) {
            if (newPassword.length < 8) {
                passwordErrors.push("be at least 8 characters long");
            }
            if (!/[A-Z]/.test(newPassword)) {
                passwordErrors.push("include at least one uppercase character");
            }
            if (!/[a-z]/.test(newPassword)) {
                passwordErrors.push("include at least one lowercase character");
            }
            if (!/\d/.test(newPassword)) {
                passwordErrors.push("include at least one number");
            }
        }

        if (newPassword !== "") {
            if (newPassword !== newPasswordVerify) {
                passwordErrors.push("match the verify password field");
            }

            if (passwordErrors.length > 0) {
                var errorMessage = "Invalid password reset. Password must " + passwordErrors.join(", ") + ".";
                editUsers.elements.passwordTextBox.next(".errors").text(errorMessage);
                window.alert(errorMessage);
                pass = false;
            } else {
                editUsers.elements.passwordTextBox.next(".errors").text("");
            }
        } else {
            editUsers.elements.passwordTextBox.next(".errors").text("");
        }
        return pass;
    },
    /**
     * Validates the roles for edit user page
     */
    validateRoles: function () {
        var roles = document.forms["createForm"].elements["roles[]"];
        var hasRole = false;

        if (roles) {
            if (roles.length === undefined) {
                hasRole = roles.checked;
            } else {
                for (var i = 0; i < roles.length; i++) {
                    if (roles[i].checked) {
                        hasRole = true;
                        break;
                    }
                }
            }
        }

        if (!hasRole) {
            createAndEditUsers.elements.roles.find(".errors").text("select at least one role");
        } else {
            createAndEditUsers.elements.roles.find(".errors").text("");
        }
        return hasRole;
    },
    bindSubmitButton: function () {
        $('#editUserSubmitBtn').click(function () {
            var roleValidation = editUsers.validateRoles();
            var passwordValidation = editUsers.validatePassword();
            var elementValidation = createAndEditUsers.validateElements();
            return roleValidation && passwordValidation && elementValidation;
        });
    }
};




