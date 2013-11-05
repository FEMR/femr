//onClick activators
$('.newPatientBtn').click(function () {
    window.location = "/triage";
});

$('#femaleBtn').change(function () {
    if ($('#pregnantWrap').hasClass('hidden')) {
        $('#pregnantWrap').removeClass('hidden');
    }
});
$('#maleBtn').change(function () {
    if (!$('#pregnantWrap').hasClass('hidden')) {
        $('#pregnantWrap').addClass('hidden');
    }
    if ($('#pregnantBtn').is(':checked')) {
        $('#pregnantBtn').prop('checked', false);
        $('#pregnantBtn').parent().removeClass('active');
    }
    if (!$('#weeksWrap').hasClass('hidden')) {
        $('#weeksWrap').addClass('hidden');
    }
});
$('#pregnantBtn').change(function () {
    $('#weeksWrap').removeClass('hidden');
});

(function () {
    $('triageSubmitBtn').bind('click', function () {
        var textVal = $('txtDate').val();
        if (isDate(txtVal))
            alert('Valid Date');
        else
            alert('Invalid Date');
    });
});


//BMI auto- calculator
window.setInterval(function () {
    if ($('#heightFeet').val() && $('#weight').val() && $('#heightInches').val()) {

        var weight_lbs = parseInt($('#weight').val());
        var height_in = parseInt($('#heightInches').val());
        var height_ft = parseInt($('#heightFeet').val());

        height_in = height_in + height_ft * 12;

        $('#bmi').val(Math.round((weight_lbs / (height_in * height_in)) * 703));

    }
}, 500);

//Datepicker select age
$(document).ready(function () {
    $('.datepicker-age').datepicker({
        setDate: new Date(),
        format: "yyyy/mm/dd",
        autoclose: true
    });
});

$('#age').change(function () {
    if (!$('#age').val()) {
        $('#years').removeAttr('disabled');
    }
    else if ($('#age').val()) {
      $('#years').attr('disabled', 'disabled');
    }
});

$('#years').change(function () {
    if ($('#years').val()) {

        $('#age').attr('disabled', 'disabled');

        var years = $('#years').val();
        var now = new Date();
        now.setFullYear(now.getFullYear() - years);
        var datestring = now.toDateString();
        var day = ("0" + now.getDate()).slice(-2);
        var month = ("0" + (now.getMonth() + 1)).slice(-2);
        var birthday = now.getFullYear() + "-" + (month) + "-" + (day);
        $('#age').val(birthday);
        //console.log($('#age').val());
    }
    else if (!$('#years').val()){
        $('#age').val('');
        $('#age').removeAttr('disabled');
}

});


