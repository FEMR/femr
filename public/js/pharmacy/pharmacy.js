
$(document).ready(function(){
    $('.toggleReplacement').click(function(){
        alert($(this).attr('id'));

        //call yesReplacement or noReplacement based on id
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
