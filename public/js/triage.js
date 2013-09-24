
//onClick activators
$('#maleBtn').click(function(){
   genderSelect(this.id);
});
$('#femaleBtn').click(function(){
    genderSelect(this.id);
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


function genderSelect(gender){
    if (!$('#' + gender).hasClass('active')){
    $('#male').removeClass('active');
    $('#female').removeClass('active');
    $('#' + gender).addClass('active');
    }
}