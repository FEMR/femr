

$(document).ready(function(){
    $("[name='newTripCity']").change(function(){

        $("[name='newTripCountry']").val($(this).find(':selected').attr('country-name'));
    });


    //$('#tripTable').DataTable({
    //    columnDefs: [ { orderable: false, targets: [3] }]
    //});
});