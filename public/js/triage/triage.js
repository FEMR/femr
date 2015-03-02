var patientPhotoFeature = {

    config: {
        windowWidth: 250,
        windowHeight: 250,
        imageElement: $('#patientPhoto'),
        isNewPhoto: false,
        overrideIsDragging: false
    },

    photo: {
        x: 0,
        y: 0,
        width: 0,
        height: 0,
        imgData: null,
        deletePhoto: false,
        jWinCrop: null
    },

    init: function () {
        //destroy crop window if it already exists:
        var jwc = $(patientPhotoFeature.config.imageElement).getjWindowCrop();
        if (jwc != undefined)
            jwc.destroyCrop();

        //Create new crop window
       jwc = $(patientPhotoFeature.config.imageElement).jWindowCrop({
            targetWidth: patientPhotoFeature.config.windowWidth,
            targetHeight: patientPhotoFeature.config.windowHeight,
            loadingText: '',
            zoomInId: 'btnZoomIn',
            zoomOutId: 'btnZoomOut',

            onChange: function (result) {

                patientPhotoFeature.photo.x = result.cropX;
                patientPhotoFeature.photo.y = result.cropY;
                patientPhotoFeature.photo.width = result.cropW;
                patientPhotoFeature.photo.height = result.cropH;

                patientPhotoFeature.cropAndReplace();
            }
        });
        jwc.touchit(); //This invokes the "touchit" module which translates mouse/click
        //events into touch/drag events
        patientPhotoFeature.photo.jWinCrop = $(patientPhotoFeature.config.imageElement).getjWindowCrop();

        if ($(patientPhotoFeature.config.imageElement).attr('src') == "") {
            //If picture is empty, then let's hide the jCrop window
            //  and show the upload button instead
            $('#patientPhotoDiv').hide();
            $('#photoInputFormDiv').show();
            $(patientPhotoFeature.config.imageElement).show();
            /*
             No zooming allowed
             */
            $('#zoomControls').hide();
        }
        else {
            $('#patientPhotoDiv').show();
            $('#photoInputFormDiv').hide();
            $(patientPhotoFeature.config.imageElement).show();
            /*
             At the moment, we will only support
             the delete functionality if the photo was
             pulled down from the server, thus we will
             disable the zooming controls
             */
            $('#zoomControls').hide();
        }
    },
    resetFileUploadBox: function () {
        var control = $("#photoInput");
        control.replaceWith(control = control.clone(true));
    },
    flagForDeletion: function () {
        $(patientPhotoFeature.config.imageElement).attr('src', '');
        // _photoInit();
        patientPhotoFeature.init();
        //_photoResetFileUpload();
        patientPhotoFeature.resetFileUploadBox();
        patientPhotoFeature.photo.deletePhoto = true;
        $('#deletePhoto').prop('checked', true);  //set hidden element to true so that the image will be deleted server-side

    },
    loadPhotoIntoFrame: function (evt) {
        //  Load the photo into the image frame at which point the user
        //  may drag / crop the photo

        // Filelist (the files that have been selected by the user to be uploaded
        var files = evt.target.files;

        // Loop through the FileList and render image files as thumbnails.
        for (var i = 0, f; f = files[i]; i++) {
            // Only process image files.
            if (!f.type.match('image.*')) {
                continue;
            }

            //Older web browsers won't recognize FileReader
            if (typeof FileReader !== 'undefined') {
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
                patientPhotoFeature.config.isNewPhoto = true;
            }
            return;
        }

    },
    /**
     * Takes the photo from the canvas and crops it. Then sets it back on the canvas.
     * This helps reduce the amount of bandwidth, especially when a poor signal is detected.
     */
    cropAndReplace: function () {

        if (patientPhotoFeature.config.isNewPhoto === true) {
            var canvas = document.getElementById('patientPhotoCanvas'),
                context = canvas.getContext('2d');

            canvas.height = 255;
            canvas.width = 255;

            var img = document.getElementById('patientPhoto');

            var orientation = 1;
            EXIF.getData(img, function () {
                orientation = EXIF.getTag(this, "Orientation");
            });


            //sx, sy, sw, and sh identify the area of the photo that is going to
            //be cropped. This is because a user can drag the photo around and choose
            //which part they want to be saved.
            var sx = patientPhotoFeature.photo.x;
            var sy = patientPhotoFeature.photo.y;
            var sw = patientPhotoFeature.photo.width;
            var sh = patientPhotoFeature.photo.height;

            switch (orientation) {
                case 1:
                    //patientPhotoFeature.drawImageIOSFix(context, img, sx, sy, sw, sh, 0, 0, 255, 255);
                    if (sx >= 2) {
                        //fixes a bug where iOS devices will spit out a black image.
                        //This only happens when the user has scrolled all the way to
                        //the right, but sx is always subtracted by 2 (negative not allowed).
                        sx = sx - 2;
                    }
                    context.drawImage(img, sx, sy, sw, sh, 0, 0, 255, 255);

                    break;
                case 3:
                    //this occurs in iOS landscape mode, but only when the camera is on the left
                    context.translate(canvas.width, canvas.height);
                    context.rotate(180 * Math.PI / 180);
                    //get the width of the actual picture that was uploaded
                    var real_width = img.naturalWidth;
                    //get the width of the <img> html element
                    var imgElement_width = img.width;
                    var pixel_density_sx = real_width / imgElement_width;
                    //250 = jwc frame size
                    var max_sx = (imgElement_width - 250) * pixel_density_sx;
                    //a 180 degree rotation introduces a different spot for sx
                    sx = max_sx - sx;
                    if (sx < 0) {
                        //just in case the calculation is off by a small decimal
                        sx = 0;
                    }
                    if (sx >= 2) {
                        //fixes a bug where iOS devices will spit out a black image.
                        //This only happens when the user has scrolled all the way to
                        //the right, but sx is always subtracted by 2 (negative not allowed).
                        sx = sx - 2;
                    }
                    context.drawImage(img, sx, sy, sw, sh, 0, 0, 255, 255);
                    break;
                case 6:
                    patientPhotoFeature.config.overrideIsDragging = true;
                    //here you will find iOS in portrait mode with bizzare behavior,
                    //we use a special library to take care of the vertical squashing bug
                    var fileInput = document.getElementById('photoInput');
                    var file = fileInput.files[0];
                    // MegaPixImage constructor accepts File/Blob object.
                    var mpImg = new MegaPixImage(file);
                    //context.drawImage(img, sx, sy, sw, sh, 0, 0, 255, 255);
                    // Render resized image into canvas element.
                    mpImg.render(canvas, { width: 255, height: 255, orientation: 6, sx: sx, sy: sy });
                    break;
                case 8:
                    //this case occurs in android portrait mode and android isn't fucking retarded
                    context.translate(0, canvas.height);
                    context.rotate(270 * Math.PI / 180);
                    if (sx >= 2) {
                        //fixes a bug where iOS devices will spit out a black image.
                        //This only happens when the user has scrolled all the way to
                        //the right, but sx is always subtracted by 2 (negative not allowed).
                        sx = sx - 2;
                    }
                    context.drawImage(img, sx, sy, sw, sh, 0, 0, 255, 255);
                    break;
                default:
                    if (sx >= 2) {
                        //fixes a bug where iOS devices will spit out a black image.
                        //This only happens when the user has scrolled all the way to
                        //the right, but sx is always subtracted by 2 (negative not allowed).
                        sx = sx - 2;
                    }
                    context.drawImage(img, sx, sy, sw, sh, 0, 0, 255, 255);
            }
        }
    },
    prepareForPOST: function () {
        //0.5 is the quality downgrade.
        var canvas = document.getElementById('patientPhotoCanvas');
        if (!isCanvasBlank(canvas)){
            var dataURL = canvas.toDataURL("image/jpeg", 0.5);
            $('#photoInputCropped').val(dataURL);
            $('#photoInput').remove();//remove file upload from DOM so it's not submitted in POST
        }
    }

};

var multipleChiefComplaintFeature = {
    isActive: ($("#addChiefComplaint").length > 0),
    chiefComplaintsJSON: [],
    addChiefComplaintInput: function () {
        var value = triageFields.chiefComplaint.chiefComplaint.val().trim();
        //make sure a value exists in the chief complaint box and make sure it doesn't
        //already exist in the chief complaint array
        if (value === "" || $.inArray(value, multipleChiefComplaintFeature.chiefComplaintsJSON) > -1) {
            return;
        }
        //add to visual list
        triageFields.chiefComplaint.chiefComplaintList.append("<li><span class=removeChiefComplaint>X</span>" + value + "</li>");
        //add to JSON list
        multipleChiefComplaintFeature.chiefComplaintsJSON.push(value);
        //clear chief complaint textarea
        triageFields.chiefComplaint.chiefComplaint.val("");
        //bind chief complaint removal
        $('.removeChiefComplaint').unbind();
        $('.removeChiefComplaint').click(function (evt) {
            multipleChiefComplaintFeature.removeChiefComplaint(evt);
        });
    },
    removeChiefComplaint: function (evt) {
        var chiefComplaintText = $(evt.target).parent().text();
        chiefComplaintText = chiefComplaintText.substring(1, chiefComplaintText.length);
        var index = multipleChiefComplaintFeature.chiefComplaintsJSON.indexOf(chiefComplaintText);
        if (index > -1) {
            multipleChiefComplaintFeature.chiefComplaintsJSON.splice(index, 1);
        }
        $(evt.target).parent().remove();
    },
    JSONifyChiefComplaints: function () {
        var chiefComplaintBox = $('#chiefComplaint').val().trim();
        //add the value in the box currently (if it exists and if it doesn't already exist in the current list)
        if (chiefComplaintBox && $.inArray(chiefComplaintBox, multipleChiefComplaintFeature.chiefComplaintsJSON) === -1) {
            multipleChiefComplaintFeature.chiefComplaintsJSON.push(chiefComplaintBox);
        }
        $('input[name=chiefComplaintsJSON]').val(JSON.stringify(multipleChiefComplaintFeature.chiefComplaintsJSON));
    }
};

var triageFields = {
    patientInformation: {
        firstName: $('#firstName'),
        lastName: $('#lastName'),
        age: $('#age'),
        years: $('#years'),
        months: $('#months'),
        city: $('#city'),
        ageClassification: $('[name=ageClassification]')
    },
    patientVitals: {
        respiratoryRate: $('#respiratoryRate'),
        bloodPressureSystolic: $('#bloodPressureSystolic'),
        bloodPressureDiastolic: $('#bloodPressureDiastolic'),
        heartRate: $('#heartRate'),
        oxygenSaturation: $('#oxygenSaturation'),
        temperature: $('#temperature'),
        weight: $('#weight'),
        heightFeet: $('#heightFeet'),
        heightInches: $('#heightInches'),

        /*Alaa Serhan*/
        celsiusTemperature: $('#celsiusTemperature'),
        kgWeight: $('#kgWeight'),
        heightMeters: $('#heightMeters'),
        heightCentimeters: $('#heightCentimeters'),

        glucose: $('#glucose'),
        weeksPregnant: $('#weeksPregnant')
    },
    chiefComplaint: {

        chiefComplaint: $('#chiefComplaint'),
        chiefComplaintList: $('#chiefComplaintList')
    }
};

var birthdayAgeAutoCalculateFeature = {

    ageChangeCheck: function () {
        var patientInfo = triageFields.patientInformation;

        if (!patientInfo.years.val() && !patientInfo.months.val()) {
            patientInfo.age.val(null);
            patientInfo.years.css('border', '');
            patientInfo.months.css('border', '');
            return false;
        }
        var checkYears = Math.round(patientInfo.years.val());
        var checkMonths = Math.round(patientInfo.months.val());
        var pass = true;
        if (patientInfo.years.val()) {
            if (checkYears < 0 || isNaN(checkYears)) {
                patientInfo.years.css('border-color', 'red');
                patientInfo.age.val(null);
                pass = false;
            }
            else {
                patientInfo.years.val(checkYears);
                patientInfo.years.css('border', '');
            }
        }
        else {
            patientInfo.years.css('border', '');
        }
        if (patientInfo.months.val()) {
            if (checkMonths < 0 || isNaN(checkMonths)) {
                patientInfo.months.css('border-color', 'red');
                patientInfo.age.val(null);
                pass = false;
            }
            else {
                patientInfo.months.val(checkMonths);
                patientInfo.months.css('border', '');
            }
        }
        else {
            patientInfo.months.css('border', '');
        }
        return (pass)
    },
    calculateBirthdayFromAge: function () {
        var patientInfo = triageFields.patientInformation;

        var inputYears;
        var inputMonths;
        if (!patientInfo.years.val()) {
            inputYears = 0;
        }
        else {
            inputYears = $('#years').val();
        }
        if (!patientInfo.months.val()) {
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
        $('#weeksPregnant').attr('disabled', false);
    });
    $('#maleBtn').change(function () {
        $('#weeksPregnant').val('');
        $('#weeksPregnant').attr('disabled', true);
    });
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
        if (birthdayAgeAutoCalculateFeature.ageChangeCheck()) {
            var birthDate = birthdayAgeAutoCalculateFeature.calculateBirthdayFromAge();
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
        if (birthdayAgeAutoCalculateFeature.ageChangeCheck()) {
            var birthDate = birthdayAgeAutoCalculateFeature.calculateBirthdayFromAge();
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
    $('#btnDeletePhoto').click(function () {
        var b = confirm("Are you sure you would like to delete this photo?");
        if (b == true)
            patientPhotoFeature.flagForDeletion();
    });

    $('#triageSubmitBtn').click(function () {
        //get the base64 URI string from the canvas
        patientPhotoFeature.prepareForPOST();
        //make sure the feature is turned on before JSONifying
        if (multipleChiefComplaintFeature.isActive == true) {
            multipleChiefComplaintFeature.JSONifyChiefComplaints();
        }
        return validate(); //located in triageClientValidation.js
    });

    patientPhotoFeature.init();

    $('#photoInput').change(function (evt) {
        patientPhotoFeature.loadPhotoIntoFrame(evt);
    });

    $('#addChiefComplaint').click(function () {
        multipleChiefComplaintFeature.addChiefComplaintInput();
    });


});

function isCanvasBlank(canvas){
    var blank = document.createElement('canvas');
    blank.width = canvas.width;
    blank.height = canvas.height;

    return canvas.toDataURL() == blank.toDataURL();
}


$(function () {
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
});


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

/* Alaa Serhan */
/* BMI auto- calculator */
window.setInterval(function () {

    if ($('#heightMeters').val() && $('#kgWeight').val()) {
        var weight_kgs = parseInt($('#kgWeight').val());
        var height_cm = parseInt($('#heightCentimeters').val());
        var height_m = parseInt($('#heightMeters').val());

        if (!$('#heightCentimeters').val()) {
            height_cm = 0;
        }

        var total_height = height_m + "." + height_cm;
        $('#bmi').val(Math.round((weight_kgs / (total_height * total_height))));

    }

}, 500);


