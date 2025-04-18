//The following code disables using enter key on any form fields of input types text.
//This code below is also found in triage.js, medical.js, and pharmacy.js
window.addEventListener('keydown',function(e){
    if(e.code==='Enter'){
        if(e.target.nodeName==='INPUT' && (e.target.type==='text' || e.target.type==='number'
            || e.target.type==='checkbox'|| e.target.type==='tel' || e.target.type==='date'
            || e.target.type==='radio'))
        {
            e.preventDefault();
            return false;
        }
    }
},true);

const patientPhotoFeature = {

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
        let jwc = $(patientPhotoFeature.config.imageElement).getjWindowCrop();
        if (jwc !== undefined)
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

        if ($(patientPhotoFeature.config.imageElement).attr('src') === "") {
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
        let control = $("#photoInput");
        control.replaceWith(control.clone(true));
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
        const files = evt.target.files;

        // Loop through the FileList and render image files as thumbnails.
        for (const f of files) {
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
                reader.onload = (function () {
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

var diabeticScreeningFeature = {
    shouldPatientBeScreened: function () {
        if ($('#isDiabetesScreenSettingEnabled').val() === "true") {

            var patientVitals = triageFields.patientVitals;
            var bmiScore = $('#bmi').val();
            //checks to see if a systolic and/or diastolic blood pressure were taken then checks to see if they
            //surpass the threshold required for the diabetes prompt
            if (
                (patientVitals.bloodPressureSystolic.val() !== null && parseInt(patientVitals.bloodPressureSystolic.val()) >= 135)
                || (patientVitals.bloodPressureDiastolic.val() !== null && parseInt(patientVitals.bloodPressureDiastolic.val()) >= 80)
            ) {
                //checks if the patient is 18 or older
                return diabeticScreeningFeature.isAgeOrOlder(18);
            }
            //checks to see if a BMI score is available then checks to see if it
            //surpasses the threshold required for the diabetes prompt
            if (isFinite(bmiScore) && bmiScore !== null && bmiScore >= 25) {
                //checks if the patient is 25 or older
                return diabeticScreeningFeature.isAgeOrOlder(25);
            }
        }


        return false;
    },
    /**
     * checks to see if a patient is a specific age or older
     * @param age age of the patient
     * @returns {boolean} true if they are older than the age, false otherwise
     */
    isAgeOrOlder: function(age){
        if (!isNumeric(age)){
            return false;
        }
        const patientInfo = triageFields.patientInformation;
        let years = patientInfo.ageYears.val();

        const months = patientInfo.ageMonths.val();
        if (isNumeric(months)){
            years = years + months*12;
        }
        const ageClassification = $('input[name=ageClassification]:checked').val();
        if (ageClassification === "adult" || ageClassification === "elder"){
            return true;
        }
        return years >= age;

    },
    /**
     * Sets everything to read only while the user indicates whether or not
     * the patient was screened for diabetes.
     */
    readonlyEverything: function(){

        //disable general info except age classification and sex buttons
        //because they are stupid
        triageFields.patientInformation.firstName.prop('readonly', true);
        triageFields.patientInformation.lastName.prop('readonly', true);

        triageFields.patientInformation.address.prop('readonly', true);
        triageFields.patientInformation.phoneNumber.prop('readonly', true);
        triageFields.patientInformation.birthDate.prop('readonly', true);
        triageFields.patientInformation.ageYears.prop('readonly', true);
        triageFields.patientInformation.ageMonths.prop('readonly', true);
        triageFields.patientInformation.city.prop('readonly', true);
        //disable all vitals
        triageFields.patientVitals.respiratoryRate.prop('readonly', true);
        triageFields.patientVitals.bloodPressureSystolic.prop('readonly', true);
        triageFields.patientVitals.bloodPressureDiastolic.prop('readonly', true);
        triageFields.patientVitals.heartRate.prop('readonly', true);
        triageFields.patientVitals.oxygenSaturation.prop('readonly', true);
        triageFields.patientVitals.temperature.prop('readonly', true);
        triageFields.patientVitals.weight.prop('readonly', true);
        triageFields.patientVitals.heightFeet.prop('readonly', true);
        triageFields.patientVitals.heightInches.prop('readonly', true);
        triageFields.patientVitals.glucose.prop('readonly', false);
        triageFields.patientVitals.weeksPregnant.prop('readonly', true);
        //disable chief complaint
        triageFields.chiefComplaint.chiefComplaint.prop('readonly', true);
        //disable age classification, need to handle absence of POST data
        var value = $("[name=ageClassification]:checked").val();
        triageFields.patientInformation.ageClassification.prop('disabled', true);
        if (value){//checks to make sure there is a value to POST. If there isn't, don't post anything (normal behavior)
            triageFields.patientInformation.ageClassification.last().append("<input type='text' class='hidden' name='ageClassification' value='" + value + "'/>");
        }
        //disable sex buttons, since this is weird and added to the label we still get POST data
        triageFields.patientInformation.sex.parent().addClass('disabled');
        //disable photo, POST data still comes through for a photo. probably because of cropping!
        triageFields.patientInformation.photo.prop('disabled', true);

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
        $('.removeChiefComplaint').off().on('click', function (evt) {
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
        address: $('#address'),
        phoneNumber: $('#phoneNumber'),
        birthDate: $('#birthDateInput'), // Birthdate, seemingly doesn't work for existing patients?
        ageYears: $('#yearsInput'),
        ageMonths: $('#monthsInput'),
        ageClassification: $('[name=ageClassification]'),
        city: $('#city'),
        maleButton: $('#maleBtn'),
        femaleButton: $('#femaleBtn'),
        sex: $('[name=sex]'),
        photo: $('#photoInput')
    },
    patientVitals: {
        respiratoryRate: $('#respiratoryRate'),
        bloodPressureTitle: $('#bloodPressureTitle'),
        bloodPressureSystolic: $('#bloodPressureSystolic'),
        bloodPressureDiastolic: $('#bloodPressureDiastolic'),
        heartRate: $('#heartRate'),
        oxygenSaturation: $('#oxygenSaturation'),
        temperature: $('#temperature'),
        weight: $('#weight'),
        heightFeet: $('#heightFeet'),
        heightInches: $('#heightInches'),
        glucose: $('#glucose'),
        weeksPregnant: $('#weeksPregnant'),
        vitalUnits: $('#vitalUnits') /* Alaa Serhan - Metric BMI*/
    },
    chiefComplaint: {

        chiefComplaint: $('#chiefComplaint'),
        chiefComplaintList: $('#chiefComplaintList')
    }
};

const ageClassificationAutoCalculateFeature = {
    classSelection: function(patientAgeYears) {

        const patientAge = Number(patientAgeYears);

        if (patientAge == null) {
            return;
        }

        let valueToCheck = null;

        if (patientAge <= 1) {
            valueToCheck = "infant";
        } else if (patientAge <= 12) {
            valueToCheck = "child";
        } else if (patientAge <= 17) {
            valueToCheck = "teen";
        } else if (patientAge <= 64) {
            valueToCheck = "adult";
        } else if (patientAge <= 200) {
            valueToCheck = "elder";
        }

        if (valueToCheck) {
            $(`input[name="ageClassification"][value="${valueToCheck}"]`).prop("checked", true);
        }
    }
}



$("[name=ageClassification]").on('change', function () {
    // clear other fields since its an OR.
    const patientInfo = triageFields.patientInformation;
    patientInfo.birthDate.val(null);
    patientInfo.ageYears.val(null);
    patientInfo.ageMonths.val(null);
})

const birthdayAgeAutoCalculateFeature = {

    ageChangeCheck: function () {
        var patientInfo = triageFields.patientInformation;

        if (!patientInfo.ageYears.val() && !patientInfo.ageMonths.val()) {
            patientInfo.birthDate.val(null);
            patientInfo.ageYears.css('border', '');
            patientInfo.ageMonths.css('border', '');
            $('input[name="ageClassification"]').prop('checked', false);
            return false;
        }

        var checkYears = Math.round(patientInfo.ageYears.val());
        var checkMonths = Math.round(patientInfo.ageMonths.val());
        var pass = true;
        if (patientInfo.ageYears.val()) {
            if (checkYears < 0 || isNaN(checkYears)) {
                patientInfo.ageYears.css('border-color', 'red');
                patientInfo.age.val(null);
                pass = false;
            }
            else {
                patientInfo.ageYears.val(checkYears);
                patientInfo.ageYears.css('border', '');
            }
        }
        else {
            patientInfo.ageYears.css('border', '');
        }
        if (patientInfo.ageMonths.val()) {
            if (checkMonths < 0 || isNaN(checkMonths)) {
                patientInfo.ageMonths.css('border-color', 'red');
                patientInfo.birthDate.val(null);
                pass = false;
            }
            else {
                patientInfo.ageMonths.val(checkMonths).css('border', '');
            }
        }
        else {
            patientInfo.ageMonths.css('border', '');
        }
        return (pass)
    },
    calculateBirthdayFromAge: function () {
        const patientInfo = triageFields.patientInformation;

        let inputYears;
        let inputMonths;
        if (!patientInfo.ageYears.val()) {
            inputYears = 0;
        } else {
            inputYears = patientInfo.ageYears.val();
        }

        if (!patientInfo.ageMonths.val()) {
            inputMonths = 0;
        } else {
            inputMonths = patientInfo.ageMonths.val();
        }

        let birthDate = new Date();
        let birthMonth = birthDate.getMonth();
        let birthDay = birthDate.getDate();
        let birthYear = birthDate.getFullYear();
        if (birthMonth < inputMonths) {
            inputMonths = inputMonths - birthMonth - 1;
            birthMonth = 11;
            birthYear = birthYear - 1;
        }
        if (birthMonth < inputMonths) {
            const yearsFromMonths = Math.floor(inputMonths / 12);
            inputMonths = inputMonths - yearsFromMonths * 12;
            birthYear = birthYear - yearsFromMonths;
        }
        birthMonth = birthMonth - inputMonths;
        birthYear = birthYear - inputYears;
        if (birthDay === 31 && (birthMonth === 3 || birthMonth === 5 || birthMonth === 8 || birthMonth === 10)) {
            birthDay = 30;
        }
        else if (birthDay > 28 && birthMonth === 1) {
            if (birthYear % 400 === 0) {
                birthDay = 29;
            }
            else if (birthYear % 100 === 0) {
                birthDay = 28;
            }
            else if (birthYear % 4 === 0) {
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


$(function () {
    $('.newPatientBtn').on('click', function () {
        if (confirm("Are you sure you want to reset the fields?!")) {
            window.location = "/triage";
        }
    });

    $('#phoneNumber').keyup(function() {
        var numbers = $(this).val();
        $(this).val(numbers.replace(/\D/g, ''));
    });

    //gen info and vitals shit
    $('#femaleBtn').on('input', function () {
        $('#weeksPregnant').attr('disabled', 'false');

        // remove any errors
        $(this).parents(".generalInfoInput").removeClass("has-errors");
    });
    $('#maleBtn').on('change',function () {
        $('#weeksPregnant').val('').attr('disabled', true);

        // remove any errors
        $(this).parents(".generalInfoInput").removeClass("has-errors");
    });

    // Was previously #age which was incorrect for various reasons...
    // It is attempting to access the patient's date of birth, which is held in (#birthDateInput)
    $('#birthDateInput').on('change', function () {
        const ageElement = $(this)
        var inputYear = ageElement.val().toString().split('-')[0];
        var inputMonth = ageElement.val().toString().split('-')[1] - 1;
        var inputDay = ageElement.val().toString().split('-')[2];
        if ((inputMonth >= 0) && ((inputDay && inputYear) > 0)) {
            var inputDate = new Date(Number(inputYear), inputMonth, Number(inputDay));
            if (inputDate <= Date.now()) {
                const nowDate = new Date();
                const nowMonth = nowDate.getMonth();
                const nowDay = nowDate.getDate();
                const nowYear = nowDate.getFullYear();
                const diffMonth = nowMonth - inputMonth;
                const diffDay = nowDay - inputDay;
                const diffYear = nowYear - inputYear;

                let ageMonths = 12 * diffYear + diffMonth;
                if (diffDay < 0) {
                    ageMonths--;
                }
                $('#yearsInput').val(Math.floor(ageMonths / 12)).css('border', '');
                $('#monthsInput').val(ageMonths % 12).css('border', '');
                ageClassificationAutoCalculateFeature.classSelection(Math.floor(ageMonths / 12));
                $('#birthDateInput').css('border', '');
            } else {
                $('#birthDateInput').css('border-color', 'red');
                $('#yearsInput').val(null);
                $('#monthsInput').val(null);
            }
        } else {
            $('#birthDateInput').css('border', '');
            $('#yearsInput').val(null);
            $('#monthsInput').val(null);
        }
    });

    triageFields.patientInformation.ageYears.on('change', function () {
        if (birthdayAgeAutoCalculateFeature.ageChangeCheck()) {
            const birthDate = birthdayAgeAutoCalculateFeature.calculateBirthdayFromAge();
            const birthString = birthDate.toYMD();
            const nan = isNaN(Number(birthDate));
            if (nan === false) {
                triageFields.patientInformation.birthDate.val(birthString).css('border', '');

                ageClassificationAutoCalculateFeature.classSelection(triageFields.patientInformation.ageYears.val());
                triageFields.patientInformation.ageYears.css('border', '');
                triageFields.patientInformation.ageMonths.css('border', '');
            }
        }
    });
    triageFields.patientInformation.ageMonths.on('change', function () {
        if (birthdayAgeAutoCalculateFeature.ageChangeCheck()) {
            const birthDate = birthdayAgeAutoCalculateFeature.calculateBirthdayFromAge();
            const birthString = birthDate.toYMD();
            const nan = isNaN(Number(birthDate));
            if (nan === false) {
                triageFields.patientInformation.birthDate.val(birthString).css('border', '');
                triageFields.patientInformation.ageYears.css('border', '');
                triageFields.patientInformation.ageMonths.css('border', '');
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
        const b = confirm("Are you sure you would like to delete this photo?");
        if (b === true)
            patientPhotoFeature.flagForDeletion();
    });

    $('#triageSubmitBtn').on('click',function () {
        let pass = validatePatientInformation();

        //only prepare for POST if the fields are validated
        //also only do the diabetes prompt checking if the fields are validated
        if (pass) {
            //get the base64 URI string from the canvas
            patientPhotoFeature.prepareForPOST();
            //make sure the feature is turned on before JSONifying
            if (multipleChiefComplaintFeature.isActive === true) {
                multipleChiefComplaintFeature.JSONifyChiefComplaints();
            }

            const isDiabeticScreeningPromptNecessary = diabeticScreeningFeature.shouldPatientBeScreened();
            if (isDiabeticScreeningPromptNecessary) {
                const diabetesDialog = $('.submitResetWrap.hidden');
                const submitMenu = $('.submitResetWrap').not('.hidden');
                $(submitMenu).addClass('hidden');
                $(diabetesDialog).removeClass('hidden');
                diabeticScreeningFeature.readonlyEverything();
            } else {
                checkIfDuplicatePatientMatch();
            }

            pass = !isDiabeticScreeningPromptNecessary;
        }
        return pass; //located in triageClientValidation.js
    });


    $('#yesDiabetesScreen').on('click', function () {
        $('input[name=isDiabetesScreenPerformed]').val("true");
        checkIfDuplicatePatientMatch();
    })


    $('#noDiabetesScreen').on('click', function(){
        $('input[name=isDiabetesScreenPerformed]').val("false");
        checkIfDuplicatePatientMatch();
    })

    function checkIfDuplicatePatientMatch() {
        const patientInfo = triageFields.patientInformation;
        const first = patientInfo.firstName.val();
        const last = patientInfo.lastName.val();
        const phone = patientInfo.phoneNumber.val();
        const addr = patientInfo.address.val();
        //age col in patients table stores a bday, using age var to keep consistent naming conventions, passing date as long to router
        const age = birthdayAgeAutoCalculateFeature.calculateBirthdayFromAge().valueOf();
        const gender = patientInfo.sex.val();
        const city = patientInfo.city.val();
        const ageClassification = $("[name=ageClassification]:checked").val();

        const url = "/search/dupPatient/findMatch";
        const patientId = $("#patientId").val();

        let queryParams;
        if(ageClassification != null) {
            queryParams = {
                first: first,
                last: last,
                phone: phone,
                addr: addr,
                gender: gender,
                city: city
            }
        } else {
            queryParams = {
                first: first,
                last: last,
                phone: phone,
                addr: addr,
                age: age,
                gender: gender,
                city: city
            }
        }


        $.getJSON(url, queryParams,function (result) {
            if (result === true) {
                if(!(patientId > 0)) {
                    if (confirm("A patient with similar information already exists in the database. Would you like to view the matching patients?")) {
                        var patientMatchesUrl;
                        if(ageClassification != null) {
                            patientMatchesUrl = "/history/patient/withMatches/p?first=" + first + "&last=" + last
                                + "&phone=" + phone + "&addr=" + addr + "&gender=" + gender + "&city=" + city;
                        } else {
                            patientMatchesUrl = "/history/patient/withMatches/p?first=" + first + "&last=" + last
                                + "&phone=" + phone + "&addr=" + addr + "&age=" + age + "&gender=" + gender + "&city=" + city;
                        }
                        window.location.replace(patientMatchesUrl);
                    }
                }
            }
        })
    }

    patientPhotoFeature.init();

    $('#photoInput').on('change', function (evt) {
        patientPhotoFeature.loadPhotoIntoFrame(evt);
    });

    $('#addChiefComplaint').on('click', function () {
        multipleChiefComplaintFeature.addChiefComplaintInput();
    });
    //AJ Saclayan City Typeahead
    /* Search typeahead */
    if ($("#citySearchContainer").length > 0) {

        var city_data = [];

        // Get Patients from server
        $.getJSON("/search/typeahead/cities", function (data) {

            city_data = data;

            var mission_cities = new Bloodhound({

                datumTokenizer: function (d) {

                    // break apart first/last name into separate words
                    var words = Bloodhound.tokenizers.whitespace(d.name); //(d.id + " " + d.firstName + " " + d.lastName)

                    // make all possible substring words
                    // Original Word: Name
                    // Add  ame, me, e to the list also
                    $.each(words, function (k, v) {
                        var i = 0;
                        while ((i + 1) < v.length) {
                            words.push(v.substr(i, v.length));
                            i++;
                        }
                    });

                    return words;
                },
                queryTokenizer: Bloodhound.tokenizers.whitespace,
                local: city_data, //patient_data
                limit: 30
            });
            mission_cities.initialize(); //patients.initialize

            var typeahead_options = {

                highlight: true
            };

            //initalize typeahead
            $("#citySearchContainer").find(".citySearch").typeahead(typeahead_options, {
                name: 'mission_cities',
                displayKey: 'name',
                source: mission_cities.ttAdapter(),
                matcher: function (item) {
                    if (item.toLowerCase().indexOf(this.query.trim().toLowerCase()) !== -1) {
                        return true;
                    }
                },
                templates: {
                    empty: [
                        '<div class="emptyMessage">No matching cities found</div>'
                    ]
                }
            }).on('typeahead:selected', function(event, item) {
                // triggered when an item is selected from the dropdown list in autocompleted
                const $cityName = $(this).closest(".cityRow").find(".cityName");
                $cityName.val(item.id);
            }).on('typeahead:autocompleted', function(event, item) {
                    // triggered when an item is tabbed to completion
                    $(this).trigger("typeahead:selected", item);
                }
            ).on("change", function() {
                    // triggered when text is entered that is not part of the autocomplete
                    const $cityName = $(this).closest(".cityRow").find(".cityID");
                    $cityName.val("");
            });

            // Reenable search input field
            $("input.citySearch").removeClass("loading")
                .removeAttr("disabled")
                .attr("placeholder", "City");

            //$("input.citySearch").removeClass("loading")
            //    .removeAttr("disabled")
            //    .attr("placeholder", "Patient ID or Name");

        });
    }
   //citiesFeature.initializeCitiesTypeahead().then(function() {
   //     citiesFeature.addCitiesTypeahead();
   // });
   //
   // ///AJ Saclayan Cities Suggestion
   // typeaheadFeature.setGlobalVariableAndInitalize("/search/typeahead/cities", cityFeature.newProblems.first(),'name',true,true);
   // //typeaheadFeature.setGlobalVariableAndInitalize("/search/typeahead/diagnoses", problemFeature.newProblems.first(), 'diagnoses', true, true);

});

function isCanvasBlank(canvas){
    const blank = document.createElement('canvas');
    blank.width = canvas.width;
    blank.height = canvas.height;

    return canvas.toDataURL() === blank.toDataURL();
}


$(function () {
    Date.prototype.toYMD = function Date_toYMD() {
        let year, month, day;
        year = String(this.getFullYear());
        month = String(this.getMonth() + 1);
        if (month.length === 1) {
            month = "0" + month;
        }
        day = String(this.getDate());
        if (day.length === 1) {
            day = "0" + day;
        }
        return year + "-" + month + "-" + day;
    }
});


/* BMI auto- calculator */
function calculateBMI() {
    if (triageFields.patientVitals.heightFeet.val() && triageFields.patientVitals.weight.val()) {
        const vitalsUnits = $('#vitalsUnits').val(); /* Alaa Serhan */
        const weight = parseInt(triageFields.patientVitals.weight.val());
        let height_in = parseInt(triageFields.patientVitals.heightInches.val());
        const height_ft = parseInt(triageFields.patientVitals.heightFeet.val());

        if (!triageFields.patientVitals.heightInches.val()) {
            height_in = 0;
        }

        let bmiScore;
        if (vitalsUnits === "metric") {
            // Get total height in meters from separate meters, centimeters
            const heightMeters = (height_ft * 100 + height_in) / 100;
            // Calculate BMI (Metric)
            bmiScore = calculateBMIScore("metric", weight, heightMeters);
        } else {
            // Get total height in inches
            let totalInches = height_in + height_ft * 12;
            // Calculate BMI (Imperial)
            bmiScore = calculateBMIScore("standard", weight, totalInches);
        }
        // Set the BMI value or clear it if the score is null
        $('#bmi').val(bmiScore !== null ? bmiScore : '');
    }
    else {
        $('#bmi').val('');
    }
}

// Attach event listeners to height and weight input fields
$('#heightFeet, #heightInches, #weight').on('input change', calculateBMI);

$("#heightInches").change(function(){
    var isMetric = ($("#vitalsUnits").val() === "metric");

    var heightFeet = parseFloat(triageFields.patientVitals.heightFeet.val()) || 0;
    var heightInches = parseFloat(triageFields.patientVitals.heightInches.val()) || 0;

    var unitValue = isMetric ? 100 : 12;

    // if inches > 12 or 100 add to feet
    if( heightInches > unitValue ){
        heightFeet += Math.floor(heightInches/unitValue);
        heightInches = heightInches % unitValue;
    }

    $(triageFields.patientVitals.heightFeet).val(heightFeet || "");
    $(triageFields.patientVitals.heightInches).val(heightInches || "");
});



