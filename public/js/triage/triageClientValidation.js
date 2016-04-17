var triageFieldValidator = {
    isValid: true,
    validatePatientInformation: function () {
        var patientInformation = triageFields.patientInformation;//located in triage.js

        //validate First Name
        if (!$.trim(patientInformation.firstName.val())) {
            patientInformation.firstName.attr("placeholder", "Required Input");
            patientInformation.firstName.css('border-color', 'red');
            triageFieldValidator.isValid = false;
        }
        //validate Last Name
        if (!$.trim(patientInformation.lastName.val())) {
            patientInformation.lastName.attr("placeholder", "Required Input");
            patientInformation.lastName.css('border-color', 'red');
            triageFieldValidator.isValid = false;
        }
        //validate City
        if (!$.trim(patientInformation.city.val())) {
            patientInformation.city.attr("placeholder", "Required Input");
            patientInformation.city.css('border-color', 'red');
            triageFieldValidator.isValid = false;
        }
        //Validate Age
        if (!patientInformation.age.val() && !patientInformation.ageClassification.filter(':checked').val() && !patientInformation.months.val() && !patientInformation.years.val() && !$('#readOnlyBirthDate').val() && !$('#readOnlyAge').val()) {
            //nothing has been filled out
            $('#ageClassificationWrap').css('border', '1px solid red');
            triageFieldValidator.isValid = false;
        } else {
            //something has been filled out
            $('#ageClassificationWrap').css('border', 'none');
        }

        //Validate Age and Age Classification
        // Compares the differences in months in the current Date and input Age
        /*var d = new Date;
        var inputYears = patientInformation.age.val().split('-')[0];
        var inputMonths = patientInformation.age.val().split('-')[1]-1;
        var months = inputMonths + inputYears * 12;
        var diffMonths = (d.getMonth() + d.getFullYear() * 12) - months;
        var diffYears = Math.floor(diffMonths / 12);
        switch(patientInformation.ageClassification.filter(':checked').val()) {
            case "infant":
                if (!(diffYears >= 0 && diffYears <= 1)) {
                    triageFieldValidator.isValid = false;
                    $('#classificationRadioWrap').css('border', '1px solid red');
                }
                break;
            case "child": 
                if(!(diffYears >= 2 && diffYears <= 12)) {
                    triageFieldValidator.isValid = false;
                    $('#classificationRadioWrap').css('border', '1px solid red');
                }
                break;
            case "teen": 
                if(!(diffYears >= 13 && diffYears <= 17)) {
                    triageFieldValidator.isValid = false;
                    $('#classificationRadioWrap').css('border', '1px solid red');
                }
                break;
            case "adult": 
                if(!(diffYears >= 18 && diffYears <= 64)) {
                    triageFieldValidator.isValid = false;
                    $('#classificationRadioWrap').css('border', '1px solid red');
                }
                break;
            case "elder": 
                if(!(diffYears >= 65)) {
                    triageFieldValidator.isValid = false;
                    $('#classificationRadioWrap').css('border', '1px solid red');
                }
                break;
            default:
                triageFieldValidator.isValid = false;
                break;
        }*/
        
    },
    validatePatientVitals: function () {
        var patientVitals = triageFields.patientVitals;//located in triage.js
        triageFieldValidator.isValid = vitalClientValidator(patientVitals);
    }
};

function validate() {
    //always check vitals first
    triageFieldValidator.validatePatientVitals();
    triageFieldValidator.validatePatientInformation();
    return triageFieldValidator.isValid;
}