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

$('#treatmentBtn').click(function(){
    $('#hpiControl').addClass('hidden');
    $('#treatmentControl').removeClass('hidden');
});

$('#hpiBtn').click(function(){
    $('#hpiControl').removeClass('hidden');
    $('#treatmentControl').addClass('hidden');
});