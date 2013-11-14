$(document).ready(function () {
    $('.newPatientBtn').click(function () {
        if (confirm("Are you sure you want to reset the fields?")){
            window.location = "/triage";
        }
        else{
            return;
        }

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
        if ($('#yesPregnantBtn').is(':checked')) {
            $('#yesPregnantBtn').prop('checked', false);
            $('#yesPregnantBtn').parent().removeClass('active');
        }
        if (!$('#weeksWrap').hasClass('hidden')) {
            $('#weeksWrap').addClass('hidden');
        }
    });

    $('#yesPregnantBtn').change(function(){
        $('#weeksWrap').removeClass('hidden');
        $('#boolPregnant').val(true);
    });

    $('#noPregnantBtn').change(function(){
        $('#weeksWrap').addClass('hidden');
        $('#boolPregnant').val(false);
    });

    $('.datepicker-age').datepicker({
        format: "yyyy-mm-dd",
        autoclose: true
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
                $('#age').css('border-color', 'red');
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
            if (nan || !(yrs > -1)) {
                $('#years').val('');
                $('#age').val('');
                $('#years').css('border-color', 'red');
                $('#years').attr('placeholder', 'Enter correct age in Years');
            }
        }
        // age in years erased, clear birthdate field
        else if (!$('#years').val()) {
            $('#age').val('');
        }
    });
});


$('#firstName').change(function () {
    $('#firstName').css('border', '');
});

$('#lastName').change(function () {
    $('#lastName').css('border', '');
});

$('#city').change(function () {
    $('#city').css('border', '');
});

$('#respirations').change(function () {
    $('#respirations').css('border', '');
});

$('#bloodPressureSystolic').change(function () {
    $('#bloodPressureSystolic').css('border', '');
});

$('#bloodPressureDiastolic').change(function () {
    $('#bloodPressureDiastolic').css('border', '');
});

$('#heartRate').change(function () {
    $('#heartRate').css('border', '');
});

$('#oxygen').change(function () {
    $('#oxygen').css('border', '');
});

$('#temperature').change(function () {
    $('#temperature').css('border', '');
});

$('#weight').change(function () {
    $('#weight').css('border', '');
});

$('#heightFeet').change(function () {
    $('#heightFeet').css('border', '');
});

$('#heightInches').change(function () {
    $('#heightInches').css('border', '');
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


(function () {
    // Format date object as yyyy-MM-dd
    Date.prototype.toYMD = Date_toYMD;
    function Date_toYMD() {
        var year, month, day;
        year = String(this.getFullYear());
        month = String(this.getMonth() + 1);
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
