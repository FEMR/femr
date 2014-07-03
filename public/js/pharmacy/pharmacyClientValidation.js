$(document).ready(function () {
    $('#pharmacySubmitBtn').click(function () {
        var pass = true;
        if (checkReplacementPrescriptions() === false) {
            alert("Duplicate prescriptions can not be submitted.");
            pass = false;
        }
        return pass;
    })
});

function checkReplacementPrescriptions() {
    var medications = [];
    var replacementMedications = [];
    for (var m = 1; m <= 5; m++){
        if (typeof($('#medication' + m).val()) !== 'undefined'){
            medications.push($('#medication' + m).val());
            if (typeof($('#replacementMedication' + m).val()) !== 'undefined') {
                replacementMedications.push($('#replacementMedication' + m).val());
            }
        }
    }

    replacementMedications.sort();

    var last = replacementMedications[0];
    for (var i = 1; i < replacementMedications.length; i++) {
        if (typeof replacementMedications[i] != 'undefined') {
            if (replacementMedications[i].toLowerCase().trim() === last.toLowerCase().trim() && replacementMedications[i] != '') {
                return false;
            }
        }
        last = replacementMedications[i];
    }
    for (var i = 0; i < replacementMedications.length; i++) {
        for (var j = 0; j < medications.length; j++) {
            if (typeof replacementMedications[j] != 'undefined') {
                if (replacementMedications[i].toLowerCase().trim() === medications[j].toLowerCase().trim() && replacementMedications[i] != '') {
                    return false;
                }
            }
        }
    }
}