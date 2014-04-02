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
    if ($(btn).next().hasClass('hidden')){
        $(btn).next().removeClass('hidden');
        //initalize typeahead
        $(btn).next().typeahead({
            name: 'medication',
            local: meds
        });
    }else{
        $(btn).next().addClass('hidden');
        //eliminate typeahead
        $(btn).next().typeahead('destroy');
    }
}

