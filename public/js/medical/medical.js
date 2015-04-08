
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
    allPrescriptions: $('.prescriptionWrap > input'),
    refreshSelectors: function () {
        prescriptionFeature.allPrescriptions = $(prescriptionFeature.allPrescriptions.selector);
    },
    initialize: function() {
        prescriptionFeature.allPrescriptions.last().combobox({
            select: function(event, ui) {
                $(this).val(ui.item.id);
            }
        });
    },
    getNumberOfNonReadonlyPrescriptionFields: function () {
        prescriptionFeature.refreshSelectors();
        var number = 0;
        $(prescriptionFeature.allPrescriptions).each(function () {
            if (!$(this).is('[readonly]')) {
                number++;
            }
        });
        return number;
    },
    addPrescriptionField: function () {
        var scriptIndex = prescriptionFeature.getNumberOfNonReadonlyPrescriptionFields();
        var $newPrescription = $("<input name='prescriptions[" + scriptIndex + "].name' type='text' class='form-control input-sm'/>");

        $(".prescriptionWrap").append($newPrescription);

        /* Turn the input into a custom combobox */
        $newPrescription.combobox({
            select: function(event, ui) {
                $(this).val(ui.item.id);
            }
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
    prescriptionFeature.initialize();

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


    $('#medicalSubmitBtn').click(function () {
        return photoNameFixup() && validate(); //validate from medicalClientValidation.js
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

(function( $ ) {
    $.widget( "custom.combobox", {
        _create: function() {
            var self = this;
            this.wrapper = $( "<span></span>" )
                .addClass( "custom-combobox" )
                .insertAfter( this.element );
            this.element.hide();
            this._createAutocomplete();
            this._createShowAllButton();

            this.input.data("ui-autocomplete")._renderItem = function(ul, item) {
                return $("<li></li>").data("item.autocomplete", item).append("<a>" + item.label + "</a>").appendTo(ul);
            };
        },

        _createAutocomplete: function() {
            var value = "";

            this.input = $( "<input>" )
                .appendTo( this.wrapper )
                .val( value )
                .attr( "title", "" )
                .addClass( "custom-combobox-input ui-widget ui-widget-content ui-state-default ui-corner-left" )
                .autocomplete({
                    delay: 0,
                    minLength: 0,
                    source: function(request, response) {
                        $.ajax({
                            url: "/medical/getMedication?term=" + request.term,
                            type: "GET",
                            dataType: "json",
                            success: function (data) {
                                response($.map(data.page_data, function (item) {
                                    return {
                                        label: item.name + " (" + item.form + ")",
                                        value: item.name + " (" + item.form + ")",
                                        name: item.name,
                                        id: item.id
                                    }
                                }));
                            }
                        })
                    },
                    select: function(event, ui) {}
                })
                .tooltip({
                    tooltipClass: "ui-state-highlight"
                });

            this._on( this.input, {
                autocompleteselect: function( event, ui ) {
                    this._trigger( "select", event, {
                        item: ui.item
                    });
                },
                autocompletechange: "_removeIfInvalid"
            });


        },

        _createShowAllButton: function() {
            var input = this.input,
                wasOpen = false;

            $( "<a></a>" )
                .attr( "tabIndex", -1 )
                .attr( "title", "Show All Items" )
                .tooltip()
                .appendTo( this.wrapper )
                .button({
                    icons: {
                        primary: "ui-icon-triangle-1-s"
                    },
                    text: false
                })
                .removeClass( "ui-corner-all" )
                .addClass( "custom-combobox-toggle ui-corner-right" )
                .mousedown(function() {
                    wasOpen = input.autocomplete( "widget" ).is( ":visible" );
                })
                .click(function() {
                    input.focus();

                    // Close if already visible
                    if ( wasOpen ) {
                        return;
                    }

                    // Pass empty string as value to search for, displaying all results
                    input.autocomplete( "search", "" );
                });
        },



        _removeIfInvalid: function( event, ui ) {
            // Selected an item, nothing to do
            if ( ui.item ) {
                return;
            }

            if (!ui.item) {
                // Search for a match (case-insensitive)
                var value = this.input.val(),
                    valueLowerCase = value.toLowerCase(),
                    valid = false;

                var autocomplete = this.input.data("ui-autocomplete");
                autocomplete.widget().children(".ui-menu-item").each(function () {
                    var item = $(this).data("item.autocomplete");
                    if (item.name.toLowerCase() == valueLowerCase) {
                        ui.item = item;
                        valid = true;
                        return false;
                    }
                });
            }
            // Found a match, nothing to do
            if ( ui.item ) {
                this.input.val(ui.item.label);
                this._trigger( "select", event, {
                    item: ui.item
                });
                return;
            }

            // Remove invalid value
            this.input
                .val( "" )
                .attr( "title", value + " didn't match any item" )
                .tooltip( "open" );
            this.element.val( "" );
            this._delay(function() {
                this.input.tooltip( "close" ).attr( "title", "" );
            }, 2500 );
        },

        _destroy: function() {
            this.wrapper.remove();
            this.element.show();
        }
    });
})( jQuery );