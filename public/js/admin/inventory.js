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

    // AJAX STUFF
    var manageUsers = {
        elements: {
            userToggleButtons: $('.toggleBtn')
        },
        bindUserToggleButtons: function () {
            //manageUsers.elements.userToggleButtons.click(function () {
            //    manageUsers.toggleMedication(this);
            //});
        },
        toggleUser: function (object) {
            //user ID
            var id = $(object).attr('data-id');
            var value = $(object).attr('value');
            var tripId = $(object).attr('tripid');
            $.ajax({
                url: '/admin/inventory/edit/' + id + "/" + value + "/" + tripId,
                type: 'POST',
                data: {},
                dataType: 'text',
                success: function () {
                    alert("Yay");
                },
                error: function () {
                    //don't change button - implies an error
                    alert("Fuck");
                }
            });
        },
        toggleMedication: function (btn){
            var id = $(btn).attr('data-id');
            var tripId = $(btn).attr('tripid');
            $.ajax({
                url: '/admin/inventory/delete/' + id + "/" + tripId,
                type: 'POST',
                data: {},
                dataType: 'text',
                success: function (isDeleted) {

                        if($(btn).hasClass("btn-danger")){
                            $(btn).html("Undo");
                            $(btn).removeClass("btn-danger");
                            $(btn).addClass("btn-success");
                        }
                        else{
                            $(btn).html("Remove");
                            $(btn).removeClass("btn-success");
                            $(btn).addClass("btn-danger");
                        }

                },
                error: function () {
                    //don't change button - implies an error
                    alert("Fuck");
                }
            });
        }
    };
    $(".editInput").keypress(function(e) {
        if (e.which == 13) {
            $(this)[0].style.display = "none";
            $(this).prev()[0].innerText = $(this)[0].value;
            var value = $(this)[0].value;
            $(this).prev().attr({
                "value" : value
            })
            $(this).prev().show();
            manageUsers.toggleUser($(this).prev());
            return false;
        }
    });
    $('.toggleBtn').click(function () {
        manageUsers.toggleMedication(this);
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