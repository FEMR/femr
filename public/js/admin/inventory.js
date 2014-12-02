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
        refreshFields: function () {
            inventoryFields.newMedication.strength = $(inventoryFields.newMedication.strength.selector);
            inventoryFields.newMedication.ingredient = $(inventoryFields.newMedication.ingredient.selector);
            inventoryFields.newMedication.unit = $(inventoryFields.newMedication.unit.selector);
            inventoryFields.newMedication.getNumberOfIngredients = $('.newIngredientRow').not('.hidden').length + 1;
        }
    }
};

var medicationInventoryFeature = {
    newIngredientRow: $('.newIngredientRow'),
    addIngredient: function () {
        if (!medicationInventoryFeature.newIngredientRow.hasClass('hidden')) {
            //clone the new ingredient div
            medicationInventoryFeature.newIngredientRow.clone().appendTo('#newMedicationWrap');
            //since it cloned with values, clear the values
            var newDiv = $('.newIngredientRow').last();
            $(newDiv).find('.ingredientFieldMedication input').val("");
            $(newDiv).find('.strengthFieldMedication input').val("");
        } else {
            //show the new ingredient div
            medicationInventoryFeature.newIngredientRow.find('.ingredientFieldMedication input').prop("disabled", false);
            medicationInventoryFeature.newIngredientRow.find('.strengthFieldMedication input').prop("disabled", false);
            medicationInventoryFeature.newIngredientRow.find('.unitFieldMedication select').prop("disabled", false);
            medicationInventoryFeature.newIngredientRow.removeClass('hidden');
        }

    },
    validateMedication: function () {
        var pass = true;
        if ($.trim(inventoryFields.newMedication.quantity.val()) === "")
            pass = false;
        if ($.trim(inventoryFields.newMedication.form.val()) === null)
            pass = false;
        if ($.trim(inventoryFields.newMedication.name.val()) === "")
            pass = false;

        inventoryFields.newMedication.refreshFields();

        $(inventoryFields.newMedication.strength).each(function () {
            if ($.trim($(this).val()) === "")
                pass = false;
        });
        $(inventoryFields.newMedication.unit).each(function () {
            if ($.trim($(this).val()) === "")
                pass = false;
        });
        $(inventoryFields.newMedication.ingredient).each(function () {
            if ($.trim($(this).val()) === "")
                pass = false;
        });

        return true;
    },
    bindSubmitMedicationButton: function () {
        $('#submitMedicationButton').click(function () {
            return medicationInventoryFeature.validateMedication();
        });
    },
    bindAddNewIngredientButton: function () {
        $('#addNewIngredient').click(function () {
            medicationInventoryFeature.addIngredient();
        });
    }
};