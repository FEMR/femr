$(document).ready(function () {
    bindDeactivateBtn();
});


function bindDeactivateBtn() {
    $('.deactivateBtn').click(function () {
        deactivateUser($(this).attr('data-user_id'));
    })
}
function deactivateUser(id) {
    $.ajax({
        url: '/admin/users/create',
        type: 'POST',
        data: {userId: id},
        dataType: 'text',

        success: function (data) {
            console.log(data);
        },
        error: function (response) {
            console.log(response);
        }
    });

}
