$(document).ready(function ()
{
    $(".date").each(function(i) {
        $('#date').text($('#date').text().slice(0, -10))
        $(this).attr('id', "date" + (i + 1));
    });

    $('#newPatientBtn').click(function(){
        window.location= "/triage";
    });

    $('#EditHistory').click(function(){
        window.location= "/triage";
    });

    $('#historySubmitBtn').click(function () {
        //get the base64 URI string from the canvas
        patientPhotoFeature.prepareForPOST();
        //make sure the feature is turned on before JSONifying
        if (multipleChiefComplaintFeature.isActive == true) {
            multipleChiefComplaintFeature.JSONifyChiefComplaints();
        }
        return validate(); //located in triageClientValidation.js
    });
}
    $('.selectPageFromRow').click(function(){
        var id = $.trim($(this).parent().parent().find('.patientId').html());
        document.getElementById("nameOrIdSearchForm").value = id;
        document.getElementById("searchBtn").click();
    });

});



