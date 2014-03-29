var _imgInfo = {
    x: 0,
    y: 0,
    width: 0,
    height: 0,
    imgData: null,
    deletePhoto: false,
    jWinCrop: null
};

$(document).ready(function () {

    $('.newPatientBtn').click(function () {
        if (confirm("Are you sure you want to reset the fields?")) {
            window.location = "/triage";
        }
        else {
            return;
        }
    });
    //gen info and vitals shit
    $('#femaleBtn').change(function () {
        $('#pregnancyBtn').removeClass('disabled');
    });
    $('#maleBtn').change(function () {
        $('#pregnancyBtn').removeClass('active');
        $('#pregnancyBtn').addClass('disabled');
        $('#weeksPregnant').val('');
        $('#weeksPregnant').attr('disabled', 'disabled');
    });
    $('#pregnancyBtn').change(function () {
        if (typeof $("#weeksPregnant").attr('disabled') === "undefined") {
            $('#weeksPregnant').val('');
            $('#weeksPregnant').attr('disabled', 'disabled');
        }
        else {
            $('#weeksPregnant').removeAttr('disabled');
        }


    })
    //birthday shit
    $('#age').change(function () {
        var inputYear = $('#age').val().split('-')[0];
        var inputMonth = $('#age').val().split('-')[1] - 1;
        var inputDay = $('#age').val().split('-')[2];
        if ((inputMonth >= 0) && ((inputDay && inputYear) > 0)) {
            var inputDate = new Date(inputYear, inputMonth, inputDay);
            if (inputDate <= Date.now()) {
                var nowDate = new Date();
                var nowMonth = nowDate.getMonth();
                var nowDay = nowDate.getDate();
                var nowYear = nowDate.getFullYear();
                var diffMonth = nowMonth - inputMonth;
                var diffDay = nowDay - inputDay;
                var diffYear = nowYear - inputYear;
                var ageMonths = 12 * diffYear + diffMonth;
                if (diffDay < 0) {
                    ageMonths--;
                }
                $('#years').val(Math.floor(ageMonths / 12));
                $('#months').val(ageMonths % 12);
                $('#years').css('border', '');
                $('#months').css('border', '');
                $('#age').css('border', '');
            }
            else {
                $('#age').css('border-color', 'red');
                $('#years').val(null);
                $('#months').val(null);
            }
        }
        else {
            $('#age').css('border', '');
            $('#years').val(null);
            $('#months').val(null);
        }
    });
    $('#years').change(function () {
        if (_ageChangeCheck()) {
            var birthDate = _calcBirthdateFromAge();
            var birthString = birthDate.toYMD();
            var nan = randomString(birthDate);
            if (nan == false) {
                $('#age').val(birthString);
                $('#years').css('border', '');
                $('#months').css('border', '');
                $('#age').css('border', '');
            }
        }
    });
    $('#months').change(function () {
        if (_ageChangeCheck()) {
            var birthDate = _calcBirthdateFromAge();
            var birthString = birthDate.toYMD();
            var nan = randomString(birthDate);
            if (nan == false) {
                $('#age').val(birthString);
                $('#years').css('border', '');
                $('#months').css('border', '');
                $('#age').css('border', '');
            }
        }
    });
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


    //PHOTO LOGIC ::START::  =~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~
    //Kick off jWindowCrop library
    //  This also keeps the _imgInfo object up to date
    _photoInit();
    //When the user chooses a photo, this function will
    //  Load the photo into the image frame at which point the user
    //  may drag / crop the photo
    $('#photoInput').change(function (evt) {
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
            reader.onload = (function (theFile) {
                return function (e) {
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
    $('#btnDeletePhoto').click(function () {
        var b = confirm("Are you sure you would like to delete this photo?");
        if (b == true)
            _photoDeleteImage();
    });
    //PHOTO LOGIC ::END::  =~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~

});


/* Photo shit */
//Handle photo delete operation
function _photoDeleteImage() {
    $('#patientPhoto').attr('src', '');
    _photoInit();
    _photoResetFileUpload();
    _imgInfo.deletePhoto = true;
    $('#deletePhoto').prop('checked', true);  //set hidden element to true so that the image will be deleted server-side
}
//Initialize controls, shows or hides
//  patient photo or upload control depending on if image
//  has been loaded already
function _photoInit() {
    //destroy crop window if it already exists:
    var jwc = $('#patientPhoto').getjWindowCrop();
    if (jwc != undefined)
        jwc.destroyCrop();


    //Create new crop window
    jwc = $('#patientPhoto').jWindowCrop({
        targetWidth: 250,
        targetHeight: 250,
        loadingText: '',
        zoomInId: 'btnZoomIn',
        zoomOutId: 'btnZoomOut',

        onChange: function (result) {
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

    if ($('#patientPhoto').attr('src') == "") {
        //If picture is empty, then let's hide the jCrop window
        //  and show the upload button instead
        $('#patientPhotoDiv').hide();
        $('#photoInputFormDiv').show();
    }
    else {
        $('#patientPhotoDiv').show();
        $('#photoInputFormDiv').hide();
        /*
         At the moment, we will only support
         the delete functionality if the photo was
         pulled down from the server, thus we will
         disable the zooming controls
         */
        $('#zoomControls').hide();
    }
}
//Reset the file upload box
function _photoResetFileUpload() {
    var control = $("#photoInput");
    control.replaceWith(control = control.clone(true));
}

/* BMI auto- calculator */
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

/* Birthday shit */
function _ageChangeCheck() {
    if (!$('#years').val() && !$('#months').val()) {
        $('#age').val(null);
        $('#years').css('border', '');
        $('#months').css('border', '');
        return false;
    }
    var checkYears = Math.round($('#years').val());
    var checkMonths = Math.round($('#months').val());
    var pass = true;
    if ($('#years').val()) {
        if (checkYears < 0 || isNaN(checkYears)) {
            $('#years').css('border-color', 'red');
            $('#age').val(null);
            pass = false;
        }
        else {
            $('#years').val(checkYears);
            $('#years').css('border', '');
        }
    }
    else {
        $('#years').css('border', '');
    }
    if ($('#months').val()) {
        if (checkMonths < 0 || isNaN(checkMonths)) {
            $('#months').css('border-color', 'red');
            $('#age').val(null);
            pass = false;
        }
        else {
            $('#months').val(checkMonths);
            $('#months').css('border', '');
        }
    }
    else {
        $('#months').css('border', '');
    }
    return (pass)
}
function _calcBirthdateFromAge() {
    var inputYears;
    var inputMonths;
    if (!$('#years').val()) {
        inputYears = 0;
    }
    else {
        inputYears = $('#years').val();
    }
    if (!$('#months').val()) {
        inputMonths = 0;
    }
    else {
        inputMonths = $('#months').val();
    }
    var birthDate = new Date();
    var birthMonth = birthDate.getMonth();
    var birthDay = birthDate.getDate();
    var birthYear = birthDate.getFullYear();
    if (birthMonth < inputMonths) {
        inputMonths = inputMonths - birthMonth - 1;
        birthMonth = 11;
        birthYear = birthYear - 1;
    }
    if (birthMonth < inputMonths) {
        var yearsFromMonths = Math.floor(inputMonths / 12);
        inputMonths = inputMonths - yearsFromMonths * 12;
        birthYear = birthYear - yearsFromMonths;
    }
    birthMonth = birthMonth - inputMonths;
    birthYear = birthYear - inputYears;
    if (birthDay == 31 && (birthMonth == 3 || birthMonth == 5 || birthMonth == 8 || birthMonth == 10)) {
        birthDay = 30;
    }
    else if (birthDay > 28 && birthMonth == 1) {
        if (birthYear % 400 == 0) {
            birthDay = 29;
        }
        else if (birthYear % 100 == 0) {
            birthDay = 28;
        }
        else if (birthYear % 4 == 0) {
            birthDay = 29;
        }
        else {
            birthDay = 28;
        }
    }
    birthDate.setFullYear(birthYear, birthMonth, birthDay);
    return birthDate;
}

