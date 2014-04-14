$(document).ready(function () {
    bindToggleBtn();
    $('#editDialog').dialog({
        dialogClass: 'editUserDialog',
        autoOpen: false,
        draggable: true,
        position: 'center',
        modal: true,
        height: 450,
        width: 600

    });
    bindEditDialog();
});


//on cancel click


/*  on edit click events */
function bindEditDialog() {
    $('.editBtn').click(function () {
        var id = $(this).attr('data-user_id');
        $.ajax({
            url: '/admin/users/edit/' + id,
            type: 'GET',
            data: {id: id},
            success: function (partialView) {
                $('#editPartial').html(partialView);
                $('#editDialog').dialog("open");
                $('#cancelBtn').click(function () {
                    $('#editDialog').dialog("close");
                });
                bindRoleDropDownClick();
                bindRoleBadge();
            },
            error: function (response) {
                alert("fatal error dear lord what have you done");
            }
        })

    });
}
function bindRoleDropDownClick() {
    $('.roleListItem').click(function () {
        var role = $(this).text();
        if (!doesRoleAlreadyExist(role)) {
            $('#currentRoles').append("<li class=list-group-item value=" + role + "><span class='badge roleBadge'>X</span>" + role + "</li><input type=text class=hidden name=roles[] value=" + role + ">");
            bindRoleBadge();
        }

    });
}
function bindRoleBadge() {
    $('.roleBadge').unbind();
    $('.roleBadge').click(function () {
        $(this).parent().remove();
    });
}
//checks if a role exists in the currentRole list
function doesRoleAlreadyExist(role) {
    var exist = false;
    var $roles = $('#currentRoles > li');
    $($roles).each(function () {
        if ($(this).attr('value').toLowerCase() === role.toLowerCase()) {
            exist = true;
        }
    });
    return exist;
}


//Toggle active/deactive user
function bindToggleBtn() {
    $('.toggleBtn').click(function () {
        toggleUser(this);
    });
}
function toggleUser(btn) {
    //user ID
    var id = $(btn).attr('data-user_id');
    //are we deleting the user?

    /*
     var isDeleted = false;
     if ($(btn).html() === "Deactivate")
     isDeleted = true;*/


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
