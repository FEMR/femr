$(document).ready(function () {

    var patientVitals = {
        //this object is sent to the vital validator which uses
        //the names of these fields (these fields are from the newVitals prompt)
        respiratoryRate: $('#newRespiratoryRate'),
        bloodPressureSystolic: $('#newSystolic'),
        bloodPressureDiastolic: $('#newDiastolic'),
        heartRate: $('#newHeartRate'),
        oxygenSaturation: $('#newOxygen'),
        temperature: $('#newTemperature'),
        weight: $('#newWeight'),
        heightFeet: $('#newHeightFeet'),
        heightInches: $('#newHeightInches'),
        glucose: $('#newGlucose'),
        weeksPregnant: $('#weeksPreg'),
        smoker: $('#newSmoker'),
        diabetic: $('#newDiabetic'),
        alcohol: $('#newAlcohol'),
        cholesterol: $('#newCholesterol'),
        hypertension: $('#newHypertension')
    };

    $('#cancelVitalsBtn').click(function () {
        closeDialog("newVitalsDialog");
    });

    $('#saveVitalsBtn').on('click', function () {

        var newVitals = {};

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

            if (patientVitals.weeksPregnant.val() !== '') { /*Sam Zanni*/
                newVitals.weeksPregnant = patientVitals.weeksPregnant.val();
            }
            // Osman

            var checkSmoker = document.getElementById("newSmoker").checked;
            var checkDiabetic = document.getElementById("newDiabetic").checked;
            var checkAlcohol = document.getElementById("newAlcohol").checked;
            var checkCholesterol = document.getElementById("newCholesterol").checked;
            var checkHypertension = document.getElementById("newHypertension").checked;

            newVitals.smoker = checkSmoker === true ? "1" : null;
            newVitals.diabetic = checkDiabetic === true ? "1" : null;
            newVitals.cholesterol = checkCholesterol === true ? "1" : null;
            newVitals.hypertension = checkHypertension === true ? "1" : null;

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


    $("#newHeightInches").change(function(){
        var isMetric = ($("#vitalsUnits").val() === "metric");

        var heightFeet = parseFloat(patientVitals.heightFeet.val()) || 0;
        var heightInches = parseFloat(patientVitals.heightInches.val()) || 0;

        var unitValue = isMetric ? 100 : 12;

        // if inches > 12 or 100 add to feet
        if( heightInches > unitValue ){
            heightFeet += Math.floor(heightInches/unitValue);
            heightInches = heightInches % unitValue;
        }

        $(patientVitals.heightFeet).val(heightFeet || "");
        $(patientVitals.heightInches).val(heightInches || "");
    });
});

function closeDialog(name) {
    $('#' + name).dialog("close");
}




