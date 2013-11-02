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

$('#treatmentBtn').click(function(){
    $('#hpiControl').addClass('hidden');
    $('#treatmentControl').removeClass('hidden');
});

$('#hpiBtn').click(function(){
    $('#hpiControl').removeClass('hidden');
    $('#treatmentControl').addClass('hidden');
});