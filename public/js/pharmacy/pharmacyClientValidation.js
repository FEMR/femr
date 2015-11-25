$(document).ready(function () {
    $("form").submit(function(event) {
        var pass = true;
        if (checkReplacementPrescriptions() === false) {
            alert("Duplicate prescriptions can not be submitted.");
            event.preventDefault();
        }

        return pass;
    })
});

function checkReplacementPrescriptions() {

    var medications = [];

    $(".medication").each(function() {
        if ($(this).has(".replacement") && !$(this).find(".replacement").hasClass("hidden")) {
            var $medicationID = $(this).find(".medicationID");
            var $prescriptionAmount = $(this).find(".prescriptionAmount > input");

            // Set prescriptionAmount to 0 if it is NAN
            if ($.trim($prescriptionAmount.val()) == "") $prescriptionAmount.val("0");

            medications.push($medicationID.val());
        } else {
            // Only dispensing
            medications.push($(this).attr("data-medID"));
        }
    });

    medications.sort();
    var last = medications[0];
    for (var i = 1; i < medications.length; i++) {
        if (typeof medications[i] != 'undefined') {
            if (medications[i].trim() === last.trim() && medications[i] != '') {
                return false;
            }
        }
        last = medications[i];
    }
}