
var medicalFieldValidator = {
    errors: [],
    validatePrescriptions : function(){
        var prescriptions = [];

        $(".prescription").each(function() {

            if ($(this).has(".replacement") && !$(this).find(".replacement").hasClass("hidden")) {
                var $medicationID = $(this).find(".medicationID");
                    /*$medicationName = $(this).find(".medicationName.tt-input"),
                    $prescriptionAmount = $(this).find(".prescriptionAmount > input");

                if ($.trim($prescriptionAmount.val()) == "" && $medicationName.val() != ""){
                    medicalFieldValidator.errors.push("Prescription amount cannot be blank.");
                }*/

                prescriptions.push($medicationID.val());
            } else {
                // Only dispensing
                prescriptions.push($(this).attr("data-medID"));
            }
        });

        prescriptions.sort();
        var last = prescriptions[0];
        for (var i = 1; i < prescriptions.length; i++) {
            if (typeof prescriptions[i] != 'undefined') {
                if (prescriptions[i].trim() === last.trim() && prescriptions[i] != '') {
                    medicalFieldValidator.errors.push("Duplicate prescriptions can not be submitted.");
                }
            }
            last = prescriptions[i];
        }
    }
}


$(document).ready(function () {
    $("form").submit(function(event) {
        medicalFieldValidator.validatePrescriptions();
        if (medicalFieldValidator.errors.length) {
            var alertMessage = "";
            for(var i = 0, len = medicalFieldValidator.errors.length; i < len; ++i){
                alertMessage += medicalFieldValidator.errors[i] + "\n";
            }
            medicalFieldValidator.errors = [];

            alert(alertMessage);
            event.preventDefault();
            return false;
        }

        return true;
    })
});
