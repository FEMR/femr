/**
 * Created by Danny on 1/26/14.
 * adds bootstrap functionality to encounter page
 */

// This is for the showEncounter.scala.html page
$(document).ready(function () {

$('#myTab a').click(function (e) {
    e.preventDefault()
    $(this).tab('show')

});
    //this function handles the switching of the tabs in the encounter view
    $('#myTab a').click(function () {
        if ($(this).attr('id') === "hpiTab") {
            showHpi();
        } else if ($(this).attr('id') === "treatmentTab") {
            showTreatment();
        } else if($(this).attr('id') === "pmhTab") {
            showPmh();
        }
        else if($(this).attr('id') === "pharmacyTab"){
            showPharamcy();
        }
    });

});

// implement the functions to show and hide the tabes on the encounters page

function showTreatment() {
    $('#hpiControl').addClass('hidden');
    $('#pmhControl').addClass('hidden');
    $('#pharmacyControl').addClass('hidden');
    $('#treatmentControl').removeClass('hidden');
}
function showHpi() {
    $('#pmhControl').addClass('hidden');
    $('#treatmentControl').addClass('hidden');
    $('#pharmacyControl').addClass('hidden');
    $('#hpiControl').removeClass('hidden');
}

function showPmh() {
    $('#treatmentControl').addClass('hidden');
    $('#hpiControl').addClass('hidden');
    $('#pharmacyControl').addClass('hidden');
    $('#pmhControl').removeClass('hidden');
}
function showPharamcy() {
    $('#hpiControl').addClass('hidden');
    $('#pmhControl').addClass('hidden');
    $('#treatmentControl').addClass('hidden');
    $('#pharmacyControl').removeClass('hidden');
}