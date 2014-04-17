//BMI auto- calculator

$(document).ready(function () {
    //set a global variable to track browser compatibility with image previews
    window.isFileReader = typeof FileReader !== 'undefined';

    calculateBMI();

    //Unhides a prescription input box everytime
    //the + button is clicked (max of 5)
    $('#addPrescriptionButton').click(function () {
        var numberOfFilledPrescriptions = getNumberOfFilledScripts();
        if (numberOfFilledPrescriptions > 0 && ($("body").data("script") < numberOfFilledPrescriptions || typeof $("body").data("script") === "undefined")) {
            $("body").data("script", numberOfFilledPrescriptions);
        }

        if (typeof $("body").data("script") === "undefined") {
            $("body").data("script", 2);
        } else if ($("body").data("script") < 5) {
            $("body").data("script", $("body").data("script") + 1);
        } else {
            return;
        }
        $("#prescription" + $("body").data("script")).removeClass("hidden");
        $("#prescription" + $("body").data("script")).focus();
        return;
    });

    $('#subtractPrescriptionButton').click(function () {
        if (typeof $("body").data("script") === "undefined") {
            return;
        } else if ($("body").data("script") > 1) {
            $("#prescription" + $("body").data("script")).addClass("hidden");
            $("#prescription" + ($("body").data("script"))).val('');
            $("#prescription" + ($("body").data("script") - 1)).focus();
            $("body").data("script", $("body").data("script") - 1);
        }
        return;
    });

    //Unhides a problem input box everytime
    //the + button is clicked (max of 5)
    $('#addProblemButton').click(function () {
        var numberOfProblems = getNumberOfProblems();
        if (numberOfProblems > 0 && ($("body").data("prob") < numberOfProblems || typeof $("body").data("prob") === "undefined")) {
            $("body").data("prob", numberOfProblems);
        }


        if (typeof $("body").data("prob") === "undefined") {
            $("body").data("prob", 2);
        } else if ($("body").data("prob") < 5) {
            $("body").data("prob", $("body").data("prob") + 1);
        } else {
            return;
        }
        $("#problem" + $("body").data("prob")).removeClass("hidden");
        $("#problem" + $("body").data("prob")).focus();
        return;
    });

    $('#subtractProblemButton').click(function () {
        if (typeof $("body").data("prob") === "undefined") {
            return;
        } else if ($("body").data("prob") > 1) {
            $("#problem" + $("body").data("prob")).addClass("hidden");
            $("#problem" + ($("body").data("prob"))).val('');
            $("#problem" + ($("body").data("prob") - 1)).focus();
            $("body").data("prob", $("body").data("prob") - 1);
        }
        return;
    });

    //controls the tabbed viewing of HPI and Treatment
    $('#medicalTabs a').click(function (e) {
        e.preventDefault()
        $(this).tab('show')

    });

    $('#medicalTabs a').click(function () {
        if ($(this).attr('id') === "hpiTab") {
            showHpi();
        } else if ($(this).attr('id') === "treatmentTab") {
            showTreatment();
        } else if ($(this).attr('id') === "pmhTab") {
            showPmh();
        } else if ($(this).attr('id') === "photoTab") {
            showPhotoTab();
        }

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

    $('#medicalSubmitBtn').click(function () {
        return photoNameFixup();
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


});

function showTreatment() {
    $('#hpiControl').addClass('hidden');
    $('#pmhControl').addClass('hidden');
    $('#photoControl').addClass('hidden');
    $('#treatmentControl').removeClass('hidden');
}
function showHpi() {
    $('#pmhControl').addClass('hidden');
    $('#treatmentControl').addClass('hidden');
    $('#photoControl').addClass('hidden');
    $('#hpiControl').removeClass('hidden');
}

function showPmh() {
    $('#treatmentControl').addClass('hidden');
    $('#hpiControl').addClass('hidden');
    $('#photoControl').addClass('hidden');
    $('#pmhControl').removeClass('hidden');
}


function showPhotoTab() {
    $('#treatmentControl').addClass('hidden');
    $('#hpiControl').addClass('hidden');
    $('#pmhControl').addClass('hidden');
    $('#photoControl').removeClass('hidden');
}

function getNumberOfFilledScripts() {
    var x = 0;
    $('.prescription').each(function () {
        if ($(this).attr("readonly")) {
            x++;
        }
    });
    return x;
}

function getNumberOfProblems() {
    var x = 0;
    $('.problem').each(function () {
        if ($(this).attr("readonly")) {
            x++;
        }
    });
    return x;
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
    if (window.isFileReader){
        newPortrait.find('> div > img').replaceWith($('#modalImg').clone(true));
    }else{
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
