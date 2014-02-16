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


//on edit click
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
            },
            error: function (response) {
                alert("fatal error dear lord what have you done");
            }
        })

    });
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
    var isDeleted = false;
    if ($(btn).html() === "Deactivate")
        isDeleted = true;


    $.ajax({
        url: '/admin/users/create',
        type: 'POST',
        data: {userId: id, isDeleted: isDeleted},
        dataType: 'text',
        success: function (buttonText) {
            //on success, toggle button to reflect current state of the user
            $(btn).html(buttonText);
            if (buttonText === "Activate") {
                $(btn).removeClass("btn-danger");
                $(btn).addClass("btn-success");
            } else {
                $(btn).removeClass("btn-success");
                $(btn).addClass("btn-danger");
            }
        },
        error: function (response) {
            //don't change button - implies an error
        }
    });

}
