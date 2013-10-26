//onClick activators
$('.newPatientBtn').click(function(){
    window.location = "/triage";
});

$('#femaleBtn').change(function(){
    if ($('#pregnantWrap').hasClass('hidden')){
        $('#pregnantWrap').removeClass('hidden');
    }
});
$('#maleBtn').change(function(){
    if (!$('#pregnantWrap').hasClass('hidden')){
        $('#pregnantWrap').addClass('hidden');
    }
    if ($('#pregnantBtn').is(':checked')){
        $('#pregnantBtn').prop('checked',false);
        $('#pregnantBtn').parent().removeClass('active');
    }
    if (!$('#weeksWrap').hasClass('hidden')){
        $('#weeksWrap').addClass('hidden');
    }
});
$('#pregnantBtn').change(function(){
   $('#weeksWrap').removeClass('hidden');
});



//BMI auto- calculator
window.setInterval(function(){
    if ($('#heightFeet').val() && $('#weight').val() && $('#heightInches').val()){

        var weight_lbs = parseInt($('#weight').val());
        var height_in = parseInt($('#heightInches').val());
        var height_ft = parseInt($('#heightFeet').val());

        height_in = height_in + height_ft * 12;

        $('#bmi').val(Math.round((weight_lbs / (height_in * height_in)) * 703));

    }
}, 500);
