var vitalClientValidator = function (vitalElements) {
    var isMetric = ($("#vitalsUnits").val() == "metric");
    var isValid = true;
    //Respirations
    if ($.trim(vitalElements.respiratoryRate.val().length) > 0 && integerCheck(vitalElements.respiratoryRate.val()) === false) {
        vitalElements.respiratoryRate.css('border-color', 'red');
        isValid = false;
    }
    //Blood Pressure - Systolic
    if ($.trim(vitalElements.bloodPressureSystolic.val().length) > 0 && integerCheck(vitalElements.bloodPressureSystolic.val()) === false) {
        vitalElements.bloodPressureSystolic.css('border-color', 'red');
        isValid = false;
    }
    //Blood Pressure - Diastolic
    if ($.trim(vitalElements.bloodPressureDiastolic.val().length) > 0 && integerCheck(vitalElements.bloodPressureDiastolic.val()) === false) {
        vitalElements.bloodPressureDiastolic.css('border-color', 'red');
        isValid = false;
    }
    //Heart Rate
    if ($.trim(vitalElements.heartRate.val().length) > 0 && integerCheck(vitalElements.heartRate.val()) === false) {
        vitalElements.heartRate.css('border-color', 'red');
        isValid = false;
    }
    //Oxygen
    if ($.trim(vitalElements.oxygenSaturation.val().length) > 0 && decimalCheck(vitalElements.oxygenSaturation.val()) === false) {
        vitalElements.oxygenSaturation.css('border-color', 'red');
        isValid = false;
    }
    //Temperature
    if ($.trim(vitalElements.temperature.val().length) > 0 && decimalCheck(vitalElements.temperature.val()) === false) {
        vitalElements.temperature.css('border-color', 'red');
        isValid = false;
    }
    //Weight
    if ($.trim(vitalElements.weight.val().length) > 0 && decimalCheck(vitalElements.weight.val()) === false) {
        vitalElements.weight.css('border-color', 'red');
        isValid = false;
    }
    //Height - Feet
    if ($.trim(vitalElements.heightFeet.val().length) > 0 && integerCheck(vitalElements.heightFeet.val()) === false) {
        vitalElements.heightFeet.css('border-color', 'red');
        isValid = false;
    }
    //Height - Inches
    if ($.trim(vitalElements.heightInches.val().length) > 0 && integerCheck(vitalElements.heightInches.val()) === false) {
        vitalElements.heightInches.attr("placeholder", "Enter a Number");
        vitalElements.heightInches.css('border-color', 'red');
        isValid = false;
    }
    //Height - Inches less than 12, CM less than 100
    var maxHeight = (isMetric) ? 100 : 12;
    if ($.trim(vitalElements.heightInches.val()) >= maxHeight) {
        vitalElements.heightInches.attr("placeholder", "Max value: " + (maxHeight - 1));
        vitalElements.heightInches.css('border-color', 'red');
        isValid = false;
    }
    //Glucose
    if ($.trim(vitalElements.glucose.val().length) > 0 && integerCheck(vitalElements.glucose.val()) === false) {
        vitalElements.glucose.css('border-color', 'red');
        isValid = false;
    }
    if (typeof(vitalElements.weeksPregnant) !== 'undefined') {
        //Pregnant - Weeks
        if ($.trim(vitalElements.weeksPregnant.val().length) > 0 && integerCheck(vitalElements.weeksPregnant.val()) === false) {
            vitalElements.weeksPregnant.css('border-color', 'red');
            isValid = false;
        }
        //Pregnant - Weeks zero or greater
        if ($.trim(vitalElements.weeksPregnant.val()) < 0) {
            vitalElements.weeksPregnant.attr("placeholder", "Min value: 0");
            vitalElements.weeksPregnant.css('border-color', 'red');
            isValid = false;
        }
    }
    return isValid;

};