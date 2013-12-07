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
    medications.push($('#medication1').val());
    medications.push($('#medication2').val());
    medications.push($('#medication3').val());
    medications.push($('#medication4').val());
    medications.push($('#medication5').val());

    var replacementMedications = [];
    replacementMedications.push($('#replacementMedication1').val());
    replacementMedications.push($('#replacementMedication2').val());
    replacementMedications.push($('#replacementMedication3').val());
    replacementMedications.push($('#replacementMedication4').val());
    replacementMedications.push($('#replacementMedication5').val());
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