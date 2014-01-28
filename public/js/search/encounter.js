/**
 * Created by Danny on 1/26/14.
 * adds bootstrap functionality to encounter page
 */

$(document).ready(function () {

$('#myTab a').click(function (e) {
    e.preventDefault()
    $(this).tab('show')

});
    //this function handles the switching of the tabs in the encounter view
    $('#myTab a').click(function () {
        if ($(this).attr('id') === "EncounterInfoTab"){
            showEncounterInfo();
        }
        else if ($(this).attr('id') === "hpiTab") {
            showHpi();
        } else if ($(this).attr('id') === "treatmentTab") {
            showTreatment();
        } else if($(this).attr('id') === "pmhTab") {
            showPmh();
        }
    });

});

// implement the functions to show and hide the tabes on the encounters page

function showEncounterInfo() {
    $('#hpiControl').addClass('hidden');
    $('#treatmentControl').addClass('hidden');
    $('#pmhControl').addClass('hidden');
    $('#encounterControl').removeClass('hidden');
}
function showTreatment() {
    $('#encounterControl').addClass('hidden');
    $('#hpiControl').addClass('hidden');
    $('#pmhControl').addClass('hidden');
    $('#treatmentControl').removeClass('hidden');
}
function showHpi() {
    $('#encounterControl').addClass('hidden');
    $('#pmhControl').addClass('hidden');
    $('#treatmentControl').addClass('hidden');
    $('#hpiControl').removeClass('hidden');
}

function showPmh() {
    $('#encounterControl').addClass('hidden');
    $('#treatmentControl').addClass('hidden');
    $('#hpiControl').addClass('hidden');
    $('#pmhControl').removeClass('hidden');
}