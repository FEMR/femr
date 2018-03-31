//The following code disables using enter key on any form fields of input types text.
//This code below is also found in triage.js, medical.js, and pharmacy.js
window.addEventListener('keydown',function(e){
    if(e.keyIdentifier=='U+000A'||e.keyIdentifier=='Enter'||e.keyCode==13){
        if(e.target.nodeName=='INPUT' && (e.target.type=='text' || e.target.type=='number'
            || e.target.type=='checkbox'|| e.target.type=='tel' || e.target.type=='date'
            || e.target.type=='radio'))
        {
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
        $('.problem')
            .parent()
            .append("<div class='problem'>" +
            "<input name='problems[" + problemIndex + "].name' type='text' class='form-control newProblems'/>" +
            "</div>");

        var problemInputElement = $("[name='problems[" + problemIndex + "].name']");
        //data for typeahead already exists on the page from loading the diagnoses input box
        typeaheadFeature.initalizeTypeAhead($(problemInputElement), 'diagnoses', true, true);
        $(problemInputElement).focus();
    },
    removeProblemField: function () {
        problemFeature.refreshSelectors();
        var lastProblem = $(problemFeature.newProblems).last();
        if ($(problemFeature.newProblems).size() > 1) {
            if (!$(lastProblem).is('[readonly]')) {
                $(lastProblem).parent().parent().remove();
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
            var substrRegex = new RegExp(q, 'i'); //Regex used to determine if a string contains substring 'q'
            var matches = []; //Array to be populated with matches

            //Iterate through medication and find matches
            $.each(strs.medication, function (i, med) {
                if (substrRegex.test(med.name)) {
                    med.value = med.name;
                    matches.push(
                        med
                    );
                }
            });
            cb(matches);
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
    removePrescriptionField: function () {
        prescriptionFeature.refreshSelectors();
        var lastPrescription = $(prescriptionFeature.allPrescriptions).last();
        if ($(prescriptionFeature.allPrescriptions).size() > 1) {
            if (!$(lastPrescription).is('[readonly]')) {
                $(lastPrescription).remove();
            }
        } else {
            if (!$(lastPrescription).is('[readonly]')) {
                $(lastPrescription).val('');
            }
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

    $('#subtractPrescriptionButton').click(function () {
        prescriptionFeature.removePrescriptionField();
    });



    //hide/unhide problems
    $('#addProblemButton').click(function () {
        problemFeature.addProblemField();
    });

    $('#subtractProblemButton').click(function () {
        problemFeature.removeProblemField();
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
    $('#photoInputContainer').html('<input type="file" class="form-control" onchange="imageInputChange(this)" placeholder="Choose Image" />');
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

$('.deleteProblem').click(function(){
    var lineToRemove = $(this).parent().parent();

    $.ajax({
        type: 'post',
        url: '/medical/deleteProblem/' + $('#patientId').val() + '/' + $(this).data('problem'),
        success: function(result){
            if(result == "true"){
                lineToRemove.remove();
            }
        },
        failure: function(result){

        }
    });
});