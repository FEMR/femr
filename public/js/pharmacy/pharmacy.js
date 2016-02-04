
var prescriptionFeature = {
    medicationTypeaheadData: [],
    initializeMedicationTypeahead: function() {
        return $.getJSON("/search/typeahead/medicationsWithID", function (data) {
            prescriptionFeature.medicationTypeaheadData = data;
        });
    },
    medicationTypeaheadMatcher: function(strs) {
        return function findMatches(q, cb) {
            var substrRegex = new RegExp(q, 'i'); //Regex used to determine if a string contains substring 'q'
            var matches = []; //Array to be populated with matches

            //Iterate through medication and find matches
            $.each(strs.medication, function (i, med) {
                if (substrRegex.test(med.name)) {
                    med.value = med.name + " (" + med.form + ")";
                    matches.push(
                        med
                    );
                }
            });
            cb(matches);
        };
    },
    addMedicationTypeahead: function() {
        $(".prescriptionRow .medicationName").each(function() {
            $(this).typeahead({ hint: true, highlight: true },
                {
                    displayKey: 'value',
                    name: "medications",
                    source: prescriptionFeature.medicationTypeaheadMatcher(prescriptionFeature.medicationTypeaheadData),
                    templates: {
                        //suggestion: Handlebars.compile("<div>{{value}} {{#each ingredients}}<div class='medication_ingredient'>{{name}} {{value}}{{unit}}</div>{{/each}}</div>")
                    }
                }).on('typeahead:selected', function (event, item) {
                    var $medicationID = $(this).closest(".prescriptionRow").find(".medicationID");
                    $medicationID.val(item.id);
                }).on('typeahead:autocompleted', function (event, item, data) {
                    $(this).trigger("typeahead:selected", item);
                }).on("change", function () {
                    var $medicationID = $(this).closest(".prescriptionRow").find(".medicationID");
                    $medicationID.val("");  //Remove value if it is not one from typeahead
                });
        })
    },
    calculateTotalPrescriptionAmount: function() {

        var $prescriptionRow = $(this).closest(".prescriptionRow");
        var $amountInput = $prescriptionRow.find(".prescriptionAmount input");

        // Modifer per day for administration type
        var selectedOption= $prescriptionRow.find(".prescriptionAdministration").find("select.administrationName > option:selected");
        var modifier = parseFloat( $(selectedOption).data("modifier") );

        // Days to prescribe
        var days = parseFloat($prescriptionRow.find(".prescriptionAdministrationDays input").val());

        var amount = Math.ceil(modifier * days);
        $amountInput.val(amount);
    }
};
$(document).ready(function () {
    $('.replaceBtn').click(replaceClick);

    /* Setup calculate to days input on keyup/change events */
    $(".prescriptionRow").find(".prescriptionAdministrationDays > input").on("keyup change",
        prescriptionFeature.calculateTotalPrescriptionAmount
    );
    $(".prescriptionRow").find(".prescriptionAdministration > select").on("change",
        prescriptionFeature.calculateTotalPrescriptionAmount
    );
    prescriptionFeature.initializeMedicationTypeahead().then(function() {
        prescriptionFeature.addMedicationTypeahead();
    });
    /*prescriptionFeature.initializeAdministrationTypeahead().then(function() {
        prescriptionFeature.addAdministrationTypeahead();
    });*/
});



// Show/hide the replacement div
function replaceClick() {
    var $container = $(this).closest("li");
    var $replacementDiv = $container.find(".replacement");
    if ($replacementDiv.hasClass("hidden"))
        $replacementDiv.removeClass("hidden")
    else {
        $replacementDiv.addClass("hidden");
        // Reset input's to defaults
        $replacementDiv.find(".prescriptionInput input").val("");
        $replacementDiv.find(".prescriptionAmount input").val(0);
    }
}

