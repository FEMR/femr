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

    $('#historySaveBtn').click(function () {
        var newAssessments = {};

        var patientAssessments = {
            //this object is sent to the vital validator which uses
            //the names of these fields
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



