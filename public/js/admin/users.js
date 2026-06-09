var adminUserI18n = {
    strings: {},
    t: function (key, fallback) {
        return this.strings[key] || fallback || key;
    },
    load: function (callback) {
        var languageUrl = window.femrLanguageUrl || '/assets/json/languages.json';
        var serverLanguage = window.femrLanguageCode;
        var storedLanguage = localStorage.getItem('languageCode');

        fetch(languageUrl)
            .then(function (response) { return response.json(); })
            .then(function (languageData) {
                var language = languageData[serverLanguage] ? serverLanguage : (languageData[storedLanguage] ? storedLanguage : 'en');
                adminUserI18n.strings = Object.assign({}, languageData.en || {}, languageData[language] || {});
                localStorage.setItem('languageCode', language);
                adminUserI18n.applyDomTranslations();
                callback();
            })
            .catch(function () {
                adminUserI18n.applyDomTranslations();
                callback();
            });
    },
    roleKey: function (roleName) {
        return 'admin_users_role_' + String(roleName || '').toLowerCase().replace(/[^a-z0-9]+/g, '_').replace(/^_|_$/g, '');
    },
    applyDomTranslations: function () {
        $('[data-i18n]').each(function () {
            var key = $(this).attr('data-i18n');
            var value = adminUserI18n.t(key, $(this).text());
            $(this).text(value);
        });

        $('[data-i18n-placeholder]').each(function () {
            var key = $(this).attr('data-i18n-placeholder');
            $(this).attr('placeholder', adminUserI18n.t(key, $(this).attr('placeholder')));
        });

        $('[data-i18n-aria]').each(function () {
            var key = $(this).attr('data-i18n-aria');
            $(this).attr('aria-label', adminUserI18n.t(key, $(this).attr('aria-label')));
        });

        $('[data-role-name]').each(function () {
            var roleName = $(this).attr('data-role-name');
            $(this).text(adminUserI18n.t(adminUserI18n.roleKey(roleName), roleName));
        });

        adminUserI18n.translatePasswordAges();
    },
    translatePasswordAges: function () {
        $('#userTable tbody td').each(function () {
            var text = $.trim($(this).text());
            var match = /^(\d+)\s+days$/i.exec(text);
            if (match) {
                $(this).text(match[1] + ' ' + adminUserI18n.t('admin_users_days', 'days'));
            }
        });
    },
    dataTableLanguage: function () {
        return {
            lengthMenu: adminUserI18n.t('datatable_show', 'Show') + ' _MENU_ ' + adminUserI18n.t('datatable_entries', 'entries'),
            search: adminUserI18n.t('datatable_search', 'Search') + ':',
            info: adminUserI18n.t('datatable_info', 'Showing _START_ to _END_ of _TOTAL_ entries'),
            infoEmpty: adminUserI18n.t('datatable_info_empty', 'Showing 0 to 0 of 0 entries'),
            infoFiltered: adminUserI18n.t('datatable_info_filtered', '(filtered from _MAX_ total entries)'),
            emptyTable: adminUserI18n.t('datatable_empty_table', 'No data available in table'),
            zeroRecords: adminUserI18n.t('datatable_zero_records', 'No matching records found'),
            paginate: {
                previous: adminUserI18n.t('datatable_previous', 'Previous'),
                next: adminUserI18n.t('datatable_next', 'Next')
            }
        };
    }
};

$(document).ready(function () {
    adminUserI18n.load(function () {
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
                ],
                language: adminUserI18n.dataTableLanguage(),
                drawCallback: function () {
                    adminUserI18n.applyDomTranslations();
                }
            });
        }
    });
});

var manageUsers = {
    bindUserToggleButtons: function () {
        $(document).on('click', '.toggleBtn', function (event) {
            event.preventDefault();
            manageUsers.toggleUser(this);
        });
    },
    setButtonState: function (btn, variant, labelKey, fallbackLabel, icon) {
        var $btn = $(btn);
        $btn.removeClass('admin-button--success admin-button--danger');
        if (variant) {
            $btn.addClass('admin-button--' + variant);
        }
        $btn.html('<span class="material-symbols-outlined" aria-hidden="true">' + icon + '</span><span data-i18n="' + labelKey + '">' + adminUserI18n.t(labelKey, fallbackLabel) + '</span>');
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
                    manageUsers.setButtonState(btn, 'danger', 'admin_users_deactivate', 'Deactivate', 'block');

                } else {
                    manageUsers.setButtonState(btn, 'success', 'admin_users_activate', 'Activate', 'check_circle');
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
            createAndEditUsers.elements.email.next(".errors").text(adminUserI18n.t('admin_users_error_email_required', 'email is a required field'));
            pass = false;
        } else {
            createAndEditUsers.elements.email.next(".errors").text("");
        }

        if ($.trim(document.forms["createForm"]["firstName"].value) === "") {
            createAndEditUsers.elements.firstName.next(".errors").text(adminUserI18n.t('admin_users_error_first_name_required', 'first name is a required field'));
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
        password: $("#password"),
        passwordVerify: $("#passwordVerify")
    },
    validateRolesAndPassword: function () {
        var pass = true;
        // Adding password constraint!
        var passwordError = "";
        var password = $.trim(document.forms["createForm"]["password"].value);
        var passwordVerifyField = document.forms["createForm"]["passwordVerify"];
        var passwordVerify = passwordVerifyField ? $.trim(passwordVerifyField.value) : password;
        createUsers.elements.password.next(".errors").text("");
        createUsers.elements.passwordVerify.next(".errors").text("");

        if (password  === "") {
            passwordError = adminUserI18n.t('admin_users_error_password_required', 'please assign this user a password');
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
            var passwordMatchError = adminUserI18n.t('sessions_edit_password_error_match', 'passwords do not match');
            pass = false;
            createUsers.elements.passwordVerify.next(".errors").text(passwordMatchError);
            if (passwordError === "") {
                passwordError = passwordMatchError;
            }
        }

        if(pass === false)
        {
            if (passwordError !== "")
                createUsers.elements.password.next(".errors").text(passwordError);
            else
                createUsers.elements.password.next(".errors").text(adminUserI18n.t('admin_users_error_password_complexity', 'password must have at least one uppercase, one lowercase, one digit, one symbol, and be at least 8 characters long.'));
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
            createAndEditUsers.elements.roles.find(".errors").text(adminUserI18n.t('admin_users_error_role_required', 'select at least one role'));
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
        var removeLabel = adminUserI18n.t('admin_users_remove', 'Remove');
        var removeAria = adminUserI18n.t('admin_users_remove_aria', 'Remove {name}').replace('{name}', title);

        var rowHtml = '<tr data-trip-id="' + selectedTripId + '">' +
            '<td>' + team + '</td>' +
            '<td>' + country + '</td>' +
            '<td>' + date + '</td>' +
            '<td>' +
            '<input type="hidden" name="tripIds[]" value="' + selectedTripId + '" />' +
            '<button type="button" class="admin-button admin-button--danger js-remove-create-trip" aria-label="' + removeAria + '" data-i18n="admin_users_remove">' + removeLabel + '</button>' +
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
                passwordErrors.push(adminUserI18n.t('admin_users_error_password_min', 'be at least 8 characters long'));
            }
            if (!/[A-Z]/.test(newPassword)) {
                passwordErrors.push(adminUserI18n.t('admin_users_error_password_uppercase', 'include at least one uppercase character'));
            }
            if (!/[a-z]/.test(newPassword)) {
                passwordErrors.push(adminUserI18n.t('admin_users_error_password_lowercase', 'include at least one lowercase character'));
            }
            if (!/\d/.test(newPassword)) {
                passwordErrors.push(adminUserI18n.t('admin_users_error_password_number', 'include at least one number'));
            }
        }

        if (newPassword !== "") {
            if (newPassword !== newPasswordVerify) {
                passwordErrors.push(adminUserI18n.t('admin_users_error_password_verify_match', 'match the verify password field'));
            }

            if (passwordErrors.length > 0) {
                var errorMessage = adminUserI18n.t('admin_users_error_invalid_password_reset', 'Invalid password reset. Password must {requirements}.').replace('{requirements}', passwordErrors.join(", "));
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
            createAndEditUsers.elements.roles.find(".errors").text(adminUserI18n.t('admin_users_error_role_required', 'select at least one role'));
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




