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

        } else if ((patientInformation.months.val() || patientInformation.years.val()) && patientInformation.ageClassification.filter(':checked').val()) {
            var months;
            if(!patientInformation.months.val()){
                months = "0";
            }else{
               months =  patientInformation.months.val();
            }
            var years;
            if(!patientInformation.years.val()){
                years = "0";
            }else{
                years = patientInformation.years.val();
            }
            var totalAge = parseFloat(years) + (parseFloat(months) / 12);
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
                $('#ageClassificationWrap').css('border', 'none');
            }
            else {
                $('#ageClassificationWrap').css('border', '1px solid red');
                triageFieldValidator.isValid = false;
            }
        }
        else{
            //something has been filled out
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