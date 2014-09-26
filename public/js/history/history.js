$(document).ready(function ()
{
    $(".date").each(function(i) {
        $('#date').text($('#date').text().slice(0, -10))
        $(this).attr('id', "date" + (i + 1));
    });

    $('#newPatientBtn').click(function(){
        window.location= "/triage";
    });

    $('.selectPageFromRow').click(function(){
        var id = $.trim($(this).parent().parent().find('.patientId').html());
        document.getElementById("nameOrIdSearchForm").value = id;
        document.getElementById("searchBtn").click();
    });

});



