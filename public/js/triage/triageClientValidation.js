var triageFieldValidator = {
    isValid: true,
    validatePatientInformation: function () {
        var patientInformation = triageFields.patientInformation;//located in triage.js

        //validate First Name
        if (!$.trim(patientInformation.firstName.val())) {
            patientInformation.firstName.attr("placeholder", "Required Input");
            // patientInformation.firstName.css('border-color', 'red');
            $(patientInformation.firstName).parent(".generalInfoInput").addClass("has-errors");
            triageFieldValidator.isValid = false;
        }
        //validate Last Name
        if (!$.trim(patientInformation.lastName.val())) {
            patientInformation.lastName.attr("placeholder", "Required Input");
            // patientInformation.lastName.css('border-color', 'red');
            $(patientInformation.lastName).parent(".generalInfoInput").addClass("has-errors");
            triageFieldValidator.isValid = false;
        }
        //validate City
        if (!$.trim(patientInformation.city.val())) {
            patientInformation.city.attr("placeholder", "Required Input");
            $(patientInformation.city).parents(".generalInfoInput").addClass("has-errors");
            // patientInformation.city.css('border-color', 'red');
            triageFieldValidator.isValid = false;
        }
        //Validate Age
        if (!patientInformation.age.val() && !patientInformation.ageClassification.filter(':checked').val() && !patientInformation.months.val() && !patientInformation.years.val() && !$('#readOnlyBirthDate').val() && !$('#readOnlyAge').val()) {
            //nothing has been filled out
            $('#ageClassificationWrap').children(".generalInfoInput").addClass("has-errors");
            // $('#ageClassificationWrap').css('border', '1px solid red');
            triageFieldValidator.isValid = false;

        } else if ((patientInformation.months.val() || patientInformation.years.val()) && patientInformation.ageClassification.filter(':checked').val()) {
            var months = 0.0;
            if(patientInformation.months.val()){
               months =  parseFloat(patientInformation.months.val());
            }
            var years = 0.0;
            if(patientInformation.years.val()){
                years = parseFloat(patientInformation.years.val());
            }
            var totalAge = years + (months / 12);
            var ageGroupYearMatch = true;

            switch (patientInformation.ageClassification.filter(':checked').val()){
                case 'infant':
                    if (totalAge >= 2) {
                        ageGroupYearMatch = false;
                    }
                    break;
                case 'child':
                    if ((totalAge < 2) || (totalAge >= 13) ){
                        ageGroupYearMatch = false;
                    }
                    break;
                case 'teen':
                    if ((totalAge < 13) || (totalAge >= 18)){
                        ageGroupYearMatch = false;
                    }
                    break;
                case 'adult':
                    if ((totalAge < 18) || (totalAge >= 65)){
                        ageGroupYearMatch = false;
                    }
                    break;
                case 'elder':
                    if (totalAge < 65){
                        ageGroupYearMatch = false;
                    }
                    break;
            }
            if (ageGroupYearMatch){
                $('#ageClassificationWrap').children(".generalInfoInput").removeClass("has-errors");
                // $('#ageClassificationWrap').css('border', 'none');
            }
            else {
                $('#ageClassificationWrap').children(".generalInfoInput").addClass("has-errors");
                // $('#ageClassificationWrap').css('border', '1px solid red');
                triageFieldValidator.isValid = false;
            }
        }
        else{
            //something has been filled out
            $('#ageClassificationWrap').children(".generalInfoInput").removeClass("has-errors");
            // $('#ageClassificationWrap').css('border', 'none');
        }

        // validate gender
        if (!$(patientInformation.sex).is(":checked")) {
            console.log("No Sex");
            $(patientInformation.sex).parents(".generalInfoInput").addClass("has-errors");
            triageFieldValidator.isValid = false;
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