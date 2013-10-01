

//onClick activators
$('.newPatientBtn').click(function(){
    window.location = "/triage";
});
$('#femaleBtn').change(function(){
    if ($('#pregnantWrap').hasClass('hidden')){
        $('#pregnantWrap').removeClass('hidden');
    }
});
$('#maleBtn').change(function(){
    if (!$('#pregnantWrap').hasClass('hidden')){
        $('#pregnantWrap').addClass('hidden');
    }
    if ($('#pregnantBtn').is(':checked')){
        $('#pregnantBtn').prop('checked',false);
        $('#pregnantBtn').parent().removeClass('active');
    }
    if (!$('#weeksWrap').hasClass('hidden')){
        $('#weeksWrap').addClass('hidden');
    }
});
$('#pregnantBtn').change(function(){
   $('#weeksWrap').removeClass('hidden');
});

$('#resetBtn').click(function(){
    resetFields();
});
$('#historyBtn').click(function(){
    showHistory();
});


function resetFields(){
    $(':input').val('');
    $('.active').removeClass('active');
    }


function showHistory(){
    if ($('.patientMedicalHistory' ).hasClass('hide')){
    $('.patientMedicalHistory' ).removeClass('hide');
    }
else{
    $('.patientMedicalHistory' ).addClass('hide');
    }
}