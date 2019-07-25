var triageFieldValidator = {
    isValid: true,
    validatePatientInformation: function () {
        var patientInformation = triageFields.patientInformation;//located in triage.js

        //validate First Name
        if (!$.trim(patientInformation.firstName.val())) {
            patientInformation.firstName.attr("placeholder", "Required Input");
            $(patientInformation.firstName).parent(".generalInfoInput").addClass("has-errors");
            triageFieldValidator.isValid = false;
        }
        else{
            $(patientInformation.firstName).parent(".generalInfoInput").removeClass("has-errors");
        }

        //validate Last Name
        if (!$.trim(patientInformation.lastName.val())) {
            patientInformation.lastName.attr("placeholder", "Required Input");
            $(patientInformation.lastName).parent(".generalInfoInput").addClass("has-errors");
            triageFieldValidator.isValid = false;
        }
        else{
            $(patientInformation.lastName).parent(".generalInfoInput").removeClass("has-errors");
        }

        //validate City
        if (!$.trim(patientInformation.city.val())) {
            patientInformation.city.attr("placeholder", "Required Input");
            $(patientInformation.city).parents(".generalInfoInput").addClass("has-errors");
            triageFieldValidator.isValid = false;
        }
        else{
            $(patientInformation.city).parents(".generalInfoInput").removeClass("has-errors");
        }

        //Validate Age
        if ( (patientInformation.months.val().length > 0 && !integerCheck(patientInformation.months.val())) ||
            (patientInformation.years.val().length > 0 && !integerCheck(patientInformation.years.val()))
        ){
            $('#ageClassificationWrap').children(".generalInfoInput").addClass("has-errors");
            triageFieldValidator.isValid = false;
        }
        else if (!patientInformation.age.val() && !patientInformation.ageClassification.filter(':checked').val() && !patientInformation.months.val() && !patientInformation.years.val() && !$('#readOnlyBirthDate').val() && !$('#readOnlyAge').val()) {
            //nothing has been filled out
            $('#ageClassificationWrap').children(".generalInfoInput").addClass("has-errors");
            triageFieldValidator.isValid = false;

        } else if ((patientInformation.months.val() || patientInformation.years.val()) && patientInformation.ageClassification.filter(':checked').val()) {
            // This checks that the patients age is in the correct range (if chosen), months is irrelevant in this calculation
            // - the UI does something kinda wonky with a revisiting patient: the #months hidden field is total months of age, not the months since the last bday,
            //      fixing this could cause other bugs, so eliminating months instead
            //      - see femr.common.models.PatientItem::monthsOld
            var totalAge = 0.0;
            if(patientInformation.years.val()){
                totalAge = parseFloat(patientInformation.years.val());
            }
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
                $("#conflictingAgeMessage").hide();
                $('#ageClassificationWrap').children(".generalInfoInput").removeClass("has-errors");
            }
            else {
                $("#conflictingAgeMessage").show();
                $('#ageClassificationWrap').children(".generalInfoInput").addClass("has-errors");
                triageFieldValidator.isValid = false;
            }
        }
        else{
            //something has been filled out
            $('#ageClassificationWrap').children(".generalInfoInput").removeClass("has-errors");
        }

        // check for future birthdate
        if(patientInformation.age.val()){
            var birthDate = new Date(patientInformation.age.val());
            var today = new Date();

            var years = patientInformation.years.val();

            if(birthDate > today || years > 120){
                $('#ageClassificationWrap').children(".generalInfoInput").addClass("has-errors");
                triageFieldValidator.isValid = false;
            }
        }

        // validate gender
        if (!$(patientInformation.sex).is(":checked")) {
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