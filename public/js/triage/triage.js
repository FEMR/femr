$(document).ready(function () {
    $('.newPatientBtn').click(function () {
        if (confirm("Are you sure you want to reset the fields?")) {
            window.location = "/triage";
        }
        else {
            return;
        }

    });

    $('#femaleBtn').change(function () {
        $('#pregnancyBtn').removeClass('disabled');
    });
    $('#maleBtn').change(function () {
        $('#pregnancyBtn').removeClass('active');
        $('#pregnancyBtn').addClass('disabled');
        $('#weeksPregnant').val('');
        $('#weeksPregnant').attr('disabled','disabled');
    });
    $('#pregnancyBtn').change(function(){
        if (typeof $("#weeksPregnant").attr('disabled') === "undefined"){
            $('#weeksPregnant').val('');
            $('#weeksPregnant').attr('disabled','disabled');
        }
        else{
            $('#weeksPregnant').removeAttr('disabled');
        }


    })

    $('#month').change(function (){
        var dropdownMonth = parseInt($('#month').val()-1);
        var dropdownDay = parseInt($('#day').val());
        var dropdownYear = parseInt($('#year').val());

        if((dropdownMonth >= 0))
        {
            $('#month').css('border', '');
        }
        else
        {
            $('#month').css('border-color','red');
            $('#years').css('border-color','red');
            $('#age').val(null);
            $('#years').val(null);
        }

        if((dropdownMonth >= 0) && ((dropdownDay && dropdownYear) >0))
        {
            var dropdownDate = new Date(dropdownYear, dropdownMonth, dropdownDay);
            var ageInYears = ~~((Date.now() - dropdownDate)/(31557600000));
            console.log(ageInYears);
            // birthdate is not a date clear fields
            var nan = randomString(ageInYears);
            if (dropdownDate <= Date.now()) {
                $('#years').val(Math.floor(ageInYears));
            }

            $('#years').css('border', '');
            $('#age').val(dropdownDate.toYMD())
        }
    });

    $('#day').change(function (){
        var dropdownMonth = parseInt($('#month').val()-1);
        var dropdownDay = parseInt($('#day').val());
        var dropdownYear = parseInt($('#year').val());

        if((dropdownDay > 0))
        {
            $('#day').css('border', '');
        }
        else
        {
            $('#day').css('border-color','red');
            $('#years').css('border-color','red');
            $('#age').val(null);
            $('#years').val(null);
        }

        if((dropdownMonth >=0) && ((dropdownDay && dropdownYear) >0))
        {
            var dropdownDate = new Date(dropdownYear, dropdownMonth, dropdownDay);
            var ageInYears = ~~((Date.now() - dropdownDate)/(31557600000));
            console.log(ageInYears);
            // birthdate is not a date clear fields
            var nan = randomString(ageInYears);
            if (dropdownDate <= Date.now()) {
                $('#years').val(Math.floor(ageInYears));
            }

            $('#years').css('border', '');
            $('#age').val(dropdownDate.toYMD())
        }
    });

    $('#year').change(function (){
        var dropdownMonth = parseInt($('#month').val()-1);
        var dropdownDay = parseInt($('#day').val());
        var dropdownYear = parseInt($('#year').val());

        if((dropdownYear > 0))
        {
            $('#year').css('border', '');
        }
        else
        {
            $('#year').css('border-color','red');
            $('#years').css('border-color','red');
            $('#age').val(null);
            $('#years').val(null);
        }

        if((dropdownMonth >=0) && ((dropdownDay && dropdownYear) >0))
        {
            var dropdownDate = new Date(dropdownYear, dropdownMonth, dropdownDay);
            var ageInYears = ~~((Date.now() - dropdownDate)/(31557600000));
            console.log(ageInYears);
            // birthdate is not a date clear fields
            var nan = randomString(ageInYears);
            if (dropdownDate <= Date.now()) {
                $('#years').val(Math.floor(ageInYears));
            }
            $('#years').css('border', '');
            $('#age').val(dropdownDate.toYMD())
        }
    });

    $('#years').change(function () {
        $('#years').css('border', '');
        var checkYears = parseInt($('#years').val());
        // years in age not null calculate birthdate
        if ($('#years').val() && integerCheck(checkYears) == true) {
            var birthDate = new Date();
            birthDate.setFullYear(birthDate.getFullYear() - checkYears);
            var birthString = birthDate.toYMD();
            var nan = randomString(birthDate);
            if (nan == false) {
                $('#year').val(birthString.split('-')[0]);
                $('#month').val(birthString.split('-')[1]);
                $('#day').val(birthString.split('-')[2]);
                $('#age').val(birthString);
            }
        }
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

// Format date object as yyyy-MM-dd
(function () {
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

// reset border colors on input change
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
    if ($.trim($('#respirations').val().length) > 0 && integerCheck($('#respirations').val()) == false){
        $('#respirations').css('border-color', 'red');
        $('#errorMessageDecimal').removeClass('hidden');
    }
    else{
        $('#respirations').css('border-color', '');
        $('#errorMessageDecimal').addClass('hidden');
    }
});

$('#bloodPressureSystolic').change(function () {
    if ($.trim($('#bloodPressureSystolic').val().length) > 0 && integerCheck($('#bloodPressureSystolic').val()) == false){
        $('#bloodPressureSystolic').css('border-color', 'red');
        $('#errorMessageDecimal').removeClass('hidden');
    }
    else{
        $('#bloodPressureSystolic').css('border-color', '');
        $('#errorMessageDecimal').addClass('hidden');
    }
});

$('#bloodPressureDiastolic').change(function () {
    if ($.trim($('#bloodPressureDiastolic').val().length) > 0 && integerCheck($('#bloodPressureDiastolic').val()) == false){
        $('#bloodPressureDiastolic').css('border-color', 'red');
        $('#errorMessageDecimal').removeClass('hidden');
    }
    else{
        $('#bloodPressureDiastolic').css('border-color', '');
        $('#errorMessageDecimal').addClass('hidden');
    }
});

$('#heartRate').change(function () {
    if($.trim($('#heartRate').val().length) > 0 && integerCheck($('#heartRate').val()) == false){
        $('#heartRate').css('border-color', 'red');
        $('#errorMessageDecimal').removeClass('hidden');
    }
    else{
        $('#heartRate').css('border-color', '');
        $('#errorMessageDecimal').addClass('hidden');
    }
});

//$('#oxygen').change(function () {
//    if (decimalCheck($('#oxygen').val()) == false){
//        $('#oxygen').css('border-color', 'red');
//    }
//   else{
//       $('#oxygen').css('border-color', '');
//    }
//});

//$('#temperature').change(function () {
//    if (decimalCheck($('#temperature').val()) == false){
//        $('#temperature').css('border-color', 'red');
//    }
//    else{
//        $('#temperature').css('border-color', '');
//    }
//});

//$('#weight').change(function () {
//    if (decimalCheck($('#weight').val()) == false){
//        $('#weight').css('border-color', 'red');
//    }
//    else{
//        $('#weight').css('border-color', '');
//    }
//});

$('#heightFeet').change(function () {
    if ($.trim($('#heightFeet').val().length) > 0 && integerCheck($('#heightFeet').val()) == false){
        $('#heightFeet').css('border-color', 'red');
        $('#errorMessageHeight').removeClass('hidden');
    }
    else{
        $('#heightFeet').css('border-color', '');
        $('#errorMessageHeight').addClass('hidden');
    }
});

$('#heightInches').change(function () {
    if ($.trim($('#heightInches').val()) >= 12 || ($.trim($('#heightInches').val().length) > 0 && integerCheck($('#heightInches').val()) == false)){
        $('#heightInches').css('border-color', 'red');
        $('#errorMessageHeight').removeClass('hidden');
    }
    else{
        $('#heightInches').css('border-color', '');
        $('#errorMessageHeight').addClass('hidden');
    }
});

$('#weeksPregnant').change(function () {
    if (($.trim($('#weeksPregnant').val()) < 0) || ($.trim($('#weeksPregnant').val().length) > 0 && integerCheck($('#weeksPregnant').val()) == false))  {
        $('#weeksPregnant').css('border-color', 'red');
        $('#errorMessageDecimal').removeClass('hidden');
    }
    else{
        $('#weeksPregnant').css('border-color', '');
        $('#errorMessageDecimal').addClass('hidden');
    }
});

//$('#glucose').change(function () {
//    if (integerCheck($('#glucose').val()) == false){
//        $('#glucose').css('border-color', 'red');
//    }
//    else{
//        $('#glucose').css('border-color', '');
//    }
//})

//BMI auto- calculator
window.setInterval(function () {
    if ($('#heightFeet').val() && $('#weight').val()) {

        var weight_lbs = parseInt($('#weight').val());
        var height_in = parseInt($('#heightInches').val());
        var height_ft = parseInt($('#heightFeet').val());

        if (!$('#heightInches').val()) {
            height_in = 0;
        }

        height_in = height_in + height_ft * 12;

        $('#bmi').val(Math.round((weight_lbs / (height_in * height_in)) * 703));

    }
}, 500);

//Populate years drop down and just for DW max is 123yrs old LOL!
(function () {
    $('#year').append($('<option />').val(0).html("Year"));
    for (i = new Date().getFullYear(); i > 1889; i--)
    {
        $('#year').append($('<option />').val(i).html(i));
    }
})();

