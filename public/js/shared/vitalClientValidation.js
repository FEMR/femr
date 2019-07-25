var vitalClientValidator = function (vitalElements) {
    var isMetric = ($("#vitalsUnits").val() == "metric");
    var isValid = true;

    // remove all errors before validating
    $("#vitalContainer .vitalWrap").removeClass("has-errors");
    $("#vitalContainer label.range-message").remove();

    //Respirations
    if (vitalIsInvalid("respiratoryRate", vitalElements.respiratoryRate.val(), isMetric)) {
        $(vitalElements.respiratoryRate).parent(".vitalWrap").addClass("has-errors");
        $(vitalElements.respiratoryRate).before(getRangeMessage("respiratoryRate", isMetric));
        isValid = false;
    }
    //Blood Pressure - Systolic
    if (vitalIsInvalid("bloodPressureSystolic", vitalElements.bloodPressureSystolic.val(), isMetric)) {
        $(vitalElements.bloodPressureSystolic).parents(".vitalWrap").addClass("has-errors");
        $(vitalElements.bloodPressureSystolic).before(getRangeMessage("bloodPressureSystolic", isMetric));
        isValid = false;
    }
    //Blood Pressure - Diastolic
    if (vitalIsInvalid("bloodPressureDiastolic", vitalElements.bloodPressureDiastolic.val(), isMetric)) {
        $(vitalElements.bloodPressureDiastolic).parents(".vitalWrap").addClass("has-errors");
        $(vitalElements.bloodPressureDiastolic).before(getRangeMessage("bloodPressureDiastolic", isMetric));
        isValid = false;
    }
    //Heart Rate
    if (vitalIsInvalid("heartRate", vitalElements.heartRate.val(), isMetric)) {
        $(vitalElements.heartRate).parent(".vitalWrap").addClass("has-errors");
        $(vitalElements.heartRate).before(getRangeMessage("heartRate", isMetric));
        isValid = false;
    }
    //Oxygen
    if (vitalIsInvalid("oxygenSaturation", vitalElements.oxygenSaturation.val(), isMetric)) {
        $(vitalElements.oxygenSaturation).parent(".vitalWrap").addClass("has-errors");
        $(vitalElements.oxygenSaturation).before(getRangeMessage("oxygenSaturation", isMetric));
        isValid = false;
    }
    //Temperature
    if (vitalIsInvalid("temperature", vitalElements.temperature.val(), isMetric)) {
        $(vitalElements.temperature).parent(".vitalWrap").addClass("has-errors");
        $(vitalElements.temperature).before(getRangeMessage("temperature", isMetric));
        isValid = false;
    }
    //Weight
    if (vitalIsInvalid("weight", vitalElements.weight.val(), isMetric)) {
        $(vitalElements.weight).parent(".vitalWrap").addClass("has-errors");
        $(vitalElements.weight).before(getRangeMessage("weight", isMetric));
        isValid = false;
    }
    //Height
    if ($.trim(vitalElements.heightFeet.val().length) > 0 || $.trim(vitalElements.heightInches.val().length) > 0) {
        var heightFeet = parseFloat(vitalElements.heightFeet.val()) || 0;
        var heightInches = parseFloat(vitalElements.heightInches.val()) || 0;

        if (isMetric) {
            // yea I know its called heightInches, but its actually centimeters
            heightInches += heightFeet * 100;
        } else {
            heightInches += heightFeet * 12;
        }
        if (vitalIsInvalid("height", heightInches, isMetric)) {
            $(vitalElements.heightInches).parents(".vitalWrap").addClass("has-errors");

            var heightLimit = isMetric ? "2.8 meters" : "9 feet";
            $(vitalElements.heightFeet).before(getRangeMessage("height", isMetric, "Height should be " + heightLimit + " or lower"));
            isValid = false;
        }
    }
    //Glucose
    if (vitalIsInvalid("glucose", vitalElements.glucose.val(), isMetric)) {
        $(vitalElements.glucose).parent(".vitalWrap").addClass("has-errors");
        $(vitalElements.glucose).before(getRangeMessage("glucose", isMetric));
        isValid = false;
    }
    //Pregnant - Weeks
    if (vitalIsInvalid("weeksPregnant", vitalElements.weeksPregnant.val(), isMetric)) {
        $(vitalElements.weeksPregnant).parent(".vitalWrap").addClass("has-errors");
        $(vitalElements.weeksPregnant).before(getRangeMessage("weeksPregnant", isMetric));
        isValid = false;
    }
    return isValid;

};

function getRangeMessage(index, isMetric, customMessage){
    var min = (isMetric && vitalFieldRanges[index].hasOwnProperty('metric')) ? vitalFieldRanges[index]['metric'].min : vitalFieldRanges[index]['imperial'].min;
    var max = (isMetric && vitalFieldRanges[index].hasOwnProperty('metric')) ? vitalFieldRanges[index]['metric'].max : vitalFieldRanges[index]['imperial'].max;

    var message = document.createElement("label");
    message.setAttribute("class", "range-message");
    var text = customMessage || document.createTextNode("Must be between " + min + " and " + max + " and max 2 decimal places");
    message.append(text);
    return message;

}

function vitalIsInvalid(index, value, isMetric){

    var min = (isMetric && vitalFieldRanges[index].hasOwnProperty('metric')) ? vitalFieldRanges[index]['metric'].min : vitalFieldRanges[index]['imperial'].min;
    var max = (isMetric && vitalFieldRanges[index].hasOwnProperty('metric')) ? vitalFieldRanges[index]['metric'].max : vitalFieldRanges[index]['imperial'].max;

    value = $.trim(value);

    // is not blank and is non-numeric OR is out of range
    return (value.length > 0 && !decimalCheck(value)) || (decimalCheck(value) && (value < min || value > max));
}

var vitalFieldRanges = {
    respiratoryRate: {
        // breaths per minute
        imperial: {
            min: 6,
            max: 60
        }
    },
    bloodPressureSystolic: {
        imperial: {
            min: 40,
            max: 300
        }
    },
    bloodPressureDiastolic: {
        imperial: {
            min: 30,
            max: 200
        }
    },
    heartRate: {
        // beats per minute
        imperial: {
            min: 4,
            max: 350
        }
    },
    oxygenSaturation: {
        // %
        imperial: {
            min: 70,
            max: 100
        }
    },
    temperature: {
        // Fahrenheit
        imperial: {
            min: 90,
            max: 110
        },
        // Celcius
        metric: {
            min: 32,
            max: 43
        }
    },
    weight: {
        // lbs
        imperial: {
            min: 4,
            max: 1000
        },
        // kg
        metric: {
            min: 2,
            max: 455
        }
    },
    height: {
        // in inches
        imperial: {
            min: 0,
            max: 108
        },
        // cm
        metric: {
            min: 0,
            max: 280
        }
    },
    glucose: {
        // mg/dL
        imperial: {
            min: 0,
            max: 700
        }
    },
    weeksPregnant: {
        imperial: {
            min: 1,
            max: 45
        }
    }
};