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

    $(".infoLabel").click(function () {

        //var label = $(this).parent("p").find("span");
        //var label = $(this).parent("p").child("span.medicalSurgicalHistory");

        // Get Label text from top html
        var label = $(this).text();
        //put label text in form label
        $("#edit-form").find(".form-label").text(label);

        // get value from top html
        var value = $(this).parent("p").find(".value").text();
        // put value in form
        $("#edit-form").find("input.value").val(value);

        // need to get form text field name
        var id = $(this).parent("p").find(".value").attr("id");
        $("#edit-form").find("input.fieldId").val(id);

        $("#edit-form").show();
    });

    $("#saveEncounterBtn").click(function () {
        var fieldValue = $('#editInput').val();
        // var form = $(this);
        //var label = $(this).text();
        var fieldName = $('#fieldIdInput').val();
        //
        $.ajax({
            type: "Post",
            url: 'history/encounter/updateField/' + $('#patientEncounterId').val(),
            data: {fieldValue: fieldValue, fieldName: fieldName}
        }).done(function () {

            $("#edit-form").hide();


        })


    });


    $("#cancelEncounterBtn").click(function () {

        $("#edit-form").hide();
    });


    function closeDialog(name) {
        $('#' + name).dialog("close");

    }


});