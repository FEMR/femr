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

    //TODO: If there is conflicting birthDate / Age then throw the conflicting message.

    // //Validate Age
    // if ()
    // ){
    //     triageFieldValidator.isValid = isPatientInformationValid = false;
    //     $('#ageClassificationWrap').children(".generalInfoInput").addClass("has-errors");
    // }
    // else if (!patientInformation.age.val() && !patientInformation.ageClassification.filter(':checked').val() && !patientInformation.months.val() && !patientInformation.years.val() && !$('#readOnlyBirthDate').val() && !$('#readOnlyAge').val()) {
    //     //nothing has been filled out
    //
    // } else if ((patientInformation.months.val() || patientInformation.years.val()) && patientInformation.ageClassification.filter(':checked').val()) {
    //     // This checks that the patients age is in the correct range (if chosen), months is irrelevant in this calculation
    //     // - the UI does something kinda wonky with a revisiting patient: the #months hidden field is total months of age, not the months since the last bday,
    //     //      fixing this could cause other bugs, so eliminating months instead
    //     //      - see femr.common.models.PatientItem::monthsOld
    //     var totalAge = 0.0;
    //     if(patientInformation.years.val()){
    //         totalAge = parseFloat(patientInformation.years.val());
    //     }
    //     var ageBirthdate = true;
    //
    //
    //     if (ageGroupYearMatch){
    //         $("#conflictingAgeMessage").hide();
    //     }
    //     else {
    //         $("#conflictingAgeMessage").show();
    //         $('#ageClassificationWrap').children(".generalInfoInput").addClass("has-errors");
    //         triageFieldValidator.isValid = isPatientInformationValid = false;
    //     }
    // }
    // else{
    //     //something has been filled out
    //     $('#ageClassificationWrap').children(".generalInfoInput").removeClass("has-errors");
    // }

    // // check for future birthdate
    // if(patientInformation.birthDate.val()){
    //     var birthDate = new Date(patientInformation.age.val());
    //     var today = new Date();
    //
    //     var years = patientInformation.birthDate.val();
    //
    //     if(birthDate > today || years > 120){
    //         $('#ageClassificationWrap').children(".generalInfoInput").addClass("has-errors");
    //         triageFieldValidator.isValid = isPatientInformationValid = false;
    //     }
    // }

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