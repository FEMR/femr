$(document).ready(function ()
{
    $(".date").each(function(i) {
        $('#date').text($('#date').text().slice(0, -10))
        $(this).attr('id', "date" + (i + 1));
    });

    $('#newPatientBtn').click(function(){
        window.location= "/triage";
    });

});

function setSearchResults(idx){
    document.getElementById("id").value = idx;
    document.getElementById("idSearch").click();
}


