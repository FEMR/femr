var editEncounterFeature = {
    loadFieldHistory: function (encounterID, fieldName, complaint) {
        $.ajax({
            type: "GET",
            url: '/history/encounter/listTabFieldHistory/' + encounterID,
            data: {FieldName: fieldName, ChiefComplaintName: complaint}
        }).done(function (partialView) {
            return $("#tabFieldHistory").html(partialView);
        });
    },
    saveField: function (fieldName, fieldValue, complaint) {

        $.ajax({
            type: "POST",
            url: '/history/encounter/updateField/' + $('#patientEncounterId').val(),
            data: {FieldValue: fieldValue, FieldName: fieldName, ChiefComplaintName: complaint}
        }).done(function () {
            if (complaint == null) // No chief complaint. Easy to update fields value
                $("span[data-id='" + fieldName + "']").text(fieldValue);
            else //Chief complaint so we need to update value of specific complaint's field
                $("h4[data-complaint='" + complaint + "']")
                    .parent()
                    .find("span[data-id='" + fieldName + "']")
                    .text(fieldValue);


            // Close edit form
            $("#edit-form").hide();
        });
    },
    showEditDialog: function (thiss) {
        // Get Label text from top html
        var label = $(thiss).text();
        var form = $("#edit-form");
        //put label text in form label
        form.find(".form-label").text(label);

        // get value from top html
        var value = $(thiss).parent("p").find(".value").text();
        // put value in form
        form.find("input.value").val($.trim(value));

        // need to get form text field name
        var fieldName = $(thiss).parent("p").find(".value").attr("data-id");
        $("#fieldIdInput").val(fieldName);

        // Set complaint to chiefComplaint (if there is one)
        var complaint = null;
        var $complaintHeader = $(thiss).parent().siblings("h4");
        if ($complaintHeader.length > 0)
            complaint = $complaintHeader.attr("data-complaint");
        form.data("complaint", complaint);

        editEncounterFeature.loadFieldHistory($('#patientEncounterId').val(), fieldName, complaint);

        form.show();

        // Set focus to input
        form.find("input.value").focus().select();
    },
    hideEditDialog: function () {

        $("#edit-form").hide();
    }
};

$(document).ready(function () {

    //this function was written in November of 2013 and that's all i know about it
    $(".date").each(function (i) {

        var date = $('#date');
        $(date).text($(date).text().slice(0, -10));
        $(this).attr('id', "date" + (i + 1));
    });

    //selects a patient from the duplicate patient search to be seen in triage
    $('.selectPageFromRow').click(function () {

        var id = $.trim($(this).parent().parent().find('.patientId').html());
        document.getElementById("nameOrIdSearchForm").value = id;
        document.getElementById("searchBtn").click();
    });

    //this is used to get the dialog for editing a field
    $(".infoLabel.editable").click(function () {

        editEncounterFeature.showEditDialog($(this));
    });

    //this is used for saving an encounter field replacement
    $("#saveEncounterBtn").click(function () {

        var fieldName = $('#fieldIdInput').val();
        var fieldValue = $('#editInput').val();
        var complaint = $("#edit-form").data("complaint");
        editEncounterFeature.saveField(fieldName, fieldValue, complaint);
    });

    //this is used to remove the dialog for editing a field
    $("#cancelEncounterBtn").click(function () {

        editEncounterFeature.hideEditDialog();
    });

    $('#deletePatientBtn').click(function () {
        var msg = "Please enter the reason for deleting this record below:";
        var reason;

        do {
            if (reason = prompt(msg)) {
                $('#reasonDeleted').val(reason);
                $("#deletePatient").click();
            } else if (!msg.includes('***')) {
                msg += "\n\n***A reason must be provided in order to delete a patient record***";
            }
        } while (reason === "");
    });
    $('.deleteEncounterbtn').click(function () {
        var msg = "Please enter the reason for deleting this encounter:";
        var reason;

        do {
            if (reason = prompt(msg)) {
                $('.reasonEncounterDeleted').val(reason);
                $(".deleteEncounter").click();
            } else if (!msg.includes('***')) {
                msg += "\n\n***A reason must be provided in order to delete a patient encounter***";
            }
            else{

            }
        } while (reason === "");
    });
});