// AJAX STUFF
var manageInventoryFeature = {

    /**
     * Set click event for when a user clicks the Remove button in the
     * 'Remove' column of the inventory table.
     */
    bindRemoveButtonClick: function(){

        $(".removeBtn").unbind("click");
        $('.removeBtn').click(function () {
            manageInventoryFeature.toggleMedication(this);

        });
    },
    /**
     * Set click event for when a user clicks the edit current quantity
     * button. Event opens a textbox to alter the quantity.
     */
    bindEditCurrentQuantityButtonClick: function(){

        $(".editCurrentQuantityBtn").unbind("click");
        $(".editCurrentQuantityBtn").click(function(){
            $(this).siblings(".editCurrentQuantity").first().hide();
            $(this).siblings(".editCurrentInput").first().show();
            $(this).hide();
        });
    },
    /**
     * Set click event for when a user clicks the edit initial quantity
     * button. Event opens a textbox to alter the quantity.
     */

    bindEditTotalQuantityButtonClick: function(){
        $(".editTotalQuantityBtn").unbind("click");
        $(".editTotalQuantityBtn").click(function(){
            $(this).siblings(".editTotalQuantity").first().hide();
            $(this).siblings(".editTotalInput").first().show();
            $(this).hide();
        });
    },
    /**
     * Set click event for when a user presses enter after they are done editing
     * the current quantity.
     */
    bindEditCurrentQuantityEnterKey: function(){

        $(".editCurrentInput").unbind("keypress");
        $(".editCurrentInput").keypress(function(e) {
            if (e.which == 13) {

                manageInventoryFeature.editCurrentQuantity( this );
                e.preventDefault();
            }
        });
    },
    /**
     * Set click event for when a user presses enter after they are done editing
     * the initial quantity.
     */
    bindEditTotalQuantityEnterKey: function(){

        $(".editTotalInput").unbind("keypress");
        $(".editTotalInput").keypress(function(e) {
            if (e.which == 13) {
                manageInventoryFeature.editTotalQuantity( this );
                e.preventDefault();
            }
        });
    },
    /**
     * AJAX call that gets triggered after a user clicks enter for a new
     * current quantity value.
     */
    editCurrentQuantity: function ( inputField ) {

        var value = $(inputField).val();
        var id = $(inputField).siblings('input.medication_id').first().val();
        var tripId = $(inputField).siblings('input.trip_id').first().val();


        $.ajax({
            url: '/admin/inventory/editCurrent/' + id + "/" + tripId,
            type: 'POST',
            data: {
                quantity: value
            },
            dataType: 'text',
            success: function () {

                // Hide edit field
                $(inputField).hide();

                // Show edit button
                $(inputField).siblings(".editCurrentQuantityBtn").show();

                // Update text quantity value and show
                $(inputField).siblings(".editCurrentQuantity").text(value);
                $(inputField).siblings(".editCurrentQuantity").show();
            },
            error: function () {

                //don't change button - implies an error
            }
        });
    },
    /**
     * AJAX call that gets triggered after a user clicks enter for a new
     * initial quantity value.
     */
    editTotalQuantity: function ( inputField ) {

        var value = $(inputField).val();
        var id = $(inputField).siblings('input.medication_id').first().val();
        var tripId = $(inputField).siblings('input.trip_id').first().val();


        $.ajax({
            url: '/admin/inventory/editTotal/' + id + "/" + tripId,
            type: 'POST',
            data: {
                quantity: value
            },
            dataType: 'text',
            success: function () {

                // Hide edit field
                $(inputField).hide();

                // Show edit button
                $(inputField).siblings(".editTotalQuantityBtn").show();

                // Update text quantity value and show
                $(inputField).siblings(".editTotalQuantity").text(value);
                $(inputField).siblings(".editTotalQuantity").show();
            },
            error: function () {

                //don't change button - implies an error
            }
        });
    },
    /**
     * AJAX call that gets triggered after a user clicks remove for any medication
     * in the inventory.
     */
    toggleMedication: function ( btn ) {
        var editCell = $(btn).parents("td").siblings("td.currentQuantity").first();

        var id = $(editCell).find('input.medication_id').val();
        var tripId = $(editCell).find('input.trip_id').val();

        if ($(btn).hasClass("btn-danger")) {
            $.ajax({
                url: '/admin/inventory/delete/' + id + "/" + tripId,
                type: 'POST',
                data: {},
                dataType: 'text',
                success: function () {
                    $(btn).html("Undo");
                    $(btn).removeClass("btn-danger");
                    $(btn).addClass("btn-success");
                },
                error: function () {
                    //don't change button - implies an error
                }
            });
        }
        else {
            $.ajax({
                url: '/admin/inventory/readd/' + id + "/" + tripId,
                type: 'POST',
                data: {},
                dataType: 'text',
                success: function () {
                    $(btn).html("Remove");
                    $(btn).removeClass("btn-success");
                    $(btn).addClass("btn-danger");
                },
                error: function () {
                    //don't change button - implies an error
                }
            });
        }
    }
};

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


    $("#inventoryTable").DataTable({
        columnDefs: [ { orderable: false, targets: [4] }]
    });

    $("#inventoryTable").on("draw.dt", function() {

        manageInventoryFeature.bindEditCurrentQuantityButtonClick();
        manageInventoryFeature.bindEditTotalQuantityButtonClick();
        manageInventoryFeature.bindEditCurrentQuantityEnterKey();
        manageInventoryFeature.bindEditTotalQuantityEnterKey();
        manageInventoryFeature.bindRemoveButtonClick();
    });
    manageInventoryFeature.bindEditCurrentQuantityButtonClick();
    manageInventoryFeature.bindEditTotalQuantityButtonClick();
    manageInventoryFeature.bindEditCurrentQuantityEnterKey();
    manageInventoryFeature.bindEditTotalQuantityEnterKey();
    manageInventoryFeature.bindRemoveButtonClick();


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

$('#submitMedicationButton').click(function () {
    // resetting any border that may have been applied previously
    $('.medicationIngredient').css('border', '');

    for(var x = 0; x < $('.medicationIngredient').length; x++){
        if($('.medicationsStrength')[x].value.trim().length > 0 && $('.medicationIngredient')[x].value.trim().length == 0){
            $('.medicationIngredient').eq(x).css('border', '1px solid red');
            return false;
        }
    }
    return true;
});