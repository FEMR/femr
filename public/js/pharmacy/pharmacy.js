$(document).ready(function () {

    $('.replaceBtn').click(function () {
        replaceClick(this);
    });

    typeaheadFeature.setGlobalVariable("/search/typeahead/medications");
});


function replaceClick(btn) {

    var medicationListElementId = $(btn).parent().attr("id");
    var medicationNumber = medicationListElementId.substr(medicationListElementId.length - 1);
    var replacementInputBox = $("#replacementMedication" + medicationNumber);

    if ($(replacementInputBox).hasClass('hidden')) {
        //show replacement input box
        $(replacementInputBox).removeClass('hidden');
        //initalize typeahead for the input
        typeaheadFeature.initalizeTypeAhead(replacementInputBox, "medication", true, true);
    } else {
        //hide the replacement input box
        $(replacementInputBox).addClass('hidden');
        $(replacementInputBox).val("");
        //eliminate typeahead for the input
        typeaheadFeature.destroyTypeAhead(replacementInputBox);
    }
}

