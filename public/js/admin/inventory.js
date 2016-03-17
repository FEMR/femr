$(document).ready(function () {

    $("#addNewIngredient").click(function(){

        var newRow = $(this).siblings(".ingredientFields.first").clone().removeClass("first");

        $(newRow).find('input').val('');
        $(newRow).find('select').val("%");
        $(newRow).find('label').remove();

        $(this).before(newRow);
        bindRemoveAction($(newRow).find(".removeIngredient"));
    });

    $(".removeIngredient").each(function(idx, elem){

        bindRemoveAction(elem);
    });


    $('#inventoryTable').DataTable({
        columnDefs: [ { orderable: false, targets: [3] }]
    });

    $(".currentQuantity").dblclick(function(){
       $(this).find("span")[0].style.display="none";
       $(this).find("input")[0].style.display="block";
       $(this).find("input")[0].focus();
    });

    //$(".editInput").blur(function(){
    //    $(this)[0].style.display="none";
    //    $(this).prev()[0].innerText=$(this)[0].value;
    //    $(this).prev().show();
    //
    //});
    //$(".editInput").keypress(function(e) {
    //    if (e.which == 13) {
    //        $(this)[0].style.display = "none";
    //        $(this).prev()[0].innerText = $(this)[0].value;
    //        $(this).prev().show();
    //
    //        return false;
    //    }
    //});

    // AJAX STUFF
    var manageUsers = {
        elements: {
            userToggleButtons: $('.editQuantity')
        },
        bindUserToggleButtons: function () {
            //manageUsers.elements.userToggleButtons.click(function () {
            //    manageUsers.toggleUser(this);
            //});

        },
        toggleUser: function (btn) {
            //user ID
            var id = $(btn).attr('data-id');
            var value = $(btn).attr('value')
            $.ajax({
                url: '/admin/inventory/edit/' + id,
                type: 'POST',
                data: {},
                dataType: 'text',
                success: function () {
                    //on success, toggle button to reflect current state of the user
                    //if (isDeleted === "false") {
                    //    $(btn).html("Deactivate");
                    //    $(btn).removeClass("btn-success");
                    //    $(btn).addClass("btn-danger");
                    //
                    //} else {
                    //    $(btn).html("Activate");
                    //    $(btn).removeClass("btn-danger");
                    //    $(btn).addClass("btn-success");
                    //}
                    alert("Yay");
                },
                error: function () {
                    //don't change button - implies an error
                    alert("Fuck");
                }
            });
        }
    };
    manageUsers.elements.userToggleButtons.keypress(function(e) {
        if (e.which == 13) {
            $(this)[0].style.display = "none";
            $(this).prev()[0].innerText = $(this)[0].value;
            $(this).prev().show();
            manageUsers.toggleUser(this);
            return false;
        }
    });

});

function bindRemoveAction(element){

    $(element).click(function(){

        console.log( $(this).parents(".ingredientFields").index() );

        // don't let the first row get removed
        if( $(this).parents(".ingredientFields").index() > 0 ) {
            $(this).parents(".ingredientFields").remove();
        }
    });
}