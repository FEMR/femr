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
            var age = patientInformation.years.val();
            if (age >= 65){patientInformation.ageClassification.filter('elder:checked');}
            else if (age >= 18){patientInformation.ageClassification.filter('adult:checked');}
            else if (age >= 13){patientInformation.ageClassification.filter('teen:checked');}
            else if (age >= 2) {patientInformation.ageClassification.filter('child:checked');}
            else{patientInformation.ageClassification.filter('infant:checked');}
            $('#ageClassificationWrap').css('border', 'none');
        }
        

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