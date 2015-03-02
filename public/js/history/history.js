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
        ($("#edit-form").find("input.value").val(value);

        // need to get form text field name

        $("#edit-form").show();
    });



   // $("#saveEncounterBtn").on('click', function() {

     //   var that = $(this)






   // });


    $("#saveEncounterBtn").click(function() {

        var form = $(this);
        var label = $(this).text();
        $.ajax({
            type: form.attr('method'),
            url: form.attr('action'),
            data: form.serialize()
        }).done(function() {
            //alert("Saved Successfully");
            var value = $(this).parent("p").find(".value").text();


            var value2 = $("#edit-form").find("input.value").val(value);

         value = value2;

            $("#edit-form").hide();



        })


    });







    $("#cancelEncounterBtn").click(function(){

        $("#edit-form").hide();
    });

    $('#historySaveBtn').click(function () {
        var newAssessments = {};

        var patientAssessments = {
            //this object is sent to the vital validator which uses
            //the names of these field
            MedicalSurgicalHistory: $('#MedicalSurgicalHistory'),
            SocialHistory: $('#SocialHistory'),
            CurrentMedications: $('#CurrentMedications'),
            FamilyHistory: $('#FamilyHistory'),
            Assessment: $('#Assessment'),
            Treatment: $('#Treatment'),
            Onset: $('#Onset'),
            Quality: $('#Quality'),
            Radiation: $('#Radiation'),
            Severity: $('#Severity'),
            Provokes: $('#Provokes'),
            Palliates: $('#Palliates'),
            TimeOfDay: $('#TimeOfDay'),
            Narrative: $('#Narrative'),
            PhysicalExamination: $('#PhysicalExamination')

        };



    var isValid = vitalClientValidator(patientAssessments);

    if (isValid === true) {
        if (patientAssessments.MedicalSurgicalHistory.val() !== '') {
            newAssessments.MedicalSurgicalHistory = patientAssessments.MedicalSurgicalHistory.val();
        }

        if (patientVitals.SocialHistory.val() !== '') {
            newAssessments.SocialHistory = patientAssessments.SocialHistory.val();
        }

        if (patientVitals.CurrentMedications.val() !== '') {
            newAssessments.CurrentMedications = patientAssessments.CurrentMedications.val();
        }

        if (patientVitals.FamilyHistory.val() !== '') {
            newAssessments.FamilyHistory = patientAssessments.FamilyHistory.val();
        }

        if (patientVitals.Assessment.val() !== '') {
            newAssessments.Assessment = patientAssessments.Assessment.val();
        }

        if (patientVitals.Treatment.val() !== '') {
            newAssessments.Treatment = patientAssessments.Treatment.val();
        }

        if (patientVitals.Onset.val() !== '') {
            newAssessments.Onset = patientAssessments.Onset.val();
        }

        if (patientVitals.Quality.val() !== '') {
            newAssessments.Quality = patientAssessments.Quality.val();
        }

        if (patientVitals.Radiation.val() !== '') {
            newAssessments.Radiation = patientAssessments.Radiation.val();
        }

        if (patientVitals.Severity.val() !== '') {
            newAssessments.Severity = patientAssessments.Severity.val();
        }

        if (patientVitals.Provokes.val() !== '') {
            newAssessments.Provokes = patientAssessments.Provokes.val();
        }

        if (patientVitals.Palliates.val() !== '') {
            newAssessments.Palliates = patientAssessments.Palliates.val();
        }

        if (patientVitals.TimeOfDay.val() !== '') {
            newAssessments.TimeOfDay = patientAssessments.TimeOfDay.val();

        }

        if (patientVitals.Narrative.val() !== '') {
            newAssessments.Narrative = patientAssessments.Narrative.val();
        }


        if (patientVitals.PhysicalExamination.val() !== '') {
            newAssessments.PhysicalExamination = patientAssessments.PhysicalExamination.val();


        }

        $.ajax({
            url: '/history/encounter/' + $("#patientId").val(),
            type: 'POST',
            data: newAssessments,
            dataType: 'json'
        }).done(function () {
            closeDialog("newAssessmentDialog");
            $.ajax({
                url: '/history/edit-encounter/' + $('#patientId').val(),
                type: 'GET',
                success: function (partialView) {
                    $('#assessmentsPartial').html(partialView);
                }
            })
        });
    }
        });
 });

function closeDialog(name) {
    $('#' + name).dialog("close");

}



