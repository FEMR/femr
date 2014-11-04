$(document).ready(function () {
    medicationInventoryFeature.bindAddNewIngredientButton();
    medicationInventoryFeature.bindSubmitMedicationButton();
});

var inventoryFields = {
    newMedication: {
        quantity: $('#quantityFieldMedication').find('input'),
        form: $('#formFieldMedication').find('select'),
        name: $('#nameFieldMedication').find('input'),
        strength: $('.strengthFieldMedication input:enabled'),
        ingredient: $('.ingredientFieldMedication input:enabled'),
        unit: $('.unitFieldMedication select:enabled'),
        refreshFields: function(){
            inventoryFields.newMedication.strength = $(inventoryFields.newMedication.strength.selector);
            inventoryFields.newMedication.ingredient = $(inventoryFields.newMedication.ingredient.selector);
            inventoryFields.newMedication.unit = $(inventoryFields.newMedication.unit.selector);
        }
    }
};

var medicationInventoryFeature = {
    newIngredientRow: $('.newIngredientRow'),
    addIngredient: function () {
        if (!medicationInventoryFeature.newIngredientRow.hasClass('hidden')){
            //clone the new ingredient div
            medicationInventoryFeature.newIngredientRow.clone().appendTo('#newMedicationWrap');
            //since it cloned with values, clear the values
            var newDiv = $('.newIngredientRow').last();
            $(newDiv).find('.ingredientFieldMedication input').val("");
            $(newDiv).find('.strengthFieldMedication input').val("");
        }else{
            //show the new ingredient div
            medicationInventoryFeature.newIngredientRow.find('.ingredientFieldMedication input').prop("disabled", false);
            medicationInventoryFeature.newIngredientRow.find('.strengthFieldMedication input').prop("disabled", false);
            medicationInventoryFeature.newIngredientRow.find('.unitFieldMedication select').prop("disabled", false);
            medicationInventoryFeature.newIngredientRow.removeClass('hidden');
        }

    },
    validateMedication: function () {
        var pass = true;
        if (inventoryFields.newMedication.quantity.val() === "")
            pass = false;
        if (inventoryFields.newMedication.form.val() === null)
            pass= false;
        if (inventoryFields.newMedication.name.val() === "")
            pass = false;
        inventoryFields.newMedication.refreshFields();
        console.log(inventoryFields.newMedication.strength.length);
        return pass;
    },
    bindSubmitMedicationButton: function(){
        $('#submitMedicationButton').click(function(){
            return medicationInventoryFeature.validateMedication();
        });
    },
    bindAddNewIngredientButton: function(){
        $('#addNewIngredient').click(function(){
            medicationInventoryFeature.addIngredient();
        });
    }
};