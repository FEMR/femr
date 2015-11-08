$(document).ready(function () {
    $('#cancelVitalsBtn').click(function () {
        closeDialog("newVitalsDialog");
    });

    $('#saveVitalsBtn').on('click', function () {
        var newVitals = {};

        var patientVitals = {
            //this object is sent to the vital validator which uses
            //the names of these fields
            respiratoryRate: $('#respRate'),
            bloodPressureSystolic: $('#bpSystolic'),
            bloodPressureDiastolic: $('#bpDiastolic'),
            heartRate: $('#heartRate'),
            oxygenSaturation: $('#oxygen'),
            temperature: $('#temperature'),
            weight: $('#newWeight'),
            heightFeet: $('#heightFt'),
            heightInches: $('#heightIn'),
            glucose: $('#newGlucose')
        };

        var isValid = vitalClientValidator(patientVitals);

        if (isValid === true) {
            if (patientVitals.bloodPressureSystolic.val() !== '') {
                newVitals.bloodPressureSystolic = patientVitals.bloodPressureSystolic.val();
            }

            if (patientVitals.bloodPressureDiastolic.val() !== '') {
                newVitals.bloodPressureDiastolic = patientVitals.bloodPressureDiastolic.val();
            }

            if (patientVitals.heartRate.val() !== '') {
                newVitals.heartRate = patientVitals.heartRate.val();
            }

            if (patientVitals.temperature.val() !== '') {
                newVitals.temperature = patientVitals.temperature.val();
            }

            if (patientVitals.oxygenSaturation.val() !== '') {
                newVitals.oxygenSaturation = patientVitals.oxygenSaturation.val();
            }

            if (patientVitals.respiratoryRate.val() !== '') {
                newVitals.respiratoryRate = patientVitals.respiratoryRate.val();
            }

            if (patientVitals.heightFeet.val() !== '') {
                newVitals.heightFeet = patientVitals.heightFeet.val();
            }

            if (patientVitals.heightInches.val() !== '') {
                newVitals.heightInches = patientVitals.heightInches.val();
            }

            if (patientVitals.weight.val() !== '') {
                newVitals.weight = patientVitals.weight.val();
            }

            if (patientVitals.glucose.val() !== '') {
                newVitals.glucose = patientVitals.glucose.val();
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
        }
    });
});

function closeDialog(name) {
    $('#' + name).dialog("close");
}



