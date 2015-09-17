$(document).ready(function () {

    $("#addNewIngredient").click(function(){

        var newRow = $(this).siblings(".ingredientFields.first").clone().removeClass("first");

        $(newRow).find('input').val('');
        $(newRow).find('select').val("%");
        $(newRow).find('label').remove();


        $(this).before(newRow);

    });


    $('#inventoryTablegit ').DataTable();

});