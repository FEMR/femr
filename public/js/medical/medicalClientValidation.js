
var medicalFieldValidator = {
    isValid : true,
    validatePrescriptions : function(){
        var prescriptions = [];
        $(prescriptionFeature.allPrescriptions).each(function(){
            prescriptions.push($(this).val());
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



}