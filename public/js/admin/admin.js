$(document).ready(function () {
    bindToggleBtn();
});


function bindToggleBtn() {
    $('.toggleBtn').click(function () {
        toggleUser(this);
    });
}
function toggleUser(btn) {
    var id = $(btn).attr('data-user_id');
    $.ajax({
        url: '/admin/users/create',
        type: 'POST',
        data: {userId: id},
        dataType: 'text',
        success: function (buttonText) {
            //on success, toggle button to reflect current state of the user
            $(btn).html(buttonText);
            if (buttonText ==="Activate"){
                $(btn).removeClass("btn-danger");
                $(btn).addClass("btn-success");
            }else{
                $(btn).removeClass("btn-success");
                $(btn).addClass("btn-danger");
            }
        },
        error: function (response) {
            //don't change button - implies an error
        }
    });

}
