$('#triageSubmitBtn').click(function () {

    return validate();

});


function validate(){
    var pass = new Boolean(true);

    if (checkRequiredInput() === false) {
        pass = false;
    }
    else if (checkNumbers() === false){
        pass = false;
    }

    return pass;
}

function checkRequiredInput() {
    var pass = new Boolean(true);

    if (!$('#firstName').val()) {
        $('#firstName').attr("placeholder", "Required Input");
        pass = false;
    }
    if (!$('#lastName').val()) {
        $('#lastName').attr("placeholder", "Required Input");
        pass = false;
    }
    if (!$('#age').val()) {
        $('#age').attr("placeholder", "Required Input");
        pass = false;
    }
    if (!$('#city').val()) {
        $('#city').attr("placeholder", "Required Input");
        pass = false;
    }

    return pass;
}

function checkNumbers(){
    var pass = new Boolean(true);

    //Blood Pressure - Systolic
    if ($.trim($('#respirations').val().length) > 0 && $.isNumeric($('#respirations').val()) == false){
        $('#respirations').val("");
        $('#respirations').attr("placeholder","Enter a Number");
        pass = false;
    }
    //Blood Pressure - Diasolic
    if ($.trim($('#bloodPressureSystolic').val().length) > 0 && $.isNumeric($('#bloodPressureSystolic').val()) == false){
        $('#bloodPressureSystolic').val("");
        $('#bloodPressureSystolic').attr("placeholder","Enter a Number");
        pass = false;
    }
    //Respirations
    if ($.trim($('#bloodPressureDiastolic').val().length) > 0 && $.isNumeric($('#bloodPressureDiastolic').val()) == false){
        $('#bloodPressureDiastolic').val("");
        $('#bloodPressureDiastolic').attr("placeholder","Enter a Number");
        pass = false;
    }
    //Heart Rate
    if ($.trim($('#heartRate').val().length) > 0 && $.isNumeric($('#heartRate').val()) == false){
        $('#heartRate').val("");
        $('#heartRate').attr("placeholder","Enter a Number");
        pass = false;
    }
    //Oxygen
    if ($.trim($('#oxygen').val().length) > 0 && $.isNumeric($('#oxygen').val()) == false){
        $('#oxygen').val("");
        $('#oxygen').attr("placeholder","Enter a Number");
        pass = false;
    }
    //Temperature
    if ($.trim($('#temperature').val().length) > 0 && $.isNumeric($('#temperature').val()) == false){
        $('#temperature').val("");
        $('#temperature').attr("placeholder","Enter a Number");
        pass = false;
    }
    //Weight
    if ($.trim($('#weight').val().length) > 0 && $.isNumeric($('#weight').val()) == false){
        $('#weight').val("");
        $('#weight').attr("placeholder","Enter a Number");
        pass = false;
    }
    //Height - Feet
    if ($.trim($('#heightFeet').val().length) > 0 && $.isNumeric($('#heightFeet').val()) == false){
        $('#heightFeet').val("");
        $('#heightFeet').attr("placeholder","Enter a Number");
        pass = false;
    }
    //Height - Inches
    if ($.trim($('#heightInches').val().length) > 0 && $.isNumeric($('#heightInches').val()) == false){
        $('#heightInches').val("");
        $('#heightInches').attr("placeholder","Enter a Number");
        pass = false;
    }
    //Age (Vital)
    if ($.isNumeric($('#age').val()) == false){
        $('#age').val("");
        $('#age').attr("placeholder","Enter a Number");
        pass = false;
    }

    return pass;
}