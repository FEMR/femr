
var medicalFieldValidator = {
    isValid : true,
    errors: [],
    validatePrescriptions : function(){
        var prescriptions = [];
        prescriptionFeature.refreshSelectors();
        $(prescriptionFeature.allPrescriptions).each(function(){
            var $medicationID = $(this).find(".medicationID");
                /*$medicationName = $(this).find(".medicationName.tt-input"),
                $prescriptionAmount = $(this).find(".prescriptionAmount > input");

            if ($.trim($prescriptionAmount.val()) == "" && $medicationName.val() != ""){
                medicalFieldValidator.errors.push("Prescription amount cannot be blank.");
            }*/

            prescriptions.push($medicationID.val());
        });

        prescriptions.sort(); //Sort by value
        var last = prescriptions[0];
        for (var i=1; i<prescriptions.length; i++){
            if (prescriptions[i] == last && prescriptions[i] != ''){
                medicalFieldValidator.errors.push("Duplicate prescriptions can not be submitted.");
            }
            last = prescriptions[i];
        }
    }
};

function validate(){
    medicalFieldValidator.validatePrescriptions();
    if(medicalFieldValidator.errors.length){
        var alertMessage = "";
        for(var i = 0, len = medicalFieldValidator.errors.length; i < len; ++i){
           alertMessage += medicalFieldValidator.errors[i] + "\n";
        }
        medicalFieldValidator.errors = [];
        alert(alertMessage);

        return false;
    }

    return true;
}