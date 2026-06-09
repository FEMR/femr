//The following code disables using enter key on any form fields of input types text.
//This code below is also found in triage.js, medical.js, and pharmacy.js
window.addEventListener('keydown',function(e){
    if(e.keyIdentifier=='U+000A'||e.keyIdentifier=='Enter'||e.keyCode==13){
        if(e.target.nodeName=='INPUT' && (e.target.type=='text' || e.target.type=='number'
            || e.target.type=='checkbox'|| e.target.type=='tel' || e.target.type=='date'
            || e.target.type=='radio'))
        {
            if ($(e.target).closest('.femr-sidebar__search').length || $(e.target).is('input[name="patientSearchQuery"]')) {
                return true;
            }
            e.preventDefault();
            return false;
        }
    }
},true);

var problemFeature = {
    allProblems: $('.newProblems, .oldProblems'),
    newProblems: $('input[name].newProblems'),
    refreshSelectors: function () {
        problemFeature.allProblems = $(problemFeature.allProblems.selector);
        problemFeature.newProblems = $(problemFeature.newProblems.selector);
    },
    getNumberOfNonReadonlyProblemFields: function () {
        problemFeature.refreshSelectors();
        return problemFeature.newProblems.length;
    },
    addProblemField: function () {
        var problemIndex = problemFeature.getNumberOfNonReadonlyProblemFields();
        $('.problemWrap .femr-diagnosis-actions')
            .before("<div class='femr-diagnosis-item problem'>" +
            "<div class='femr-diagnosis-row'>" +
            "<input name='problems[" + problemIndex + "].name' type='text' class='form-control newProblems'/>" +
            "<button class='btn addSubtractBtn femr-icon-button femr-diagnosis-remove subtractProblemButton' type='button' aria-label='Remove diagnosis'>" +
            "<span class='glyphicon glyphicon-minus'></span>" +
            "</button>" +
            "</div>" +
            "<div class='femr-diagnosis-who'></div>" +
            "</div>");

        if ($('#whoReportingEnabled').length) {
            $('.problemWrap .femr-diagnosis-item.problem').last().find('.femr-diagnosis-who').append(whoDropdown.buildHealthEventDropdown(problemIndex));
        }

        var problemInputElement = $("[name='problems[" + problemIndex + "].name']");
        //data for typeahead already exists on the page from loading the diagnoses input box
        typeaheadFeature.initalizeTypeAhead($(problemInputElement), 'diagnoses', true, true);
        $(problemInputElement).focus();
    },
    removeProblemField: function ($trigger) {
        problemFeature.refreshSelectors();
        var $problemItem = $trigger && $trigger.length
            ? $trigger.closest('.femr-diagnosis-item.problem')
            : $('.femr-diagnosis-item.problem').last();
        var lastProblem = $problemItem.find('.newProblems').last();
        if ($(problemFeature.newProblems).size() > 1) {
            if (!$(lastProblem).is('[readonly]')) {
                $problemItem.remove();
            }
        } else {
            if (!$(lastProblem).is('[readonly]')) {
                $(lastProblem).val('');
            }
        }
    }
};
var prescriptionFeature = {
    medicationTypeaheadData: [],
    administrationTypeaheadData: [],
    allPrescriptions: $('.newPrescriptionsContainer').find('.prescriptionRow'),
    refreshSelectors: function () {
        prescriptionFeature.allPrescriptions = $(prescriptionFeature.allPrescriptions.selector);
    },
    initializeMedicationTypeahead: function() {
        return $.getJSON("/search/typeahead/medicationsWithID", function (data) {
            prescriptionFeature.medicationTypeaheadData = data;
        });
    },
    medicationTypeaheadMatcher: function(strs) {
        return function findMatches(q, cb) {
            const options = {
                includeScore: true,
                keys: ['name']
            }

            const fuse = new Fuse(strs.medication, options)
            const searchResults = fuse.search(q);
            const displayResults = searchResults.map(obj => {
                var item = obj.item;
                item.value = item.name;
                return item;
            });
            cb(displayResults);
        };
    },
    // Only adds typeahead to the last prescription row
    addMedicationTypeahead: function() {
        var $element = prescriptionFeature.allPrescriptions.last().find(".medicationName");
        $element.typeahead({ hint: true, highlight: true },
            {
                displayKey: 'value',
                name: "medications",
                minLength: 0,
                source: prescriptionFeature.medicationTypeaheadMatcher(prescriptionFeature.medicationTypeaheadData),
                templates: {
                //    suggestion: Handlebars.compile("<div>{{value}} {{#each ingredients}}<div class='medication_ingredient'>{{name}} {{value}}{{unit}}</div>{{/each}}</div>")
                }
            }).on('typeahead:selected', function(event, item) {
                // triggered when an item is selected from the dropdown list in autocompleted
                var $medicationID = $(this).closest(".prescriptionRow").find(".medicationID");
                $medicationID.val(item.id);
            }).on('typeahead:autocompleted', function(event, item, data) {
                // triggered when an item is tabbed to completion
                $(this).trigger("typeahead:selected", item);
            }
        ).on("input", function(event) {
                // triggered when text is entered that is not part of the autocomplete
                var $medicationID = $(this).closest(".prescriptionRow").find(".medicationID");
                $medicationID.val("");
        });
    },
    setupNewPrescriptionRow: function(skipTypeahead) {

        prescriptionFeature.refreshSelectors();

        /* Setup calculate to days input on keyup/change events - both administration and days change */
        prescriptionFeature.allPrescriptions.last().find(".prescriptionAdministrationDays > input").on("keyup change",
            prescriptionFeature.calculateTotalPrescriptionAmount
        );
        prescriptionFeature.allPrescriptions.last().find(".prescriptionAdministration > select").on("change",
            prescriptionFeature.calculateTotalPrescriptionAmount
        );

        if (skipTypeahead !== true) {
            prescriptionFeature.addMedicationTypeahead();
        }
    },
    getNumberOfNonReadonlyPrescriptionFields: function () {
        prescriptionFeature.refreshSelectors();
        var number = 0;
        $(prescriptionFeature.allPrescriptions).find(".medicationName").each(function () {
            if (!$(this).is('[readonly]')) {
                number++;
            }
        });
        return number;
    },
    calculateTotalPrescriptionAmount: function() {

        var $prescriptionRow = $(this).closest(".prescriptionRow");
        var $amountInput = $prescriptionRow.find(".prescriptionAmount input");

        // Modifer per day for administration type
        var selectedOption= $prescriptionRow.find(".prescriptionAdministration").find("select.administrationName > option:selected");
        var modifier = parseFloat( $(selectedOption).data("modifier") );

        // Days to prescribe
        var days = parseFloat($prescriptionRow.find(".prescriptionAdministrationDays input").val());

        var amount = Math.ceil(modifier * days);
        $amountInput.val(amount);
    },
    addPrescriptionField: function () {

        var scriptIndex = prescriptionFeature.getNumberOfNonReadonlyPrescriptionFields();

        // get prescription row view from server
        $.ajax({
            url: '/medical/prescriptionRow/' + scriptIndex,
            data: {},
            success: function( data ){

                $(".newPrescriptionsContainer").append( data );

                prescriptionFeature.setupNewPrescriptionRow();
            }
        })
            .done(function () {
                $('.medicationName:last').focus();
            });
    },
    removePrescriptionField: function ($trigger) {
        console.log('removePrescriptionField called', $trigger && $trigger.length ? $trigger.get(0) : null);
        prescriptionFeature.refreshSelectors();
        var $rowToRemove = $trigger && $trigger.length
            ? $trigger.closest('.prescriptionRow')
            : $(prescriptionFeature.allPrescriptions).last();
    var isReadonly = $rowToRemove.data('prescriptionReadonly') === true;

        console.log('Prescription row found', $rowToRemove.get(0));
        console.log('Is readonly?', isReadonly, 'total rows', $(prescriptionFeature.allPrescriptions).length);

        if (isReadonly) {
            console.log('Skipping remove: readonly prescription');
            return;
        }

        $rowToRemove.remove();
        prescriptionFeature.refreshSelectors();

        console.log('Row removed. Remaining rows', $(prescriptionFeature.allPrescriptions).length);

        if ($(prescriptionFeature.allPrescriptions).length === 0) {
            console.log('No rows left, adding new prescription row');
            prescriptionFeature.addPrescriptionField();
        }
    }
};
var multipleChiefComplaintFeature = {
    numberOfChiefComplaints: $(".chiefComplaintText").length,
    getCurrentChiefComplaintObject: function () {
        return $('.chiefComplaintText').not('.hidden');
    },
    getCurrentHpiObject: function () {
        return $('.hpiWraps').not('.hidden');
    },
    slideChiefComplaintRight: function () {
        var nextText = multipleChiefComplaintFeature.getCurrentChiefComplaintObject().next();
        var nextForm = multipleChiefComplaintFeature.getCurrentHpiObject().next();
        if ($(nextText).hasClass('chiefComplaintText') && $(nextForm).hasClass('hpiWraps')) {
            multipleChiefComplaintFeature.getCurrentChiefComplaintObject().addClass("hidden");
            multipleChiefComplaintFeature.getCurrentHpiObject().addClass("hidden");
            $(nextForm).removeClass("hidden");
            $(nextText).removeClass("hidden");
            return true;
        } else {
            return false;
        }

    },
    slideChiefComplaintLeft: function () {
        var previousText = multipleChiefComplaintFeature.getCurrentChiefComplaintObject().prev();
        var previousForm = multipleChiefComplaintFeature.getCurrentHpiObject().prev();
        if ($(previousText).hasClass('chiefComplaintText') && $(previousForm).hasClass('hpiWraps')) {
            multipleChiefComplaintFeature.getCurrentChiefComplaintObject().addClass("hidden");
            multipleChiefComplaintFeature.getCurrentHpiObject().addClass("hidden");
            $(previousText).removeClass("hidden");
            $(previousForm).removeClass("hidden");
            return true;
        } else {
            return false;
        }
    }
};

//every function in here gets the most recent value of
//a vital from the "record new vitals menu"
//Add new fields as you need them #lazy
var recentVitals ={
    /**
     * Uses the listVitals table to retrieve
     * the most recent systolic blood pressure value
     * @returns *the most recent systolic blood pressure value or null
     */
    getCurrentSystolic: function(){

        var systolic = null;
        $('.systolic').each(function(){
            if ($.trim($(this).text()) !== ''){
                systolic = $(this).text();
                return false;
            }
        });

        return systolic;
    },
    /**
     * Uses the listVitals table to retrieve
     * the most recent diastolic blood pressure value
     * @returns *the most recent diastolic blood pressure value or null
     */
    getCurrentDiastolic: function(){

        var diastolic = null;
        $('.diastolic').each(function(){
            if ($.trim($(this).text()) !== ''){
                diastolic = $(this).text();
                return false;
            }
        });

        return diastolic;
    },
    /**
     * Uses the listVitals table to retrieve
     * the most recent weight value
     * @returns *the most recent weight (lbs) value or null
     */
    getCurrentWeight: function(){

        var weight = null;
        // Search vitals from most recent to least for one containing a valid weight
        $($("#weight td").get()).each(function() {
            var tryParse = parseFloat($(this).attr("data-weight"));
            if (!isNaN(tryParse)) {
                weight = tryParse;
                return false;
            }
        });

        return weight;
    },
    /**
     * Uses the listVitals table to retrieve
     * the most recent height (feet) value
     * @returns *the most recent height (feet) value or null
     */
    getCurrentHeightFeet: function(){

        var heightFeet = null;
        // Search vitals from most recent to least for one containing a valid height
        $($("#height td").get()).each(function() {
            var tryParseFeet = parseFloat($(this).attr("data-feet"));
            if (!isNaN(tryParseFeet)) {
                heightFeet = tryParseFeet;
                return false;
            }
        });

        return heightFeet;
    },
    /**
     * Uses the listVitals table to retrieve
     * the most recent height (inches) value
     * @returns *the most recent height (inches) value or null
     */
    getCurrentHeightInches: function(){

        var heightInches = null;
        $($("#height td").get()).each(function() {
            var tryParseInches = parseFloat($(this).attr("data-inches"));
            if (!isNaN(tryParseInches)) {
                heightInches = tryParseInches;
                return false;
            }
        });

        return heightInches;
    }
};

$(document).ready(function () {

    //  html id and text complaint and tabs to be translated
    var jsonObj = [
        {'id':'#complaintInfo','text':''},
        {'id':'#onsetTab','text':''},
        {'id':'#qualityTab','text':''},
        {'id':'#radiationTab','text':''},
        {'id':'#provokesTab','text':''},
        {'id':'#palliatesTab','text':''},
        {'id':'#timeTab','text':''},
        {'id':'#narrativeTab','text':''},
        {'id':'#physicalTab','text':''},
        {'id':'#assessmentTab','text':''},
        {'id':'#procedureTab','text':''},
        {'id':'#pharmacyTab','text':''},
        {'id':'#medicalTab','text':''},
        {'id':'#socialTab','text':''},
        {'id':'#currentTab','text':''},
        {'id':'#familyTab','text':''}]

    var patientId = document.getElementById('patientId').value;

    // build textToTranslate
    var textToTranslate = $(jsonObj[0].id + " span").text().replace("@","at");
    jsonObj[0].text = textToTranslate;
    for (let i = 1; i < jsonObj.length; i++) {
        textToTranslate = textToTranslate + " @ " + $(jsonObj[i].id).val().replace("@","at");
        jsonObj[i].text = $(jsonObj[i].id).val();
    }
    console.log("text:", textToTranslate);
    console.log(jsonObj);

    // get translation
    $.ajax({
        type: 'get',
        url: '/translate',
        data: {text : JSON.stringify(jsonObj), patientId: patientId},
        success: function(response){
            if(response.translation === "SameToSame"){
                $("#toggleBtn").remove();
            }
            else{
                var listTranslated = JSON.parse(response.translation);
                console.log(listTranslated);
                if(listTranslated[0]["text"] === "Translation Unavailable"){
                    //option 1 - end buffering
                        $("#loading").remove();
                        $("#toggleBtn").text("Unavailable");
                }
                else if (listTranslated[0]["text"] === "Delimiter Error") {
                    console.log("backup translation required out of 16 tabs ", listTranslated.length, " recovered");
                    for (let i = 0; i < jsonObj.length; i++) {
                        $.ajax({
                            type: 'get',
                            url: '/translate',
                            data: {text: JSON.stringify([jsonObj[i]]), patientId: patientId},
                            success: function (response) {
                                if (i === jsonObj.length - 1) {
                                    // end buffering on last field
                                    $("#loading").remove();
                                    $("#toggleBtn").text("Show Original");
                                }
                                var textOut = JSON.parse(response.translation)[0]["text"]
                                populateField(textOut, jsonObj, response.fromLanguageIsRtl, response.toLanguageIsRtl, i);
                            },
                            failure: function (result) {
                                console.error('Failed to fetch backup translation');
                            }
                        });
                    }
            }
                else{
                    $("#loading").remove();
                    $("#toggleBtn").text("Show Original");

                    // for each field populate them
                    for (let i = 0; i < jsonObj.length; i++) {
                        var textOut = listTranslated[i]["text"];
                        populateField(textOut, jsonObj, response.fromLanguageIsRtl, response.toLanguageIsRtl, i);
                    }
                }
            }
        },
        failure: function(result){
            console.error('Failed to fetch translation');
        }
    });

    //set a global variable to track browser compatibility with image previews
    window.isFileReader = typeof FileReader !== 'undefined';

    //make the first tab active
    $('#medicalTabs li').first().addClass('active');
    //unhide the first HPI form
    $('.hpiWraps').first().removeClass('hidden');
    //unhide the first chief complaint on HPI
    $('.chiefComplaintText').first().removeClass('hidden');

    //hide/unhide prescriptions
    $('#addPrescriptionButton').click(function () {
        prescriptionFeature.addPrescriptionField();
    });

    $(document).on('click', '.subtractPrescriptionButton', function () {
        prescriptionFeature.removePrescriptionField($(this));
    });

    // toggle translated text
    $('#toggleBtn').click(function () {
        if (this.innerHTML === "Show Original" || this.innerHTML === "↻") {
            // toggle complaint
            var oldText = $(jsonObj[0].id).text();
            var newText = $(jsonObj[0].id + "Store").text();
            $(jsonObj[0].id + "Store").text(oldText);
            $(jsonObj[0].id).text(newText);

            // switch and set the rtl values
            var storeRtl = $(jsonObj[0].id + "Store").data("isRtl");
            var currentRtl = $(jsonObj[0].id).data("isRtl");
            $(jsonObj[0].id + "Store").data("isRtl", currentRtl);
            $(jsonObj[0].id).data("isRtl", storeRtl);

            if ($(jsonObj[0].id).data("isRtl")) {
                $(jsonObj[0].id).addClass('rtl');
            } else {
                $(jsonObj[0].id).removeClass('rtl');
            }

            // toggle tabs
            for (let i = 1; i < jsonObj.length; i++) {
                var oldText = $(jsonObj[i].id).val();
                var newText = $(jsonObj[i].id + "Store").text();
                $(jsonObj[i].id + "Store").text(oldText);
                $(jsonObj[i].id).val(newText);

                // switch and set the rtl values
                var storeRtl = $(jsonObj[i].id + "Store").data("isRtl");
                var currentRtl = $(jsonObj[i].id).data("isRtl");
                $(jsonObj[i].id + "Store").data("isRtl", currentRtl);
                $(jsonObj[i].id).data("isRtl", storeRtl);

                if ($(jsonObj[i].id).data("isRtl")) {
                    $(jsonObj[i].id).addClass('rtl');
                } else {
                    $(jsonObj[i].id).removeClass('rtl');
                }
            }
        }
    });

    //hide/unhide problems
    $('#addProblemButton').click(function () {
        problemFeature.addProblemField();
    });

    $(document).on('click', '.subtractProblemButton', function () {
        problemFeature.removeProblemField($(this));
    });

    $('#medicalTabs li').click(function () {
        //remove the spaces before sending the id
        showTab($(this).attr('id').replace(/\s/g, ''));
    });

    //Shown event for Modal form
    $('#modalNewImage').on('shown.bs.modal', function () {
        $('#modalTextEntry').focus();
    });

    //Hide event for Modal form
    $('#modalNewImage').on('hide.bs.modal', function () {
        //Clear the img src
        $('#modalImg').attr('src', '');
        //Clear the textbox
        $('#modalTextEntry').val('');
        $('#modalSavePortrait').unbind(); //unbind any events associated to the save button

    });


    $('#newVitalsDialog').dialog({
        dialogClass: 'editUserDialog',
        autoOpen: false,
        draggable: true,
        position: 'center',
        modal: true,
        height: 500,
        width: 450

    });

    $('#newVitalsBtn').click(function () {
        $.ajax({
            url: '/medical/newVitals',
            type: 'GET',
            success: function (partialView) {
                $('#newVitalsPartial').html(partialView);
                $('#newVitalsDialog').dialog("open");
            },
            error: function () {
                alert("Error. Please make sure you are connected to fEMR.");
            }
        })

    });

    // Fail-safe handlers for dynamically injected new-vitals partial.
    $(document).off('click', '#cancelVitalsBtn').on('click', '#cancelVitalsBtn', function () {
        $('#newVitalsDialog').dialog("close");
    });

    $(document).off('click', '#saveVitalsBtn').on('click', '#saveVitalsBtn', function () {
        var patientVitals = {
            respiratoryRate: $('#newRespiratoryRate'),
            bloodPressureSystolic: $('#newSystolic'),
            bloodPressureDiastolic: $('#newDiastolic'),
            heartRate: $('#newHeartRate'),
            oxygenSaturation: $('#newOxygen'),
            temperature: $('#newTemperature'),
            weight: $('#newWeight'),
            heightFeet: $('#newHeightFeet'),
            heightInches: $('#newHeightInches'),
            glucose: $('#newGlucose'),
            weeksPregnant: $('#weeksPreg')
        };

        var isValid = (typeof vitalClientValidator === 'function') ? vitalClientValidator(patientVitals) : true;
        if (!isValid) {
            alert("Please correct highlighted vitals before saving.");
            return;
        }

        var newVitals = {};
        if (patientVitals.bloodPressureSystolic.val() !== '') { newVitals.bloodPressureSystolic = patientVitals.bloodPressureSystolic.val(); }
        if (patientVitals.bloodPressureDiastolic.val() !== '') { newVitals.bloodPressureDiastolic = patientVitals.bloodPressureDiastolic.val(); }
        if (patientVitals.heartRate.val() !== '') { newVitals.heartRate = patientVitals.heartRate.val(); }
        if (patientVitals.temperature.val() !== '') { newVitals.temperature = patientVitals.temperature.val(); }
        if (patientVitals.oxygenSaturation.val() !== '') { newVitals.oxygenSaturation = patientVitals.oxygenSaturation.val(); }
        if (patientVitals.respiratoryRate.val() !== '') { newVitals.respiratoryRate = patientVitals.respiratoryRate.val(); }
        if (patientVitals.heightFeet.val() !== '') { newVitals.heightFeet = patientVitals.heightFeet.val(); }
        if (patientVitals.heightInches.val() !== '') { newVitals.heightInches = patientVitals.heightInches.val(); }
        if (patientVitals.weight.val() !== '') { newVitals.weight = patientVitals.weight.val(); }
        if (patientVitals.glucose.val() !== '') { newVitals.glucose = patientVitals.glucose.val(); }
        if (patientVitals.weeksPregnant.val() !== '') { newVitals.weeksPregnant = patientVitals.weeksPregnant.val(); }

        newVitals.smoker = document.getElementById("newSmoker")?.checked ? "1" : null;
        newVitals.diabetic = document.getElementById("newDiabetic")?.checked ? "1" : null;
        newVitals.alcohol = document.getElementById("newAlcohol")?.checked ? "1" : null;
        newVitals.cholesterol = document.getElementById("newCholesterol")?.checked ? "1" : null;
        newVitals.hypertension = document.getElementById("newHypertension")?.checked ? "1" : null;

        $.ajax({
            url: '/medical/updateVitals/' + $('#patientId').val(),
            type: 'POST',
            data: newVitals
        }).done(function () {
            $('#newVitalsDialog').dialog("close");
            $.ajax({
                url: '/medical/listVitals/' + $('#patientId').val(),
                type: 'GET',
                success: function (partialView) {
                    $('#vitalsPartial').html(partialView);
                }
            });
        }).fail(function () {
            alert("Unable to save vitals right now. Please try again.");
        });
    });

    $('#modalCancelPortrait').click(function () {
        //remove warnings
        $('#modalImg').parent().find('p').remove();
    });


    $('#chiefComplaintRightArrow').click(function () {
        multipleChiefComplaintFeature.slideChiefComplaintRight();
    });
    $('#chiefComplaintLeftArrow').click(function () {
        multipleChiefComplaintFeature.slideChiefComplaintLeft();
    });


    $('#medicalSubmitBtn').click(function () {

        return (photoNameFixup() && validate()); //validate from medicalClientValidation.js
    });

    $("form").submit(function(event) {

        //validate from medicalClientValidation.js
        if (!photoNameFixup() || !validate()) {
            event.preventDefault();
            return false;
        }
        return true;
    });

    prescriptionFeature.setupNewPrescriptionRow(true);
    prescriptionFeature.initializeMedicationTypeahead().then(function() {
        prescriptionFeature.addMedicationTypeahead();
    });

    typeaheadFeature.setGlobalVariableAndInitalize("/search/typeahead/diagnoses", problemFeature.newProblems.first(), 'diagnoses', true, true);
});

/**
 * Populates chief complaint and tab fields with toggle support
 */
function populateField(textOut, jsonObj, fromLanguageIsRtl, toLanguageIsRtl, i) {
    if (jsonObj[i].id === "#complaintInfo"){
        var oldText =  $(jsonObj[i].id + " span").text();
        $(jsonObj[i].id + "Store").text(oldText)
        $(jsonObj[i].id + "Store").data('isRtl', fromLanguageIsRtl); // store whether langauge is rtl
        $(jsonObj[i].id).text(textOut)
        $(jsonObj[i].id).data('isRtl', toLanguageIsRtl);

    } else {
        var oldText =  $(jsonObj[i].id).val();
        $(jsonObj[i].id + "Store").text(oldText)
        $(jsonObj[i].id + "Store").data('isRtl', fromLanguageIsRtl);
        $(jsonObj[i].id).val(textOut);
        $(jsonObj[i].id).data('isRtl', toLanguageIsRtl);
    }

    // make the text right to left if it is a rtl language
    if ($(jsonObj[i].id).data('isRtl')) {
        $(jsonObj[i].id).addClass('rtl');
    } else {
        $(jsonObj[i].id).removeClass('rtl');
    }
}



/**
 * Generic tab showing function. Also takes care of identifying active tab.
 *
 * @param clickedTab tab that the user clicked
 */
function showTab(clickedTab) {
    $('#tabContentWrap').find('> .controlWrap').each(function () {
        if ($(this).attr("id").replace(/\s+/g, '') === clickedTab + "Control") {
            $(this).removeClass("hidden");
        } else {
            $(this).addClass("hidden");
        }
    });

    $('#medicalTabs').find("li").each(function () {
        if ($(this).is("#" + clickedTab)) {
            $(this).addClass("active");
        } else {
            $(this).removeClass("active");
        }
    });
}


//Renders image from fileInputId and sets the image
// id set to imgOutId.
function setDynamicImage(fileChngEvt, imgOutId) {

    var files = fileChngEvt.files; // FileList object
    // Loop through the FileList and render image files as thumbnails.
    for (var i = 0, f; f = files[i]; i++) {

        // Only process image files.
        if (!f.type.match('image.*')) {
            continue;
        }

        //detect existance of FileReader
        if (window.isFileReader) {
            var reader = new FileReader();

            // Closure to capture the file information.
            reader.onload = (function () {
                return function (e) {
                    $(imgOutId).attr('src', e.target.result);
                };
            })(f);

            // Read in the image file as a data URL.
            reader.readAsDataURL(f);
        }

        return;
    }
}

function setupModal(titleText, descText, imgSrcVal, onSave) {
    if (!window.isFileReader) {
        //add warnings for non FileReader compliant browsers
        $('#modalImg').hide();
        $('#modalImg').parent(


        ).append("<p>Image Preview is not supported in your browser.</p>")
    }
    if (titleText != null) {
        $('#myModalLabel').text(titleText);
    }
    if (descText != null) {
        $('#modalTextEntry').val(descText);
    }
    if (imgSrcVal != null) {
        $('#modalImg').attr('src', imgSrcVal);
    }
    if (onSave != null) {
        $('#modalSavePortrait').on('click', onSave);
    }
}

function addNewPortrait() {
    //Copy template DIV into var
    var newPortrait = $('#portraitTemplate').children().clone(true);
    //Get description text from modal
    var descriptionText = $('#modalTextEntry').val();
    //copy image preview from Modal dialog to new frame
    if (window.isFileReader) {
        newPortrait.find('> div > img').replaceWith($('#modalImg').clone(true));
    } else {
        newPortrait.find('> div > img').remove();
        newPortrait.find('> div').append("<p>Image Preview is not supported in your browser.</p>")
    }

    //Clear the id
    newPortrait.find('> div > img').attr('id', '');
    //copy description into new frame
    newPortrait.find('> div > div > div > p ').html(descriptionText);
    //Move and replace input box element
    var dataList = newPortrait.find('> div[name=dataList]');
    dataList.children('input[name=patientPhoto]').first().remove(); //delete old value
    $('#photoInputContainer').children().first().prop('name', 'patientPhoto');
    $('#photoInputContainer').children().appendTo(dataList);
    //Create new photoInput element
    replaceFileInputControl();

    //Copy desc text to input control:
    dataList.children('input[name=imageDescText]').first().val(descriptionText);
    //dataList.children('input[name=imageDescText]').first().prop('name', 'imageDescText');

    newPortrait.show();

    $('#patientImageList').append(newPortrait);
}

function replaceFileInputControl() {
    $('#photoInputContainer').html('<input type="file" class="form-control femr-input" onchange="imageInputChange(this)" placeholder="Choose Image" />');
}

function imageInputChange(evt) {
    setDynamicImage(evt, "#modalImg");
    setupModal("New Photo", "", null, function () {
        //remove warnings from modal
        $('#modalImg').parent().find('p').remove();
        addNewPortrait();
        $('#modalNewImage').modal('hide');
    });
    $('#modalNewImage').modal();
}

function portraitEdit(e) {
    //Get parent div ref:
    var rootDiv = $(e).parent().parent().parent().parent().parent().first();
    //Get ref to data elements
    var dataDiv = rootDiv.find("> div[name=dataList]").first();

    var descText = dataDiv.children('input[name=imageDescText]').first().val();
    if (descText == undefined)
        descText = "";
    var srcVal = rootDiv.find('> > img').first().prop('src');

    //Set modal text:
    setupModal("Edit Description", descText, srcVal, function () {

        var newDesc = $('#modalTextEntry').val();
        if (descText != newDesc) {
            //User has edited the text. Update field and set update flag for server-side processing
            dataDiv.children('input[name=hasUpdatedDesc]').first().prop('checked', true);
            dataDiv.children('input[name=hasUpdatedDesc]').first().prop('value', true);
            dataDiv.children('input[name=imageDescText]').first().val(newDesc);
            var tempP = rootDiv.find('> > > > p').first();
            tempP.text(newDesc);
        }
        $('#modalNewImage').modal('hide');
    });
    $('#modalNewImage').modal();


}

function portraitDelete(e) {
    var b = confirm("Are you sure you would like to delete this photo?");
    if (b === true) {
        //get a reference to the root div element (move up five places in this case)
        var rootDiv = $(e).parent().parent().parent().parent().parent().first();
        //get a reference to the data list
        var dataDiv = rootDiv.find("> div[name=dataList]").first();
        var photoIdInput = dataDiv.children("input[name=photoId]").first();
        if (photoIdInput.val() == "") {
            //A photo Id does not exist, therefore, this is a NEW photo (ie, not saved server-side)
            //  Thus we can simply delete this element from the DOM
            rootDiv.remove();
        }
        else {
            /* In this case, the photo exists on the server.
             Therefore, we must set the 'deleteRequested' flag to TRUE
             and then finally hide the element */

            //Set is delete flag to TRUE
            dataDiv.children("input[name=deleteRequested]").first().prop('checked', true);
            dataDiv.children("input[name=deleteRequested]").first().prop('value', true);
            //Hide element
            rootDiv.hide();
        }
    }
}

/*
 Loop through all of the photo 'portrait' frames
 and update the names to array notation.
 ie: photoId => photoId[0], photoId[1], ...
 */
function photoNameFixup() {
    var photoList = $('#patientImageList').children();
    photoList.each(function (i) {
        var dataList = $(this).find('> div[name=dataList]').first();
        //Now loop through all of the data elements
        dataList.children().each(function () {
            var oldName = $(this).attr('name');
            var name = new String(oldName);
            if (name.indexOf('[') >= 0)
                name = name.substr(0, name.indexOf('['));
            name = name + '[' + i + ']';
            $(this).prop('name', name);
        });
    });

    return true;
}

$(document).on('click', '.deleteProblem', function (event) {
    event.preventDefault();
    var $button = $(this);
    var $itemToRemove = $button.closest('.femr-diagnosis-item');
    var problemValue = $.trim($button.data('problem'));
    var encodedProblem = encodeURIComponent(problemValue || '');

    $.ajax({
        type: 'post',
        url: '/medical/deleteProblem/' + $('#patientId').val() + '/' + encodedProblem,
        success: function (result) {
            if (result == "true") {
                $itemToRemove.remove();
            }
        },
        failure: function () {

        }
    });
});

// WHO Searchable Dropdown
var whoDropdown = {
    // Maps data-value (English, stored in DB) to translation key
    healthEventItems: [
        { type: 'header', key: 'daily_report_trauma', fallback: 'Trauma' },
        { type: 'item', value: 'Major head/spine injury', id: 1, key: 'who_event_major_head_spine' },
        { type: 'item', value: 'Major torso injury', id: 2, key: 'who_event_major_torso' },
        { type: 'item', value: 'Major extremity injury', id: 3, key: 'who_event_major_extremity' },
        { type: 'item', value: 'Moderate injury', id: 4, key: 'who_event_moderate_injury' },
        { type: 'item', value: 'Minor injury', id: 5, key: 'who_event_minor_injury' },
        { type: 'header', key: 'daily_report_infectious_disease', fallback: 'Infectious disease' },
        { type: 'item', value: 'Acute respiratory infection', id: 6, key: 'who_event_acute_respiratory' },
        { type: 'item', value: 'Acute watery diarrhea', id: 7, key: 'who_event_watery_diarrhea' },
        { type: 'item', value: 'Acute bloody diarrhea', id: 8, key: 'who_event_bloody_diarrhea' },
        { type: 'item', value: 'Acute jaundice syndrome', id: 9, key: 'who_event_jaundice' },
        { type: 'item', value: 'Suspected measles', id: 10, key: 'who_event_measles' },
        { type: 'item', value: 'Suspected meningitis', id: 11, key: 'who_event_meningitis' },
        { type: 'item', value: 'Suspected tetanus', id: 12, key: 'who_event_tetanus' },
        { type: 'item', value: 'Acute flaccid paralysis', id: 13, key: 'who_event_flaccid_paralysis' },
        { type: 'item', value: 'Acute haemorrhagic fever', id: 14, key: 'who_event_haemorrhagic_fever' },
        { type: 'item', value: 'Fever of unknown origin', id: 15, key: 'who_event_fever_unknown' },
        { type: 'header', key: 'daily_report_emergency', fallback: 'Emergency' },
        { type: 'item', value: 'Surgical emergency (Non-trauma)', id: 16, key: 'who_event_surgical_emergency' },
        { type: 'item', value: 'Medical emergency (Non-infectious)', id: 17, key: 'who_event_medical_emergency' },
        { type: 'header', key: 'daily_report_other_key_diseases', fallback: 'Other key diseases' },
        { type: 'item', value: 'Skin disease', id: 18, key: 'who_event_skin_disease' },
        { type: 'item', value: 'Acute mental health problem', id: 19, key: 'who_event_mental_health' },
        { type: 'item', value: 'Obstetric complications', id: 20, key: 'who_event_obstetric' },
        { type: 'item', value: 'Severe Acute Malnutrition (SAM)', id: 21, key: 'who_event_sam' },
        { type: 'item', value: 'Other diagnosis, not specified above', id: 22, key: 'who_event_other_diagnosis' }
    ],

    buildHealthEventItemsHTML: function() {
        var t = (window.femrLanguageData && window.femrLanguageCode && window.femrLanguageData[window.femrLanguageCode])
            ? window.femrLanguageData[window.femrLanguageCode]
            : {};
        return whoDropdown.healthEventItems.map(function(item) {
            if (item.type === 'header') {
                return '<li class="who-group-header">' + (t[item.key] || item.fallback) + '</li>';
            }
            return '<li class="who-item" data-value="' + item.value + '" data-id="' + item.id + '">' +
                   (t[item.key] || item.value) + '</li>';
        }).join('');
    },

    buildHealthEventDropdown: function(index) {
        var t = (window.femrLanguageData && window.femrLanguageCode && window.femrLanguageData[window.femrLanguageCode])
            ? window.femrLanguageData[window.femrLanguageCode]
            : {};
        var placeholder = t.who_event_placeholder || '- WHO Health Event -';
        return '<div class="who-searchable-dropdown">' +
               '<div class="who-dropdown-toggle">' +
               '<span class="who-dropdown-label">' + placeholder + '</span>' +
               '<span class="caret"></span>' +
               '</div>' +
               '<div class="who-dropdown-menu">' +
               '<input type="text" class="who-search-input" placeholder=""/>' +
               '<ul class="who-dropdown-list">' + whoDropdown.buildHealthEventItemsHTML() + '</ul>' +
               '</div>' +
               '<input type="hidden" name="whoHealthEvents[' + index + ']" class="who-selected-value"/>' +
               '<input type="hidden" name="whoHealthEventIds[' + index + ']" class="who-selected-id"/>' +
               '</div>';
    },

    rebuildLabels: function(t) {
        // Update labels in any already-rendered dropdowns
        $('.who-dropdown-list .who-group-header, .who-dropdown-list .who-item').each(function() {
            var $el = $(this);
            if ($el.hasClass('who-group-header')) {
                whoDropdown.healthEventItems.forEach(function(item) {
                    if (item.type === 'header' && $el.text() === item.fallback) {
                        $el.text(t[item.key] || item.fallback);
                    }
                });
            } else {
                var val = $el.data('value');
                whoDropdown.healthEventItems.forEach(function(item) {
                    if (item.type === 'item' && item.value === val && t[item.key]) {
                        $el.text(t[item.key]);
                    }
                });
            }
        });
        // Update placeholder labels that still show the default English text
        $('.who-dropdown-toggle .who-dropdown-label').each(function() {
            var $lbl = $(this);
            if ($lbl.text() === '- WHO Health Event -') {
                $lbl.text(t.who_event_placeholder || '- WHO Health Event -');
            }
        });
    },

    filter: function($input) {
        var query = $input.val().toLowerCase();
        var $list = $input.closest('.who-dropdown-menu').find('.who-dropdown-list');
        var lastHeader = null;
        var headerHasVisible = false;

        $list.children().each(function() {
            var $el = $(this);
            if ($el.hasClass('who-group-header')) {
                if (lastHeader) { lastHeader.toggle(headerHasVisible); }
                lastHeader = $el;
                headerHasVisible = false;
            } else {
                var matches = $el.data('value').toLowerCase().indexOf(query) !== -1;
                $el.toggle(matches);
                if (matches) { headerHasVisible = true; }
            }
        });
        if (lastHeader) { lastHeader.toggle(headerHasVisible); }
    },

    closeAll: function() {
        $('.who-dropdown-menu').hide();
    }
};

// Restore saved WHO procedure ID by matching the saved name against the list items
$('.who-searchable-dropdown[data-saved-procedure]').each(function() {
    var saved = $(this).data('saved-procedure');
    $(this).find('.who-item').each(function() {
        if ($(this).data('value') === saved) {
            $(this).closest('.who-searchable-dropdown').find('.who-selected-id').val($(this).data('id'));
            return false;
        }
    });
});

// Inject a WHO Health Event dropdown (or read-only display if already saved) into each problem row on page load
if ($('#whoReportingEnabled').length) {
    $('.problemWrap .femr-diagnosis-item').each(function(index) {
        var $item = $(this);
        var $whoContainer = $item.find('.femr-diagnosis-who');
        if ($whoContainer.find('.who-searchable-dropdown').length) {
            return;
        }

        if ($item.find('.oldProblems').length) {
            var saved = $item.data('who-health-event') || '- WHO Health Event -';
            $whoContainer.append('<div class="who-searchable-dropdown"><div class="who-dropdown-toggle who-readonly"><span class="who-dropdown-label">' + saved + '</span></div></div>');
        } else if ($item.hasClass('problem')) {
            var problemIndex = $item.find('.newProblems').first().attr('name');
            var indexMatch = problemIndex ? problemIndex.match(/\[(\d+)\]/) : null;
            var indexValue = indexMatch ? parseInt(indexMatch[1], 10) : index;
            $whoContainer.append(whoDropdown.buildHealthEventDropdown(indexValue));
        }
    });
}

// Event delegation — handles both static (procedure) and dynamic (per-problem) dropdowns
$(document).on('click', '.who-dropdown-toggle', function(e) {
    e.stopPropagation();
    var $menu = $(this).siblings('.who-dropdown-menu');
    var isOpen = $menu.is(':visible');
    whoDropdown.closeAll();
    if (!isOpen) {
        $menu.show();
        var $search = $menu.find('.who-search-input');
        $search.val('');
        whoDropdown.filter($search);
        $search.focus();
    }
});

$(document).on('keydown', '.who-search-input', function(e) {
    e.stopPropagation();
});

$(document).on('input', '.who-search-input', function() {
    whoDropdown.filter($(this));
});

$(document).on('click', '.who-item', function(e) {
    e.stopPropagation();
    var $dropdown = $(this).closest('.who-searchable-dropdown');
    $dropdown.find('.who-dropdown-label').text($(this).data('value'));
    $dropdown.find('.who-selected-value').val($(this).data('value'));
    $dropdown.find('.who-selected-id').val($(this).data('id'));
    whoDropdown.closeAll();
});

$(document).click(function() {
    whoDropdown.closeAll();
});