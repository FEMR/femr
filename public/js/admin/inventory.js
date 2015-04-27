$(document).ready(function () {
    /* Alaa Serhan */
    $("#currentMedicationsGrid").bs_grid({
        ajaxFetchDataURL: "/admin/inventory/get",
        row_primary_key: "id",
        rowSelectionMode: false,
        columns: [
            { field: "id", header: "ID" },
            { field: "name", header: "Medication" },
            { field: "quantity_current", header: "Current Quantity" },
            { field: "quantity_initial", header: "Total Quantity" },
            { field: "medicationForm.name", header: "Form" },
        ],
        sorting: [
            { sortingName: "Medication", field: "name", order: "Ascending" }
        ],
        filterOptions: {
            filters: [
                {
                    filterName: "Medication",
                    filterType: "text",
                    field: "name",
                    filterLabel: "Medication",
                    filter_interface: [
                        {
                            filter_element: "input",
                            filter_element_attributes: {"type": "text"}
                        }
                    ]
                },
                {
                    filterName: "Quantity",
                    filterType: "number",
                    numberType: "integer",
                    field: "quantity_current",
                    filterLabel: "Current Quantity"
                }

            ]
        },
        onDatagridError: function(e) {
        },
        onDisplay: function(event, data) {
            var table_id_prefix = $("#currentMedicationsGrid").bs_grid("getOption", "table_id_prefix");
            var table_id = table_id_prefix + "currentMedicationsGrid";

            // Add an empty cell to tables thead
            //$("#" + table_id + " thead tr").append("<th>Ingredients</th>");
            $("#" + table_id + " thead tr").append("<th></th>");

            // Add ingredients and a delete button to each row
            $("#" + table_id + " tbody tr").each(function(i) {
                // Add Ingredients
                /*var $ingredientsTD = $("<td></td>");
                if (data.page_data && data.page_data[i] && data.page_data[i].ingredients) {
                    $.each(data.page_data[i].ingredients, function(i, ingredient) {
                        $ingredientsTD.append("<div class='medication_ingredient'>" + ingredient.name + " " + ingredient.value + ingredient.unit + "</div>");
                    });
                }

                $(this).append($ingredientsTD);*/

                // Add delete button
                var $delete = $("<a>Remove</a>").click(function() {
                    if (!confirm("Are you sure you would like to remove this medication?")) return;
                    var $tr = $(this).closest("tr");

                    // get medication from tr's ID
                    var match = /(\d+)$/.exec($tr.attr("id"));
                    if (match && match.length > 0) {
                        var medicationId = match[1];
                        $.ajax({
                            url: '/admin/inventory/delete/' + medicationId,
                            type: 'POST',
                            dataType: 'json'
                        }).done(function (data) {
                            $("#currentMedicationsGrid").bs_grid('displayGrid', true);
                        });
                    }
                });

                $(this).append($("<td></td>").append($delete));
            });
        }
    });

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
        /*if ($.trim(inventoryFields.newMedication.quantity.val()) === "")
            pass = false;*/
        if ($.trim(inventoryFields.newMedication.form.val()) === "")
            pass = false;
        if ($.trim(inventoryFields.newMedication.name.val()) === "")
            pass = false;

        inventoryFields.newMedication.refreshFields();
        /*
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
        */
        return pass;
    },
    bindSubmitMedicationButton: function () {
        $("form").submit(function(event) {
            if (!medicationInventoryFeature.validateMedication()) {
                alert("Must provide at least a form and medication name");
                event.preventDefault();
                return false;
            }
            return true;
        });
    },
    bindAddNewIngredientButton: function () {
        $('#addNewIngredient').click(function () {
            medicationInventoryFeature.addIngredient();
        });
    }
};