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