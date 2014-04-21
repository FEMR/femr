$('#triageSubmitBtn').click(function () {
    return validate();
});

function validate(){
    var pass = new Boolean(true);

    if (checkRequiredInput() === false) {
        pass = false;
    }
    if (checkNumbers() === false){
        pass = false;
    }
    return pass;
}



function checkRequiredInput() {
    var pass = new Boolean(true);

    if (!$('#firstName').val()) {
        $('#firstName').attr("placeholder", "Required Input");
        $('#firstName').css('border-color','red');
        pass = false;
    }
    if (!$('#lastName').val()) {
        $('#lastName').attr("placeholder", "Required Input");
        $('#lastName').css('border-color','red');
        pass = false;
    }
    if (!$('#age').val() && !$('#readOnlyBirthDate').val()) {
        //$('#age').val(null);
        $('#age').css('border-color', 'red');
        $('#years').val(null);
        $('#years').css('border-color','red');
        $('#years').attr('placeholder','Input age');
        $('#months').val(null);
        $('#months').css('border-color','red');
        $('#months').attr('placeholder','or birth date');
        pass = false;
    }
    else if ($('#age').val()) {
        var inputYear = $('#age').val().split('-')[0];
        var inputMonth = $('#age').val().split('-')[1] - 1;
        var inputDay = $('#age').val().split('-')[2];
        var inputDate = new Date(inputYear, inputMonth, inputDay);
        if (inputDate > Date.now()) {
            //$('#age').val(null);
            $('#age').css('border-color', 'red');
            $('#years').val(null);
            $('#years').css('border-color','red');
            $('#years').attr('placeholder','Input age');
            $('#months').val(null);
            $('#months').css('border-color','red');
            $('#months').attr('placeholder','or birth date');
            pass = false;
        }
    }
    if (!$('#years').val() && !$('#months').val() && !$('#readOnlyAge').val()) {
        //$('#age').val(null);
        $('#age').css('border-color', 'red');
        $('#years').val(null);
        $('#years').css('border-color','red');
        $('#years').attr('placeholder','Input age');
        $('#months').val(null);
        $('#months').css('border-color','red');
        $('#months').attr('placeholder','or birth date');
        pass = false;
    }
    if (!$('#city').val()) {
        $('#city').attr("placeholder", "Required Input");
        $('#city').css('border-color','red');
        pass = false;
    }

    return pass;
}

function checkNumbers(){
    var pass = new Boolean(true);


    //Respirations
    if ($.trim($('#respiratoryRate').val().length) > 0 && integerCheck($('#respiratoryRate').val()) == false){
        $('#respiratoryRate').css('border-color','red');
        pass = false;
    }
    //Blood Pressure - Systolic
    if ($.trim($('#bloodPressureSystolic').val().length) > 0 && integerCheck($('#bloodPressureSystolic').val()) == false){
        $('#bloodPressureSystolic').css('border-color','red');
        pass = false;
    }
    //Blood Pressure - Diastolic
    if ($.trim($('#bloodPressureDiastolic').val().length) > 0 && integerCheck($('#bloodPressureDiastolic').val()) == false){
        $('#bloodPressureDiastolic').css('border-color','red');
        pass = false;
    }
    //Heart Rate
    if ($.trim($('#heartRate').val().length) > 0 && integerCheck($('#heartRate').val()) == false){
        $('#heartRate').css('border-color','red');
        pass = false;
    }
    //Oxygen
    if ($.trim($('#oxygenSaturation').val().length) > 0 && decimalCheck($('#oxygenSaturation').val()) == false){
        $('#oxygenSaturation').css('border-color','red');
        pass = false;
    }
    //Temperature
    if ($.trim($('#temperature').val().length) > 0 && decimalCheck($('#temperature').val()) == false){
        $('#temperature').css('border-color','red');
        pass = false;
    }
    //Weight
    if ($.trim($('#weight').val().length) > 0 && decimalCheck($('#weight').val()) == false){
        $('#weight').css('border-color','red');
        pass = false;
    }
    //Height - Feet
    if ($.trim($('#heightFeet').val().length) > 0 && integerCheck($('#heightFeet').val()) == false){
        $('#heightFeet').css('border-color','red');
        pass = false;
    }
    //Height - Inches
    if ($.trim($('#heightInches').val().length) > 0 && integerCheck($('#heightInches').val()) == false){
        $('#heightInches').attr("placeholder","Enter a Number");
        $('#heightInches').css('border-color','red');
        pass = false;
    }
    //Height - Inches less than 12
    if ($.trim($('#heightInches').val()) >= 12){
        $('#heightInches').attr("placeholder","Max value: 11");
        $('#heightInches').css('border-color','red');
        pass = false;
    }
    //Glucose
    if ($.trim($('#glucose').val().length) > 0 && integerCheck($('#glucose').val()) == false){
        $('#glucose').css('border-color','red');
        pass = false;
    }
    //Pregnant - Weeks
    if ($.trim($('#weeksPregnant').val().length) > 0 && integerCheck($('#weeksPregnant').val()) == false){
        $('#weeksPregnant').css('border-color','red');
        pass = false;
    }
    //Pregnant - Weeks zero or greater
    if ($.trim($('#weeksPregnant').val()) < 0){
        $('#weeksPregnant').attr("placeholder","Min value: 0");
        $('#weeksPregnant').css('border-color','red');
        pass = false;
    }
    //Age < 125
    var years, months;
    if ($('#years').val()) {
        years = parseInt($('#years').val());
    } else {
        years = 0;
    }
    if ($('#months').val()) {
        months = parseInt($('#months').val());
    } else {
        months = 0;
    }
    if (years + months/12 > 125) {
        //$('#age').val(null);
        $('#age').css('border-color', 'red');
        $('#years').val(null);
        $('#years').css('border-color','red');
        $('#years').attr('placeholder','Max age is');
        $('#months').val(null);
        $('#months').css('border-color','red');
        $('#months').attr('placeholder','125 yr 0 mo');
        pass = false;
    }
    return pass;
}