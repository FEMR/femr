var patientVitals = triageFields.patientVitals;//located in triage.js

function validateVital (index, vitalReference) {
    var isMetric = ($("#vitalsUnits").val() == "metric");
    const parent = $(vitalReference).parent(".vitalWrap");
    parent.removeClass("has-errors");
    $(vitalReference).prev("label.range-message").remove();
    if (vitalIsInvalid(index, vitalReference.val(), isMetric)) {
        parent.addClass("has-errors");
        $(vitalReference).before(getRangeMessage(index, isMetric));
        return false;
    } else {
        return true;
    }
}

function validateHeight (index, heightReferenceMajor, heightReferenceMinor) {
    var isMetric = ($("#vitalsUnits").val() == "metric");
    $(heightReferenceMinor).parents(".vitalWrap").removeClass("has-errors");
    $(heightReferenceMajor).prev("label.range-message").remove();
    if ($.trim(heightReferenceMajor.val().length) > 0 || $.trim(heightReferenceMinor.val().length) > 0) {
        var heightMajor = parseFloat(heightReferenceMajor.val()) || 0;
        var heightMinor = parseFloat(heightReferenceMinor.val()) || 0;

        if (isMetric) {
            heightMinor += heightMajor * 100;
        } else {
            heightMinor += heightMajor * 12;
        }
        if (vitalIsInvalid(index, heightMinor, isMetric)) {
            $(heightReferenceMinor).parents(".vitalWrap").addClass("has-errors");
            $(heightReferenceMajor).before(getRangeMessage(index, isMetric));
            return false;
        }
        return true;
    }
}


/**
 * From
 * https://stackoverflow.com/questions/27787768/debounce-function-in-jquery
 *
 * Creates a debounced version of a function that delays invoking the function until
 * after the specified wait time has elapsed since the last time it was invoked.
 *
 * @param {Function} fn - The function to debounce.
 * @param {number} wait - The delay in milliseconds after the last call before invoking the function.
 * @param {boolean} [immediate=false] - If true, the function is triggered at the beginning of the delay period instead of at the end.
 * @returns {Function} - Returns the debounced function.
 */
const debounce = (fn, wait, immediate = false) => {
    let timer;
    return function (...args) {
        const callNow = immediate && !timer;
        clearTimeout(timer);
        timer = setTimeout(() => {
            timer = null;
            if (!immediate) fn.apply(this, args);
        }, wait);
        if (callNow) fn.apply(this, args);
    };
};

const debounceTime = 1000;

$('#respiratoryRate').on('input', debounce(() => {validateVital("respiratoryRate", patientVitals.respiratoryRate)}, debounceTime));
$('#bloodPressureSystolic').on('input', debounce(() => {validateVital("bloodPressureSystolic", patientVitals.bloodPressureSystolic)}, debounceTime));
$('#bloodPressureDiastolic').on('input', debounce(() => {validateVital("bloodPressureDiastolic", patientVitals.bloodPressureDiastolic)}, debounceTime));
$('#heartRate').on('input', debounce(() => {validateVital("heartRate", patientVitals.heartRate)}, debounceTime));
$('#oxygenSaturation').on('input', debounce(() => {validateVital("oxygenSaturation", patientVitals.oxygenSaturation)}, debounceTime));
$('#temperature').on('input', debounce(() => {validateVital("temperature", patientVitals.temperature)}, debounceTime));
$('#weight').on('input', debounce(() => {validateVital("weight", patientVitals.weight)}, debounceTime));
$('#heightFeet').on('input', debounce(() => {validateHeight("height", patientVitals.heightFeet, patientVitals.heightInches)}, debounceTime));
$('#heightInches').on('input', debounce(() => {validateHeight("height", patientVitals.heightFeet, patientVitals.heightInches)}, debounceTime));
$('#glucose').on('input', debounce(() => {validateVital("glucose", patientVitals.glucose)}, debounceTime));
$('#weeksPregnant').on('input', debounce(() => {validateVital("weeksPregnant", patientVitals.weeksPregnant)}, debounceTime));

var vitalClientValidator = function (vitalElements) {
    var isValid = true;

    // remove all errors before validating
    $("#vitalContainer .vitalWrap").removeClass("has-errors");
    $("#vitalContainer label.range-message").remove();

    //Respirations
    if (!validateVital("respiratoryRate", vitalElements.respiratoryRate)) { isValid = false; }
    //Blood Pressure - Systolic
    if (!validateVital("bloodPressureSystolic", vitalElements.bloodPressureSystolic)) { isValid = false; }
    //Blood Pressure - Diastolic
    if (!validateVital("bloodPressureDiastolic", vitalElements.bloodPressureDiastolic)) { isValid = false; }
    //Heart Rate
    if (!validateVital("heartRate", vitalElements.heartRate)) { isValid = false; }
    //Oxygen
    if (!validateVital("oxygenSaturation", vitalElements.oxygenSaturation)) { isValid = false; }
    //Temperature
    if (!validateVital("temperature", vitalElements.temperature)) { isValid = false; }
    //Weight
    if (!validateVital("weight", vitalElements.weight)) { isValid = false; }
    //Height
    if (!validateHeight("height", vitalElements.heightFeet, vitalElements.heightInches)) { isValid = false; }
    //Glucose
    if (!validateVital("glucose", vitalElements.glucose)) { isValid = false; }
    //Pregnant - Weeks
    if (!validateVital("weeksPregnant", vitalElements.weeksPregnant)) { isValid = false; }

    return isValid;

};

function getRangeMessage(index, isMetric){
    var min = (isMetric && vitalFieldRanges[index].hasOwnProperty('metric')) ? vitalFieldRanges[index]['metric'].min : vitalFieldRanges[index]['imperial'].min;
    var max = (isMetric && vitalFieldRanges[index].hasOwnProperty('metric')) ? vitalFieldRanges[index]['metric'].max : vitalFieldRanges[index]['imperial'].max;

    var message = document.createElement("label");
    message.setAttribute("class", "range-message");
    message.setAttribute("id", "range-message-" + index);
    message.setAttribute("data-min", min);
    message.setAttribute("data-max", max);
    var text = document.createTextNode("Should be greater than: " + min + " and should be less than: " + max);
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
            max: 30
        }
    },
    bloodPressureSystolic: {
        imperial: {
            min: 90,
            max: 200
        }
    },
    bloodPressureDiastolic: {
        imperial: {
            min: 50,
            max: 100
        }
    },
    heartRate: {
        // beats per minute
        imperial: {
            min: 50,
            max: 120
        }
    },
    oxygenSaturation: {
        // %
        imperial: {
            min: 90,
            max: 100
        }
    },
    temperature: {
        // Fahrenheit
        imperial: {
            min: 97.0,
            max: 100.0
        },
        // Celcius
        metric: {
            min: 36.0,
            max: 37.8
        }
    },
    weight: {
        // lbs
        imperial: {
            min: 4,
            max: 400
        },
        // kg
        metric: {
            min: 2,
            max: 180
        }
    },
    height: {
        // in inches
        imperial: {
            min: 15,
            max: 84
        },
        // cm
        metric: {
            min: 38,
            max: 213
        }
    },
    glucose: {
        // mg/dL
        imperial: {
            min: 60,
            max: 200
        }
    },
    weeksPregnant: {
        imperial: {
            min: 1,
            max: 42
        }
    }
};