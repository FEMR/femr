var meds;
$(document).ready(function () {

    $('.replaceBtn').click(function(){
        replaceClick(this);
    });

    //get medications
    $.getJSON("/pharmacy/typeahead", function (data) {
        meds = data;
    });
});


function replaceClick(btn){

    var medicationListElementId = $(btn).parent().attr("id");
    var medicationNumber = medicationListElementId.substr(medicationListElementId.length - 1);
    var replacementInputBox = $("#replacementMedication" + medicationNumber);

    if ($(replacementInputBox).hasClass('hidden')){
        //show replacement input box
        $(replacementInputBox).removeClass('hidden');
        //initalize typeahead for the input
        typeaheadFeature.initalizeTypeAhead(replacementInputBox, "medication", meds);
    }else{
        //hide the replacement input box
        $(replacementInputBox).addClass('hidden');
        $(replacementInputBox).val("");
        //eliminate typeahead for the input
        typeaheadFeature.destroyTypeAhead(replacementInputBox);
    }
}

