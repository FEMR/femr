

$(document).ready(function(){
    $("[name='newTripCity']").change(function(){

        $("[name='newTripCountry']").val($(this).find(':selected').attr('country-name'));
    });

    $('#tripTable').DataTable();
    $('#cityTable').DataTable();
    $('#teamTable').DataTable();
});