$(document).ready(function(){
    $('#medicalSubmitBtn').click(function(){
        var pass = true;
        if (checkPrescriptions() === false){
            alert("Duplicate prescriptions can not be submitted.");
            pass = false;
        }
        return pass;
    })
});

function checkPrescriptions(){
    var prescriptions = [];
    prescriptions.push($('#prescription1').val());
    prescriptions.push($('#prescription2').val());
    prescriptions.push($('#prescription3').val());
    prescriptions.push($('#prescription4').val());
    prescriptions.push($('#prescription5').val());
    prescriptions.sort();
    var last = prescriptions[0];
    for (var i=1; i<prescriptions.length; i++){
        if (prescriptions[i] == last && prescriptions[i] != ''){
            return false;
        }
        last = prescriptions[i];
    }
    return true;
}