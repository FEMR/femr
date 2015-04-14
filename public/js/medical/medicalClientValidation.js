
var medicalFieldValidator = {
    isValid : true,
    validatePrescriptions : function(){
        var prescriptions = [];
        prescriptionFeature.refreshSelectors();
        $(prescriptionFeature.allPrescriptions).each(function(){
            var $prescriptionName = $(this).find(".prescriptionName > input");
            var $prescriptionAmount = $(this).find(".prescriptionAmount > input");

            // Set prescriptionAmount to 0 if it is NAN
            if ($.trim($prescriptionAmount.val()) == "") $prescriptionAmount.val("0");

            prescriptions.push($prescriptionName.val());
        });

        prescriptions.sort();
        var last = prescriptions[0];
        for (var i=1; i<prescriptions.length; i++){
            if (prescriptions[i] == last && prescriptions[i] != ''){
                medicalFieldValidator.isValid = false;
            }
            last = prescriptions[i];
        }
    }
};


function validate(){
    medicalFieldValidator.validatePrescriptions();
    if (medicalFieldValidator.isValid === false){
        alert("Duplicate prescriptions can not be submitted.");
        medicalFieldValidator.isValid = true;
        return false;
    }
    return true;
}