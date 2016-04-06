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

    $(".editQuantityBtn").click(function(){

        $(this).siblings(".editQuantity").first().hide();
        $(this).siblings(".editInput").first().show();
        $(this).hide();
    });


    // AJAX STUFF
    var manageInventory = {

        editCurrentQuantity: function ( inputField ) {

            var value = $(inputField).val();
            var id = $(inputField).siblings('input.medication_id').first().val();
            var tripId = $(inputField).siblings('input.trip_id').first().val();


            $.ajax({
                url: '/admin/inventory/edit/' + id + "/" + tripId,
                type: 'POST',
                data: {
                    quantity: value
                },
                dataType: 'text',
                success: function () {

                    // Hide edit field
                    $(inputField).hide();

                    // Show edit button
                    $(inputField).siblings(".editQuantityBtn").show();

                    // Update text quantity value and show
                    $(inputField).siblings(".editQuantity").text(value);
                    $(inputField).siblings(".editQuantity").show();
                },
                error: function () {

                    //don't change button - implies an error
                }
            });
        },
        toggleMedication: function ( btn ){

            var editCell = $(btn).parents("td").siblings("td.currentQuantity").first();

            var id = $(editCell).find('input.medication_id').val();
            var tripId = $(editCell).find('input.trip_id').val();

            $.ajax({

                url: '/admin/inventory/delete/' + id + "/" + tripId,
                type: 'POST',
                data: {},
                dataType: 'text',
                success: function () {

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
                }
            });
        }
    };
    $(".editInput").keypress(function(e) {

        if (e.which == 13) {

            manageInventory.editCurrentQuantity( this );
            e.preventDefault();
        }
    });
    $('.removeBtn').click(function () {
        manageInventory.toggleMedication(this);
    });

    if ($('#addConceptMedicineSelect2').length > 0){

        $('#addConceptMedicineSelect2').select2({
            placeholder: "Search medicine:"
        });
    }
});

function bindRemoveAction(element){

    $(element).click(function(){

        // don't let the first row get removed
        if( $(this).parents(".ingredientFields").index() > 0 ) {
            $(this).parents(".ingredientFields").remove();
        }
    });
}
