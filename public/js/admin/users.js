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
        roles: $("#roleWrap")
    },
    validateElements: function () {
        var pass = true;
        if ($.trim(document.forms["createForm"]["email"].value) === "") {
            createAndEditUsers.elements.email.next(".errors").text("email is a required field");
            pass = false;
        } else {
            createAndEditUsers.elements.email.next(".errors").text("");
        }

        if ($.trim(document.forms["createForm"]["firstName"].value) === "") {
            createAndEditUsers.elements.firstName.next(".errors").text("first name is a required field");
            pass = false;
        } else {
            createAndEditUsers.elements.firstName.next(".errors").text("");
        }
        //check roles

        return pass;
    }
};

var createUsers = {
    elements: {
        password: $("#password")
    },
    validateRolesAndPassword: function () {
        var pass = true;
        // Adding password constraint!
        var passwordErrors = "";
        var password = $.trim(document.forms["createForm"]["password"].value);
        createUsers.elements.password.next(".errors").text(passwordVerify);

        if (password  === "") {
            passwordErrors = "please assign this user a password";
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

        if(pass === false)
        {
            if (passwordErrors !== "")
                createUsers.elements.password.next (".errors").text(passwordErrors);
            else
                createUsers.elements.password.next(".errors").text("password must have at least one uppercase, one lowercase, one digit, one symbol, and be at least 8 characters long.");
        }
        else{
            createUsers.elements.password.next(".errors").text("");
        }

        //validate roles
        var isARoleChecked = false;
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
            createAndEditUsers.elements.roles.find(".errors").text("select at least one role");
        } else {
            createAndEditUsers.elements.roles.find(".errors").text("");
        }
        return pass;
    },
    bindSubmitButton: function () {
        $('#addUserSubmitBtn').click(function () {
            var roleAndPasswordValidation = createUsers.validateRolesAndPassword();
            var elementValidation = createAndEditUsers.validateElements();
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




