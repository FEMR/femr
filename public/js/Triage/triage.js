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
        format: "yyyy-mm-dd",
        autoclose: true
    });
});

$('#age').change(function () {
    $('#age').css('border', '');
    // birthdate erased, clear years field
    if (!$('#age').val()) {
        $('#years').val('');
    }

    // birthdate not null calculate age in years
    else if ($('#age').val()) {
        var birthString = $('#age').val();
        var birthDate = new Date(birthString);
        var today = new Date();
        var currYear = today.getFullYear();
        var birthYear = birthDate.getFullYear();
        var ageInYears = currYear - birthYear;
        //console.log(ageInYears.valueOf());
        // birthdate is not a date clear fields
        var nan = randomString(ageInYears);
        if (!nan) {
            $('#years').val(ageInYears);
        }
        else {
            $('#age').css('border-color','red');
            $('#age').attr('placeholder', 'Enter a date: yyyy-mm-dd')
        }
    }
});

$('#years').change(function () {
    $('#years').css('border', '');
    var yrs = $('#years').val();
    // years in age not null calculate birthdate
    if (yrs) {
        var birthDate = new Date();
        birthDate.setFullYear(birthDate.getFullYear() - yrs);
        var birthString = birthDate.toYMD();
        var nan = randomString(birthDate);

        if (!nan) {
            $('#age').val(birthString);
        }
        if (nan || !(yrs  > -1)) {
            $('#years').val('');
            $('#age').val('');
            $('#years').css('border-color','red');
            $('#years').attr('placeholder','Enter correct age in Years');
        }
    }
    // age in years erased, clear birthdate field
    else if (!$('#years').val()) {
        $('#age').val('');
    }
});

(function() {
    // Format date object as yyyy-MM-dd
    Date.prototype.toYMD = Date_toYMD;
    function Date_toYMD() {
        var year, month, day;
        year = String(this.getFullYear());
        month = String(this.getMonth()+1);
        if (month.length == 1) {
            month = "0" + month;
        }
        day = String(this.getDate());
        if (day.length == 1) {
            day = "0" + day;
        }
        return year + "-" + month + "-" + day;
    }
})();

function randomString(strVal) {
    if (isNaN(strVal)) {
        $('#age').val('');
        $('#years').val('');
        return true;
    }
    return false;
}

/*
 * checks input for non negative decimal numbers
 */

// too much duplicate code. Needs refactoring
$('#id').change(function() {
    var idString = $('#id').val();
    var intRegex = /^\d+$/;
    if (intRegex.test(idString)) {
        $('#id').css('border', '');
    }
    else {
        $('#id').val('');
        $('#id').css('border-color','red');
        $('#id').attr('placeholder','Invalid Id');
    }
});

$('#triageSearch').click(function(event) {
    var idString = $('#id').val();
    var intRegex = /^\d+$/;
    if (intRegex.test(idString)) {
        $('#id').css('border', '');
    }
    else {
        //event.preventDefault();
        $('#id').val('');
        $('#id').css('border-color','red');
        $('#id').attr('placeholder','Invalid Id');
        return false;
    }
});

