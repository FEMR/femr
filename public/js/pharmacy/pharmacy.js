$(document).ready(function(){
    $('.toggleReplacement').click(function(){
        if ($(this).attr('id').substr(0,1) == "y"){
            yesReplacement($(this).attr('id').substr(1,2));
        }
        else{
            noReplacement($(this).attr('id').substr(1,2));
        }
    });

});

function yesReplacement(id){
    $('#replacementMedication' + id).removeClass('hidden');
    $('#replacementAmount' + id).removeClass('hidden');
}

function noReplacement(id){
    $('#replacementMedication' + id).addClass('hidden');
    $('#replacementAmount' + id).addClass('hidden');
    $('#replacementMedication' + id).val('');
}
