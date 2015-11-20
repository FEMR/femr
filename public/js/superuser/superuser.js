

$(document).ready(function(){
    $("[name='newTripCity']").change(function(){

        $("[name='newTripCountry']").val($(this).find(':selected').attr('country-name'));
    });

    if ($('#tripTable').length > 0)
        $('#tripTable').DataTable();
    if ($('#cityTable').length > 0)
        $('#cityTable').DataTable();
    if ($('#teamTable').length > 0)
        $('#teamTable').DataTable();
    if ($('#addUsersSelect2').length > 0){

        $('#addUsersSelect2').select2({
            placeholder: "Add users here"
        });
    }
    if ($('#removeUsersSelect2').length > 0){

        $('#removeUsersSelect2').select2({
            placeholder: "Remove users here"
        });
    }

});