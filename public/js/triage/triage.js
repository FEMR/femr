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

        if((dropdownMonth >= 0)) {
            $('#month').css('border', '');
        }
        else {
            $('#month').css('border-color', 'red');
            $('#day').css('border-color', 'red');
            $('#year').css('border-color', 'red');
            $('#years').css('border-color','red');
            $('#months').css('border-color','red');
            $('#age').val(null);
            $('#years').val(null);
            $('#months').val(null);
        }

        if((dropdownMonth >=0) && ((dropdownDay && dropdownYear) >0)) {
            var dropdownDate = new Date(dropdownYear, dropdownMonth, dropdownDay);
            if (dropdownDate <= Date.now()) {
                var nowDate = new Date();
                var nowMonth = nowDate.getMonth();
                var nowDay = nowDate.getDate();
                var nowYear = nowDate.getFullYear();
                var diffMonth = nowMonth - dropdownMonth;
                var diffDay = nowDay - dropdownDay;
                var diffYear = nowYear - dropdownYear;
                var ageMonths = 12 * diffYear + diffMonth;
                if (diffDay < 0) {
                    ageMonths--;
                }
                $('#years').val(Math.floor(ageMonths/12));
                $('#months').val(ageMonths%12);
                $('#years').css('border', '');
                $('#months').css('border', '');
                $('#month').css('border', '');
                $('#day').css('border', '');
                $('#year').css('border', '');
                $('#age').val(dropdownDate.toYMD());
            }
            else {
                $('#month').css('border-color', 'red');
                $('#day').css('border-color', 'red');
                $('#year').css('border-color', 'red');
                $('#years').css('border-color','red');
                $('#months').css('border-color','red');
                $('#age').val(null);
                $('#years').val(null);
                $('#months').val(null);
            }
        }
    });

    $('#day').change(function (){
        var dropdownMonth = parseInt($('#month').val()-1);
        var dropdownDay = parseInt($('#day').val());
        var dropdownYear = parseInt($('#year').val());

        if((dropdownDay > 0)) {
            $('#day').css('border', '');
        }
        else {
            $('#month').css('border-color', 'red');
            $('#day').css('border-color', 'red');
            $('#year').css('border-color', 'red');
            $('#years').css('border-color','red');
            $('#months').css('border-color','red');
            $('#age').val(null);
            $('#years').val(null);
            $('#months').val(null);
        }

        if((dropdownMonth >=0) && ((dropdownDay && dropdownYear) >0))
        {
            var dropdownDate = new Date(dropdownYear, dropdownMonth, dropdownDay);
            if (dropdownDate <= Date.now()) {
                var nowDate = new Date();
                var nowMonth = nowDate.getMonth();
                var nowDay = nowDate.getDate();
                var nowYear = nowDate.getFullYear();
                var diffMonth = nowMonth - dropdownMonth;
                var diffDay = nowDay - dropdownDay;
                var diffYear = nowYear - dropdownYear;
                var ageMonths = 12 * diffYear + diffMonth;
                if (diffDay < 0) {
                    ageMonths--;
                }
                $('#years').val(Math.floor(ageMonths/12));
                $('#months').val(ageMonths%12);
                $('#years').css('border', '');
                $('#months').css('border', '');
                $('#month').css('border', '');
                $('#day').css('border', '');
                $('#year').css('border', '');
                $('#age').val(dropdownDate.toYMD());
            }
            else {
                $('#month').css('border-color', 'red');
                $('#day').css('border-color', 'red');
                $('#year').css('border-color', 'red');
                $('#years').css('border-color','red');
                $('#months').css('border-color','red');
                $('#age').val(null);
                $('#years').val(null);
                $('#months').val(null);
            }
        }
    });

    $('#year').change(function (){
        var dropdownMonth = parseInt($('#month').val()-1);
        var dropdownDay = parseInt($('#day').val());
        var dropdownYear = parseInt($('#year').val());

        if((dropdownYear > 0)) {
            $('#year').css('border', '');
        }
        else {
            $('#month').css('border-color', 'red');
            $('#day').css('border-color', 'red');
            $('#year').css('border-color', 'red');
            $('#years').css('border-color','red');
            $('#months').css('border-color','red');
            $('#age').val(null);
            $('#years').val(null);
            $('#months').val(null);
        }

        if((dropdownMonth >=0) && ((dropdownDay && dropdownYear) >0)) {
            var dropdownDate = new Date(dropdownYear, dropdownMonth, dropdownDay);
            if (dropdownDate <= Date.now()) {
                var nowDate = new Date();
                var nowMonth = nowDate.getMonth();
                var nowDay = nowDate.getDate();
                var nowYear = nowDate.getFullYear();
                var diffMonth = nowMonth - dropdownMonth;
                var diffDay = nowDay - dropdownDay;
                var diffYear = nowYear - dropdownYear;
                var ageMonths = 12 * diffYear + diffMonth;
                if (diffDay < 0) {
                    ageMonths--;
                }
                $('#years').val(Math.floor(ageMonths/12));
                $('#months').val(ageMonths%12);
                $('#years').css('border', '');
                $('#months').css('border', '');
                $('#month').css('border', '');
                $('#day').css('border', '');
                $('#year').css('border', '');
                $('#age').val(dropdownDate.toYMD());
            }
            else {
                $('#month').css('border-color', 'red');
                $('#day').css('border-color', 'red');
                $('#year').css('border-color', 'red');
                $('#years').css('border-color','red');
                $('#months').css('border-color','red');
                $('#age').val(null);
                $('#years').val(null);
                $('#months').val(null);
            }
        }
    });

    $('#years').change(function () {
        $('#years').css('border', '');
        if (!$('#years').val()) {
            $('#years').val(0);
        }
        if (!$('#months').val()) {
            $('#months').val(0);
        }
        var checkYears = parseInt($('#years').val());
        var checkMonths = parseInt($('#months').val());
        // years in age not null calculate birthdate
        if (integerCheck(checkYears) == true && integerCheck(checkMonths) == true) {
            var birthDate = new Date();
            if (checkMonths == 0) {
                birthDate.setFullYear(birthDate.getFullYear() - checkYears);
            }
            else {
                while (birthDate.getMonth() - checkMonths < 0) {
                    checkMonths = checkMonths - birthDate.getMonth() - 1;
                    birthDate.setMonth(11);
                    birthDate.setFullYear(birthDate.getFullYear() - 1);
                }
                birthDate.setMonth(birthDate.getMonth() - checkMonths)
            }
            var birthString = birthDate.toYMD();
            var nan = randomString(birthDate);
            if (nan == false) {
                $('#year').val(parseInt(birthString.split('-')[0]));
                $('#month').val(parseInt(birthString.split('-')[1]));
                $('#day').val(parseInt(birthString.split('-')[2]));
                $('#age').val(birthString);
                $('#years').css('border', '');
                $('#months').css('border', '');
                $('#month').css('border', '');
                $('#day').css('border', '');
                $('#year').css('border', '');
            }
        }
    });

    $('#months').change(function () {
        $('#months').css('border', '');
        if (!$('#months').val()) {
            $('#months').val(0);
        }
        if (!$('#years').val()) {
            $('#years').val(0);
        }
        var checkMonths = parseInt($('#months').val());
        var checkYears = parseInt($('#years').val());
        // years in age not null calculate birthdate
        if (integerCheck(checkMonths) == true && integerCheck(checkYears) == true) {
            var birthDate = new Date();
            if (checkMonths == 0) {
                birthDate.setFullYear(birthDate.getFullYear() - checkYears);
            }
            else {
                while (birthDate.getMonth() - checkMonths < 0) {
                    checkMonths = checkMonths - birthDate.getMonth() - 1;
                    birthDate.setMonth(11);
                    birthDate.setFullYear(birthDate.getFullYear() - 1);
                }
                birthDate.setMonth(birthDate.getMonth() - checkMonths)
            }
            birthDate.setFullYear(birthDate.getFullYear() - checkYears);
            var birthString = birthDate.toYMD();
            var nan = randomString(birthDate);
            if (nan == false) {
                $('#year').val(parseInt(birthString.split('-')[0]));
                $('#month').val(parseInt(birthString.split('-')[1]));
                $('#day').val(parseInt(birthString.split('-')[2]));
                $('#age').val(birthString);
                $('#years').css('border', '');
                $('#months').css('border', '');
                $('#month').css('border', '');
                $('#day').css('border', '');
                $('#year').css('border', '');
            }
        }
    });

    //PHOTO LOGIC ::START::  =~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~
    var _imgInfo = {
        x: 0,
        y: 0,
        width: 0,
        height: 0,
        imgData: null,
        deletePhoto: false,
        jWinCrop: null
    };

    //Initialize controls, shows or hides
    //  patient photo or upload control depending on if image
    //  has been loaded already
    function _photoInit()
    {
        //destroy crop window if it already exists:
        var jwc = $('#patientPhoto').getjWindowCrop();
        if(jwc != undefined)
            jwc.destroyCrop();


        //Create new crop window
        jwc = $('#patientPhoto').jWindowCrop({
            targetWidth: 250,
            targetHeight: 250,
            loadingText: '',
            zoomInId: 'btnZoomIn',
            zoomOutId: 'btnZoomOut',

            onChange: function(result) {
                _imgInfo.x = result.cropX;
                _imgInfo.y = result.cropY;
                _imgInfo.width = result.cropW;
                _imgInfo.height = result.cropH;
                $('#imageCoords').val("{" + result.cropX + "," + result.cropY +
                                      "," + result.cropW + "," + result.cropH + "}");
                var temp = $('#imageCoords').val();
            }
        });
        jwc.touchit(); //This invokes the "touchit" module which translates mouse/click
        //events into touch/drag events
        _imgInfo.jWinCrop = $('#patientPhoto').getjWindowCrop();

        if($('#patientPhoto').attr('src') == "")
        {
            //If picture is empty, then let's hide the jCrop window
            //  and show the upload button instead
            $('#patientPhotoDiv').hide();
            $('#photoInputFormDiv').show();
        }
        else
        {
            $('#patientPhotoDiv').show();
            $('#photoInputFormDiv').hide();
        }
    }

    //Handle photo delete operation
    function _photoDeleteImage()
    {
        $('#patientPhoto').attr('src', '');
        _photoInit();
        _photoResetFileUpload();
        _imgInfo.deletePhoto = true;
        $('#deletePhoto').prop('checked', true);  //set hidden element to true so that the image will be deleted server-side
    }

    //Reset the file upload box
    function _photoResetFileUpload()
    {
        var control = $("#photoInput");
        control.replaceWith( control = control.clone( true ) );
    }

    //Kick off jWindowCrop library
    //  This also keeps the _imgInfo object up to date
    $(function() {
      _photoInit();
    });

    //Crops photo within jCropWindow frame and
    //  saves data to fileupload element
    function PrepPatientPhotoForUpload()
    {
        //_imgInfo.jWinCrop.set
        //var cnvs = document.createElement('canvas');
        //_imgInfo.deletePhoto = true;
        //$('#patientPhoto').attr("src", "");
        //_imgInfo.jWinCrop.destroyCrop();

        /*
         var cnvs = document.getElementById('testCanvas');
         cnvs.setAttribute("width", _imgInfo.width);
         cnvs.setAttribute("height", _imgInfo.height);
         cnvs.setAttribute("id","photoCanvas");
         var ctx = cnvs.getContext("2d");
         var img = new Image();
         img.onload = function() {
         ctx.drawImage(img, _imgInfo.x, _imgInfo.y, _imgInfo.width, _imgInfo.height, 0, 0, _imgInfo.width, _imgInfo.height);
         };
         img.src = $('#patientPhoto').attr('src');*/
    }



    //When the user chooses a photo, this function will
    //  Load the photo into the image frame at which point the user
    //  may drag / crop the photo
    $('#photoInput').change(function(evt) {
        var files = evt.target.files; // FileList object
        // Loop through the FileList and render image files as thumbnails.
        for (var i = 0, f; f = files[i]; i++) {

            // Only process image files.
            if (!f.type.match('image.*')) {
                continue;
            }

            $('#patientPhotoDiv').show();
            $('#photoInputFormDiv').hide();

            var reader = new FileReader();

            // Closure to capture the file information.
            reader.onload = (function(theFile) {
                return function(e) {
                    $('#patientPhoto').attr('src', e.target.result);
                };
            })(f);

            // Read in the image file as a data URL.
            reader.readAsDataURL(f);
            return;
        }

    });


    //Notes:  on post, I can create a temporary canvas, crop the image,
    // and then place data into upload section
    $('#btnDeletePhoto').click(function() {
        var b = confirm("Are you sure you would like to delete this photo?");
        if(b == true)
            _photoDeleteImage();
    });

    //PHOTO LOGIC ::END::  =~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~
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

