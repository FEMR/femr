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

    var loadAssessmentHistory = function(encounterID, fieldName, complaint) {
        $.ajax({
            type: "GET",
            url: '/history/encounter/listTabFieldHistory/' + encounterID,
            data: { FieldName: fieldName, ChiefComplaintName: complaint }
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

        // Set complaint to chiefComplaint (if there is one)
        var complaint = null;
        var $complaintHeader = $(this).parent().siblings("h4");
        if ($complaintHeader.length > 0)
            complaint = $complaintHeader.attr("data-complaint");
        $("#edit-form").data("complaint", complaint);

        loadAssessmentHistory($('#patientEncounterId').val(), fieldName, complaint);

        $("#edit-form").show();

        // Set focus to input
        $("#edit-form").find("input.value").focus().select();
    });

    $("#saveEncounterBtn").click(function () {
        var fieldValue = $('#editInput').val();
        var fieldName = $('#fieldIdInput').val();
        var complaint = $("#edit-form").data("complaint");

        $.ajax({
            type: "POST",
            url: '/history/encounter/updateField/' + $('#patientEncounterId').val(),
            data: { FieldValue: fieldValue, FieldName: fieldName, ChiefComplaintName: complaint }
        }).done(function (data) {
            if (complaint == null) // No chief complaint. Easy to update fields value
                $("span[data-id='" + fieldName + "']").text(fieldValue);
            else //Chief complaint so we need to update value of specific complaint's field
                $("h4[data-complaint='" + complaint + "']")
                    .parent()
                    .find("span[data-id='" + fieldName + "']")
                    .text(fieldValue);

            // Update field to new value
            //$(".encounterViewBody span[data-id='" + fieldName + "']").text(fieldValue);

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