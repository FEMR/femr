/**
 * Created by Danny on 1/26/14.
 * adds bootstrap functionality to encounter page
 */

// This is for the showEncounter.scala.html page
$(document).ready(function () {
    //controls the tabbed viewing of HPI and Treatment
    $('#encounterTabs a').click(function (e) {
        e.preventDefault()
        $(this).tab('show')

    });

    $('#encounterTabs a').click(function () {
        if ($(this).attr('id') === "hpiTab") {
            showHpi();
        } else if ($(this).attr('id') === "treatmentTab") {
            showTreatment();
        } else if ($(this).attr('id') === "pmhTab") {
            showPmh();
        } else if($(this).attr('id') === "photoTab") {
            showPhotos();
        } else if($(this).attr('id') === "pharmacyTab"){
            showPharamcy();
        }
    });



});

// implement the functions to show and hide the tabes on the encounters page

function showTreatment() {
    $('#hpiControl').addClass('hidden');
    $('#pmhControl').addClass('hidden');
    $('#pharmacyControl').addClass('hidden');
    $('#photoControl').addClass('hidden');
    $('#treatmentControl').removeClass('hidden');
}
function showHpi() {
    $('#pmhControl').addClass('hidden');
    $('#treatmentControl').addClass('hidden');
    $('#pharmacyControl').addClass('hidden');
    $('#photoControl').addClass('hidden');
    $('#hpiControl').removeClass('hidden');
}

function showPmh() {
    $('#treatmentControl').addClass('hidden');
    $('#hpiControl').addClass('hidden');
    $('#pharmacyControl').addClass('hidden');
    $('#photoControl').addClass('hidden');
    $('#pmhControl').removeClass('hidden');
}
function showPharamcy() {
    $('#hpiControl').addClass('hidden');
    $('#pmhControl').addClass('hidden');
    $('#treatmentControl').addClass('hidden');
    $('#photoControl').addClass('hidden');
    $('#pharmacyControl').removeClass('hidden');
}

function showPhotos() {
    $('#hpiControl').addClass('hidden');
    $('#pmhControl').addClass('hidden');
    $('#treatmentControl').addClass('hidden');
    $('#pharmacyControl').addClass('hidden');
    $('#photoControl').removeClass('hidden');
}