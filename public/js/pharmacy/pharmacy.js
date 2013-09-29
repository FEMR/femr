/**
 * Created with IntelliJ IDEA.
 * User: David
 * Date: 9/29/13
 * Time: 3:22 PM
 * To change this template use File | Settings | File Templates.
 */
function yesReplacement(id){
    $('#replacementMedication' + id).removeClass('hidden');
}

function noReplacement(id){
    $('#replacementMedication' + id).addClass('hidden');
    $('#replacementMedication' + id).val('');
}
