$(document).ready(function () {
    $('.replaceBtn').click(replaceClick);

    var customTypeAheadMatcher = function (strs) {
        return function findMatches(q, cb) {
            var matches, substrRegex;
            // an array that will be populated with substring matches
            matches = [];
            // regex used to determine if a string contains the substring `q`
            substrRegex = new RegExp(q, 'i');

            // iterate through the pool of medication for any name that
            // contains the substring `q`, add it to the `matches` array
            $.each(strs.medication, function (i, med) {
                if (substrRegex.test(med.name)) {
                    // the typeahead jQuery plugin expects suggestions to a
                    // JavaScript object, refer to typeahead docs for more info
                    matches.push({ id: med.id, value: med.name + " (" + med.form + ")" });
                }
            });

            cb(matches);
        };
    }

    typeaheadFeature.setGlobalVariable("/search/typeahead/medicationsWithID").then(function() {
        // Initialize typeahead for all medicationName's
        $(".medicationName").each(function() {
            typeaheadFeature.initalizeTypeAhead($(this),
                'medication',
                true,
                true,
                customTypeAheadMatcher, //Custom matcher for typeahead
                function (event, item) { //Custom on select function for typeahead
                    var $medicationID = $(this).closest(".prescriptionRow").find(".medicationID");
                    $medicationID.val(item.id);
                }
            );
        });
    });

    // Recalculate amount when days input is changed
    $(".prescriptionAdministrationDays > input").on("keyup change",
        calculateTotalPrescriptionAmount
    );

    // Recalculate amount when Administration is changed
    $(".prescriptionAdministrationName > select").on("change",
        calculateTotalPrescriptionAmount
    );
});

// Recalculate the  Amount based on days and Administration type
function calculateTotalPrescriptionAmount() {
    var $prescriptionRow = $(this).closest(".prescriptionRow");
    var $amountInput = $prescriptionRow.find(".prescriptionAmount input");
    var modifier = parseFloat($prescriptionRow.find(".prescriptionAdministrationName select option:selected").attr("data-modifier"));
    var days = parseFloat($prescriptionRow.find(".prescriptionAdministrationDays input").val());

    $amountInput.val(Math.ceil(modifier * days));
}


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

