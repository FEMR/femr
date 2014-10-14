$(document).ready(function () {
    $('#addNewIngredient').click(function(){
        medicationInventoryFeature.addIngredient();
    });

});

var inventoryFields = {
    newMedications: {
        quantity: $('#quantityInput'),
        name: $('#nameInput'),
        strength: $('#strengthInput'),
        ingredient: $('#ingredientInput'),
        unit: $('#unitInput'),
        form: $('#formInput')
    }
};

var medicationInventoryFeature = {
    addIngredient: function () {
        $('#newMedication').after('<div class="newIngredient">' +
            '<input type="text" class="fInput" placeholder="X" />' +
            '<select class="fOption">' +
            '<option value="" disabled selected>Unit</option>' +
            '<option>mg</option>' +
            '</select>' +
            '<input type="text" class="fInput" placeholder="ingredient"/>' +
            '</div>');
    },
    submitMedication: function () {

    }

};


/*
 Defines a medication, matches MedicationItem
 */
function Medication(name, quantity, form) {
    this.name = name;
    this.quantity_total = quantity;
    this.form = form;

}
var ingredient = {
    strength: null,
    name: null,
    unit: null
};


//Medication.prototype = {
//    //add and remove both interact directly with the database
//    add: function () {
//
//    },
//    remove: function () {
//
//    }
//}