
function yesReplacement(id){
    $('#replacementMedication' + id).removeClass('hidden');
    $('#replacementAmount' + id).removeClass('hidden');
}

function noReplacement(id){
    $('#replacementMedication' + id).addClass('hidden');
    $('#replacementAmount' + id).addClass('hidden');
    $('#replacementMedication' + id).val('');
}
