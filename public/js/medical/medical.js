//BMI auto- calculator
$(document).ready(function(){

    if ($('#heightFeet').text() && $('#weight').text() && $('#heightInches').text()){

        var weight_lbs = parseInt($('#weight').text());
        var height_in = parseInt($('#heightInches').text());
        var height_ft = parseInt($('#heightFeet').text());

        height_in = height_in + height_ft * 12;

        $('#bmi').text(Math.round((weight_lbs / (height_in * height_in)) * 703));

    }
});

//Unhides a prescription input box everytime
//the + button is clicked (max of 5)
$('#addPrescriptionButton').click(function(){

    if (typeof $("body").data("script") === "undefined"){
        $("body").data("script", 2);
    }
    else if ($("body").data("script") < 5){
        $("body").data("script", $("body").data("script") + 1);
    }
    else{
        return;
    }
    $("#prescription" + $("body").data("script")).removeClass("hidden");
    return;
});

//Unhides a problem input box everytime
//the + button is clicked (max of 5)
$('#addProblemButton').click(function(){

    if (typeof $("body").data("prob") === "undefined"){
        $("body").data("prob", 2);
    }
    else if ($("body").data("prob") < 5){
        $("body").data("prob", $("body").data("prob") + 1);
    }
    else{
        return;
    }
    $("#problem" + $("body").data("prob")).removeClass("hidden");
    return;
});

$('#treatmentBtn').click(function(){
    $('#hpiControl').addClass('hidden');
    $('#treatmentControl').removeClass('hidden');
});

$('#hpiBtn').click(function(){
    $('#hpiControl').removeClass('hidden');
    $('#treatmentControl').addClass('hidden');
});