$(document).ready(function () {
    var urlPieces = window.location.href.split('/');

    if ($.inArray("edit", urlPieces) !== -1) {
        editUsers.bindRoleDropDownClick();
        editUsers.bindRoleBadge();
        editUsers.bindSubmitButton();
    } else if ($.inArray("create", urlPieces) !== -1) {
        createUsers.bindSubmitButton();
    } else {
        manageUsers.bindUserToggleButtons();
    }


});

var manageUsers = {
    elements: {
        userToggleButtons: $('.toggleBtn')
    },
    bindUserToggleButtons: function () {
        manageUsers.elements.userToggleButtons.click(function () {
            manageUsers.toggleUser(this);
        });
    },
    toggleUser: function (btn) {
        //user ID
        var id = $(btn).attr('data-user_id');

        $.ajax({
            url: '/admin/users/toggle/' + id,
            type: 'POST',
            data: {},
            dataType: 'text',
            success: function (isDeleted) {
                //on success, toggle button to reflect current state of the user
                if (isDeleted === "false") {
                    $(btn).html("Deactivate");
                    $(btn).removeClass("btn-success");
                    $(btn).addClass("btn-danger");

                } else {
                    $(btn).html("Activate");
                    $(btn).removeClass("btn-danger");
                    $(btn).addClass("btn-success");
                }
            },
            error: function (response) {
                //don't change button - implies an error
            }
        });
    }
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
        //validate password
        if ($.trim(document.forms["createForm"]["password"].value) === "") {
            createUsers.elements.password.next(".errors").text("please assign this user a password");
            pass = false;
        } else {
            createUsers.elements.password.next(".errors").text("");
        }
        //validate roles
        var isARoleChecked = false;
        $.each(document.forms["createForm"].elements["roles[]"], function () {
            console.log($(this).is(':checked'));
            if ($(this).is(':checked')) {
                isARoleChecked = true;
            }
        });
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
    }
};

var editUsers = {
    elements: {
        xButtonOnRoleListItem: $('.roleBadge'),
        passwordTextBox: $('#newPassword')
    },
    /**
     * Validates the roles for edit users page
     */
    validateRolesAndPassword: function () {
        var pass = true;
        //validate passwords
        var newPassword = $.trim(document.forms["createForm"]["newPassword"].value);
        var newPasswordVerify = $.trim(document.forms["createForm"]["newPasswordVerify"].value);
        if (newPassword !== "") {
            if (newPassword != newPasswordVerify){
                editUsers.elements.passwordTextBox.next(".errors").text("passwords do not match");
                pass = false;
            }else{
                editUsers.elements.passwordTextBox.next(".errors").text("");
            }
        } else {
            editUsers.elements.passwordTextBox.next(".errors").text("");
        }
        //validate roles
        if (typeof document.forms["createForm"].elements["roles[]"] === 'undefined') {
            pass = false;
        }
        if (pass === false) {
            createAndEditUsers.elements.roles.find(".errors").text("select at least one role");
        } else {
            createAndEditUsers.elements.roles.find(".errors").text("");
        }
        return pass;
    },
    bindRoleDropDownClick: function () {
        $('.roleListItem').click(function () {
            var role = $(this).text();
            if (!editUsers.doesRoleExistInList(role)) {
                $('#currentRoles').append("<li class=list-group-item value=" + role + "><span class='badge roleBadge'>X</span>" + role + "<input type=text class=hidden name=roles[] value=" + role + "></li>");
                editUsers.elements.xButtonOnRoleListItem = $(editUsers.elements.xButtonOnRoleListItem.selector);
                editUsers.bindRoleBadge();
            }
        });
    },
    bindRoleBadge: function () {
        editUsers.elements.xButtonOnRoleListItem.unbind();
        editUsers.elements.xButtonOnRoleListItem.click(function () {
            editUsers.elements.xButtonOnRoleListItem = $(editUsers.elements.xButtonOnRoleListItem.selector);
            $(this).parent().remove();
        });
    },
    doesRoleExistInList: function (role) {
        var exist = false;
        var $roles = $('#currentRoles').find('> li');
        $($roles).each(function () {
            if ($(this).attr('value').toLowerCase() === role.toLowerCase()) {
                exist = true;
            }
        });
        return exist;
    },
    bindSubmitButton: function () {
        $('#editUserSubmitBtn').click(function () {
            var roleValidation = editUsers.validateRolesAndPassword();
            var elementValidation = createAndEditUsers.validateElements();
            return roleValidation && elementValidation;
        });
    }
};