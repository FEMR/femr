const validatePatientInformation = () => {
    var patientInformation = triageFields.patientInformation;//located in triage.js
    var isPatientInformationValid = true;

    //validate First Name
    if (!patientInformation.firstName.val().toString().trim()) {
        patientInformation.firstName.attr("placeholder", "Required Input");
        $(patientInformation.firstName).parent(".generalInfoInput").addClass("has-errors");
        triageFieldValidator.isValid = isPatientInformationValid = false;
    }
    else{
        $(patientInformation.firstName).parent(".generalInfoInput").removeClass("has-errors");
    }

    //validate Last Name
    if (!$.trim(patientInformation.lastName.val())) {
        patientInformation.lastName.attr("placeholder", "Required Input");
        $(patientInformation.lastName).parent(".generalInfoInput").addClass("has-errors");
        triageFieldValidator.isValid = isPatientInformationValid = false;
    }
    else{
        $(patientInformation.lastName).parent(".generalInfoInput").removeClass("has-errors");
    }

    //validate City
    if (!patientInformation.city.val().toString().trim()) {
        patientInformation.city.attr("placeholder", "Required Input");
        $(patientInformation.city).parents(".generalInfoInput").addClass("has-errors");
        triageFieldValidator.isValid = isPatientInformationValid = false;
    }
    else{
        $(patientInformation.city).parents(".generalInfoInput").removeClass("has-errors");
    }


    // Either ageClassification or birthdate or AgeYears/AgeMonths has to be filled out
    const ageFilledOut = (patientInformation.ageMonths.val().toString().length > 0 && integerCheck(patientInformation.ageMonths.val())) ||
        (patientInformation.ageYears.val().toString().length > 0 && integerCheck(patientInformation.ageYears.val()))

    const ageClassificationFilledOut = patientInformation.ageClassification.filter(':checked').val();

    const birthDateFilledOut = patientInformation.birthDate.val().toString().length > 0;


    if (!ageFilledOut && !ageClassificationFilledOut && !birthDateFilledOut) {
        $('#ageInputWrap').children(".generalInfoInput").addClass("has-errors");
        triageFieldValidator.isValid = isPatientInformationValid = false;
    } else {
        $('#ageInputWrap').children(".generalInfoInput").removeClass("has-errors");
    }

    // validate gender
    if (!$(patientInformation.sex).is(":checked")) {
        $(patientInformation.sex).parents(".generalInfoInput").addClass("has-errors");
        triageFieldValidator.isValid = isPatientInformationValid = false;
    }

    return isPatientInformationValid;
}

var triageFieldValidator = {
    isValid: true,
    validatePatientInformation: function () {
        validatePatientInformation();
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