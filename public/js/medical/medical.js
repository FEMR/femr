//BMI auto- calculator

$(document).ready(function () {

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
        }
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
                alert("fatal error dear lord what have you done");
            }
        })

    });


});

function showTreatment() {
    $('#hpiControl').addClass('hidden');
    $('#pmhControl').addClass('hidden');
    $('#treatmentControl').removeClass('hidden');
}
function showHpi() {
    $('#pmhControl').addClass('hidden');
    $('#treatmentControl').addClass('hidden');
    $('#hpiControl').removeClass('hidden');
}

function showPmh() {
    $('#treatmentControl').addClass('hidden');
    $('#hpiControl').addClass('hidden');
    $('#pmhControl').removeClass('hidden');
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
