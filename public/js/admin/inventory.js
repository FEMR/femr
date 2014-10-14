$(document).ready(function () {


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
    ingredientsList: $('#ingredientsForMedication'),
    addIngredient: function () {

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