var triageFieldValidator = {
    isValid: true,
    validatePatientInformation: function () {
        var patientInformation = triageFields.patientInformation;//located in triage.js

        //validate First Name
        if (!patientInformation.firstName.val()) {
            patientInformation.firstName.attr("placeholder", "Required Input");
            patientInformation.firstName.css('border-color', 'red');
            triageFieldValidator.isValid = false;
        }
        //validate Last Name
        if (!patientInformation.lastName.val()) {
            patientInformation.lastName.attr("placeholder", "Required Input");
            patientInformation.lastName.css('border-color', 'red');
            triageFieldValidator.isValid = false;
        }
        //validate City
        if (!patientInformation.city.val()) {
            patientInformation.city.attr("placeholder", "Required Input");
            patientInformation.city.css('border-color', 'red');
            triageFieldValidator.isValid = false;
        }

        if (!patientInformation.age.val() && !patientInformation.ageClassification.val() && !patientInformation.months.val() && !patientInformation.years.val() && !$('#readOnlyBirthDate').val() && !$('#readOnlyAge').val()) {
            $('#ageClassificationWrap').css('border', '1px solid red');
            triageFieldValidator.isValid = false;
        } else {
            $('#ageClassificationWrap').css('border', 'none');
        }
        /*
         //validate age
         if (!patientInformation.age.val() && !$('#readOnlyBirthDate').val()) {
         //if an age was not entered and age is not stored
         //$('#age').val(null);
         patientInformation.age.css('border-color', 'red');
         patientInformation.years.val(null);
         patientInformation.years.css('border-color', 'red');
         patientInformation.years.attr('placeholder', 'Input age');
         patientInformation.months.val(null);
         patientInformation.months.css('border-color', 'red');
         patientInformation.months.attr('placeholder', 'or birth date');
         triageFieldValidator.isValid = false;
         } else if (patientInformation.age.val()) {
         //age has a value
         var inputYear = patientInformation.age.val().split('-')[0];
         var inputMonth = patientInformation.age.val().split('-')[1] - 1;
         var inputDay = patientInformation.age.val().split('-')[2];
         var inputDate = new Date(inputYear, inputMonth, inputDay);
         if (inputDate > Date.now()) {
         //$('#age').val(null);
         patientInformation.age.css('border-color', 'red');
         patientInformation.years.val(null);
         patientInformation.years.css('border-color', 'red');
         patientInformation.years.attr('placeholder', 'Input age');
         patientInformation.months.val(null);
         patientInformation.months.css('border-color', 'red');
         patientInformation.months.attr('placeholder', 'or birth date');
         triageFieldValidator.isValid = false;
         }
         }

         if (!patientInformation.years.val() && !patientInformation.months.val() && !$('#readOnlyAge').val()) {
         patientInformation.age.css('border-color', 'red');
         patientInformation.years.val(null);
         patientInformation.years.css('border-color', 'red');
         patientInformation.years.attr('placeholder', 'Input age');
         patientInformation.months.val(null);
         patientInformation.months.css('border-color', 'red');
         patientInformation.months.attr('placeholder', 'or birth date');
         triageFieldValidator.isValid = false;
         }
         */
        /*
         //make sure age isn't over 500
         var years, months;
         if (patientInformation.years.val()) {
         years = parseInt($('#years').val());
         } else {
         years = 0;
         }
         if (patientInformation.months.val()) {
         months = parseInt($('#months').val());
         } else {
         months = 0;
         }
         if (years + months / 12 > 500) {
         //$('#age').val(null);
         patientInformation.age.css('border-color', 'red');
         patientInformation.years.val(null);
         patientInformation.years.css('border-color', 'red');
         patientInformation.months.val(null);
         patientInformation.months.css('border-color', 'red');
         triageFieldValidator.isValid = false;
         }    */

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