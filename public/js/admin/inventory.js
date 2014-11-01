$(document).ready(function () {
    $('#addNewIngredient').click(function(){
        medicationInventoryFeature.addIngredient();
    });

});

var inventoryFields = {
    newMedications: {
        quantity: $('#quantityFieldMedication').find('input'),
        form: $('#formFieldMedication').find('select'),
        name: $('#nameFieldMedication').find('input'),
        strength: $('.strengthFieldMedication input'),
        ingredient: $('.ingredientFieldMedication input'),
        unit: $('.unitFieldMedication select')
    }
};

var medicationInventoryFeature = {
    newIngredientRow: $('.newIngredientRow'),
    addIngredient: function () {
        if (!medicationInventoryFeature.newIngredientRow.hasClass('hidden')){
            //clone it
            medicationInventoryFeature.newIngredientRow.clone().appendTo('#newMedicationWrap');
        }else{
            //show it
            medicationInventoryFeature.newIngredientRow.removeClass('hidden');
        }

    },
    submitMedication: function () {

    }
};