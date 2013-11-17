$(document).ready(function () {
    $('.toggleReplacement').click(function () {
        if ($(this).attr('id').substr(0, 1) == "y") {
            yesReplacement($(this).attr('id').substr(1, 2));
        }
        else {
            noReplacement($(this).attr('id').substr(1, 2));
        }
    });





});

function yesReplacement(id) {
    $('#replacementMedication' + id).removeClass('hidden');
    $('#replacementAmount' + id).removeClass('hidden');


    $.getJSON("/pharmacy/typeahead", function(data){
        $('#replacementMedication' + id).typeahead({
            name: 'medication',
            local: data
        });


        //console.log(data);
    });
    //initalize typeahead


}

function noReplacement(id) {
    $('#replacementMedication' + id).addClass('hidden');
    $('#replacementAmount' + id).addClass('hidden');
    $('#replacementMedication' + id).val('');

    //remove typeahead while no button is clicked
    $('#replacementMedication' + id).typeahead('destroy');
}

