$(document).ready(function () {
    $('#cancelVitalsBtn').click(function () {
        closeDialog("newVitalsDialog");
    });


    $('#saveVitalsBtn').on('click', function () {
        var newVitals = {};

        var bloodPressureSystolic = $('#bpSystolic');
        var bloodPressureDiastolic = $('#bpDiastolic');
        var heartRate = $('#heartRate');
        var temperature = $('#temperature');
        var respiratoryRate = $('#respRate');
        var oxygen = $('#oxygen');
        var heightFeet = $('#heightFt');
        var heightInches = $('#heightIn');
        var weight = $('#newWeight');
        var glucose = $('#newGlucose');

        if (bloodPressureSystolic.val() !== '') {
            newVitals.bloodPressureSystolic = bloodPressureSystolic.val();
        }

        if (bloodPressureDiastolic.val() !== '') {
            newVitals.bloodPressureDiastolic = bloodPressureDiastolic.val();
        }

        if (heartRate.val() !== '') {
            newVitals.heartRate = heartRate.val();
        }

        if (temperature.val() !== '') {
            newVitals.temperature = temperature.val();
        }

        if (oxygen.val() !== '') {
            newVitals.oxygenSaturation = oxygen.val();
        }

        if (respiratoryRate.val() !== '') {
            newVitals.respiratoryRate = respiratoryRate.val();
        }

        if (heightFeet.val() !== '') {
            newVitals.heightFeet = heightFeet.val();
        }

        if (heightInches.val() !== '') {
            newVitals.heightInches = heightInches.val();
        }

        if (weight.val() !== '') {
            newVitals.weight = weight.val();
        }

        if (glucose.val() !== '') {
            newVitals.glucose = glucose.val();
        }

        $.ajax({
            url: '/medical/updateVitals/' + $("#patientId").val(),
            type: 'POST',
            data: newVitals,
            dataType: 'json'
        }).done(function () {
                closeDialog("newVitalsDialog");
                $.ajax({
                    url: '/medical/listVitals/' + $('#patientId').val(),
                    type: 'GET',
                    success: function (partialView) {
                        $('#vitalsPartial').html(partialView);
                    }
                })
            });
    });
});

function closeDialog(name) {
    $('#' + name).dialog("close");
}