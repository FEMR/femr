$(document).ready(function () {
    $(".date").each(function (i) {
        $('#date').text($('#date').text().slice(0, -10))
        $(this).attr('id', "date" + (i + 1));
    });

    $('#newPatientBtn').click(function () {
        window.location = "/triage";
    });

    $('#EditHistory').click(function () {
        window.location = "/triage";
    });

   // $('#tabFieldHistory').tableScroll({ height: 800});

    var loadAssessmentHistory = function(encounterID, fieldName) {
        $.ajax({
            type: "GET",
            url: '/history/encounter/listTabFieldHistory/' + encounterID,
            data: { FieldName: fieldName }
        }).done(function (partialView) {
            $("#tabFieldHistory").html(partialView);
        });
    }

    $(".infoLabel.editable").click(function () {
        // Get Label text from top html
        var label = $(this).text();

        //put label text in form label
        $("#edit-form").find(".form-label").text(label);

        // get value from top html
        var value = $(this).parent("p").find(".value").text();
        // put value in form
        $("#edit-form").find("input.value").val($.trim(value));

        // need to get form text field name
        var fieldName = $(this).parent("p").find(".value").attr("data-id");
        $("#fieldIdInput").val(fieldName);

        loadAssessmentHistory($('#patientEncounterId').val(), fieldName);

        $("#edit-form").show();

        // Set focus to input
        $("#edit-form").find("input.value").focus().select();
    });

    $("#saveEncounterBtn").click(function () {
        var fieldValue = $('#editInput').val();
        // var form = $(this);
        //var label = $(this).text();
        var fieldName = $('#fieldIdInput').val();
        console.log(fieldName, " ", fieldValue);
        $.ajax({
            type: "POST",
            url: '/history/encounter/updateField/' + $('#patientEncounterId').val(),
            data: { FieldValue: fieldValue, FieldName: fieldName}
        }).done(function (data) {
            // Update field to new value
            $(".encounterViewBody span[data-id='" + fieldName + "']").text(fieldValue);

            // Close edit form
            $("#edit-form").hide();
        });


    });


    $("#cancelEncounterBtn").click(function () {

        $("#edit-form").hide();
    });


    function closeDialog(name) {
        $('#' + name).dialog("close");

    }


});