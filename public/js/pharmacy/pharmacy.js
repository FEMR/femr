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
    console.log();
    var medicationListElementId = $(btn).parent().attr("id");
    var medicationNumber = medicationListElementId.substr(medicationListElementId.length - 1);
    var replacementInputBox = $("#replacementMedication" + medicationNumber);


    if ($(replacementInputBox).hasClass('hidden')){
        $(replacementInputBox).removeClass('hidden');
        //initalize typeahead
        $(replacementInputBox).typeahead({
            name: 'medication',
            local: meds
        });
    }else{
        $(replacementInputBox).addClass('hidden');
        $(replacementInputBox).val("");
        //eliminate typeahead
        $(replacementInputBox).typeahead('destroy');
    }
}

