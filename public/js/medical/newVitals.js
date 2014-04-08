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

        if (bpSystolic.val() !== '') {
            newVitals.bpSystolic = bpSystolic.val();

            $("#triageBpSystolic").text(newVitals.bpSystolic);
            bpSystolic.val('');
        }

        if (bpDiastolic.val() !== '') {
            newVitals.bpDiastolic = bpDiastolic.val();

            $("#triageBpDiastolic").text(newVitals.bpDiastolic);
            bpDiastolic.val('');
        }

        if (heartRate.val() !== '') {
            newVitals.heartRate = heartRate.val();

            $("#triageHeartRate").text(newVitals.heartRate);
            heartRate.val('');
        }

        if (temperature.val() !== '') {
            newVitals.temperature = temperature.val();

            $("#triageTemperature").text(newVitals.temperature);
            temperature.val('');
        }

        if (oxygen.val() !== '') {
            newVitals.oxygen = oxygen.val();

            $("#triageOxygen").text(newVitals.oxygen);
            oxygen.val('');
        }

        if (respRate.val() !== '') {
            newVitals.respRate = respRate.val();

            $("#triageRespRate").text(newVitals.respRate);
            respRate.val('');
        }

        if (heightFt.val() !== '') {
            newVitals.heightFt = heightFt.val();

            $("#heightFeet").text(newVitals.heightFt);
            $("#triageHeightFt").text(newVitals.heightFt);
            heightFt.val('');
        }

        if (heightIn.val() !== '') {
            newVitals.heightIn = heightIn.val();

            $("#heightInches").text(newVitals.heightIn);
            $("#triageHeightIn").text(newVitals.heightIn);
            heightIn.val('');
        }

        if (weight.val() !== '') {
            newVitals.weight = weight.val();

            $("#weight").text(newVitals.weight);
            weight.val('');
        }

        if (glucose.val() !== '') {
            newVitals.glucose = glucose.val();
            $("#triageGlucose").text(newVitals.glucose);
            glucose.val('');
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