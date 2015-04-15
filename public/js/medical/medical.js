
var problemFeature = {
    allProblems: $('.newProblems, .oldProblems'),
    newProblems: $('.newProblems'),
    refreshSelectors: function () {
        problemFeature.allProblems = $(problemFeature.allProblems.selector);
        problemFeature.newProblems = $(problemFeature.newProblems.selector);
    },
    getNumberOfNonReadonlyProblemFields: function () {
        problemFeature.refreshSelectors();
        return problemFeature.newProblems.length / 2;
    },
    addProblemField: function () {
        var problemIndex = problemFeature.getNumberOfNonReadonlyProblemFields();
        $('.problem')
            .parent()
            .append("<div class='problem'>" +
                "<input name='problems[" + problemIndex + "].name' type='text' class='form-control input-sm newProblems'/>" +
                "</div>");

        var problemInputElement = $("[name='problems[" + problemIndex + "].name'");
        typeaheadFeature.initalizeTypeAhead($(problemInputElement), 'diagnoses', true, true);
        $(problemInputElement).focus();

    },
    removeProblemField: function () {
        problemFeature.refreshSelectors();
        var lastProblem = $(problemFeature.newProblems).last();
        if ($(problemFeature.newProblems).size() / 2 > 1) {
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
    allPrescriptions: $('.prescriptionWrap > .prescriptionRow'),
    refreshSelectors: function () {
        prescriptionFeature.allPrescriptions = $(prescriptionFeature.allPrescriptions.selector);
    },
    customTypeAheadMatcher: function (strs) {
        return function findMatches(q, cb) {
            var matches, substrRegex;
            // an array that will be populated with substring matches
            matches = [];
            // regex used to determine if a string contains the substring `q`
            substrRegex = new RegExp(q, 'i');

            // iterate through the pool of strings and for any string that
            // contains the substring `q`, add it to the `matches` array
            $.each(strs.medication, function (i, med) {
                if (substrRegex.test(med.name)) {
                    // the typeahead jQuery plugin expects suggestions to a
                    // JavaScript object, refer to typeahead docs for more info
                    matches.push({ id: med.id, value: med.name + " (" + med.form + ")" });
                }
            });

            cb(matches);
        };
    },
    setupNewPrescriptionRow: function() {
        prescriptionFeature.refreshSelectors();

        var $prescriptionElement = prescriptionFeature.allPrescriptions.last().find(".medicationName");
        typeaheadFeature.initalizeTypeAhead($prescriptionElement,
            'medication',
            true,
            true,
            prescriptionFeature.customTypeAheadMatcher,
            function(event, item) {
                var $medicationID = $(this).closest(".prescriptionRow").find(".medicationID");
                $medicationID.val(item.id);
            }
        );

        /* Setup calculate to days input on keyup/change events */
        prescriptionFeature.allPrescriptions.last().find(".prescriptionAdministrationDays > input").on("keyup change",
            prescriptionFeature.calculateTotalPrescriptionAmount
        );
        prescriptionFeature.allPrescriptions.last().find(".prescriptionAdministrationName > select").on("change",
            prescriptionFeature.calculateTotalPrescriptionAmount
        );
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

        // Quantity of medication left
        var qty = $prescriptionRow.find(".prescriptionName input").attr("data-qty");
        // Modifer per day for administration type
        var modifier = parseFloat($prescriptionRow.find(".prescriptionAdministrationName select option:selected").attr("data-modifier"));
        // Days to prescribe
        var days = parseFloat($prescriptionRow.find(".prescriptionAdministrationDays input").val());

        var amount = Math.ceil(modifier * days);
        $amountInput.val(amount);

        if (amount > qty)
            $amountInput.addClass("lowMedication");
        else
            $amountInput.removeClass("lowMedication");
    },
    addPrescriptionField: function () {
        var scriptIndex = prescriptionFeature.getNumberOfNonReadonlyPrescriptionFields();

        var $prescriptionRow = $("<div>").addClass("prescriptionRow prescriptionInput");

        var $typeAhead = $("<input type='text' class='medicationName form-control input-sm'/>");
        $prescriptionRow.append(
            $("<span class='prescriptionName'></span>").append($typeAhead)
        );
        $prescriptionRow.append("<input class='medicationID' name='prescriptions[" + scriptIndex + "].medicationID' type='hidden' />");

        var $cloneSelect = $("select[name='prescriptions[0].administrationId']").clone();
        $cloneSelect.attr("name", "prescriptions[" + scriptIndex + "].administrationId");
        $prescriptionRow.append(
            $("<span class='prescriptionAdministrationName'></span>").append($cloneSelect)
        );

        var $daysInput = $("<input type='number' placeholder='days'  class='form-control input-sm' />");
        $prescriptionRow.append(
            $("<span class='prescriptionAdministrationDays'></span>").append($daysInput)
        );


        $prescriptionRow.append(
            $("<span class='prescriptionAmount'><input name='prescriptions[" + scriptIndex + "].amount' type='number' class='form-control input-sm'/></span> ")
        );

        $(".prescriptionWrap").append($prescriptionRow);

        prescriptionFeature.setupNewPrescriptionRow();
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


$(document).ready(function () {

    //set a global variable to track browser compatibility with image previews
    window.isFileReader = typeof FileReader !== 'undefined';

    calculateBMI();

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
    $('#modalNewImage').on('shown.bs.modal', function (e) {
        $('#modalTextEntry').focus();
    });

    //Hide event for Modal form
    $('#modalNewImage').on('hide.bs.modal', function (e) {
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
        height: 400,
        width: 450

    });

    $('#newVitalsBtn').click(function () {
        var id = $(this).attr('data-user_id');
        $.ajax({
            url: '/medical/newVitals',
            type: 'GET',
            success: function (partialView) {
                $('#newVitalsPartial').html(partialView);
                $('#newVitalsDialog').dialog("open");
            },
            error: function (response) {
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

/*
    $('#medicalSubmitBtn').click(function () {
        return photoNameFixup() && validate(); //validate from medicalClientValidation.js
    });
*/
    $("form").submit(function(event) {
        //validate from medicalClientValidation.js
        if (!photoNameFixup() || !validate()) {
            event.preventDefault();
            return false;
        }
        return true;
    });

    typeaheadFeature.setGlobalVariable("/search/typeahead/medicationsWithID").then(function() {
        prescriptionFeature.setupNewPrescriptionRow();
    });
    typeaheadFeature.setGlobalVariable("/search/typeahead/diagnoses").then(function () {
        typeaheadFeature.initalizeTypeAhead(problemFeature.newProblems.first(), 'diagnoses', true, true);
    });


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
            reader.onload = (function (theFile) {
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
        $('#modalImg').parent().append("<p>Image Preview is not supported in your browser.</p>")
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
    if (b == true) {
        //get a reference to the root div element (move up five places in this case)
        var rootDiv = $(e).parent().parent().parent().parent().parent().first();
        //get a reference to the data list
        var dataDiv = rootDiv.find("> div[name=dataList]").first();
        var photoIdInput = dataDiv.children("input[name=photoId]").first();
        var temptest = photoIdInput.val();
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
        dataList.children().each(function (y) {
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
