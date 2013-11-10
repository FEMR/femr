$('#newPatientBtn').click(function(){
    window.location= "/triage";
});

function setSearchResults(idx){
    document.getElementById("id").value = idx + 1;
    document.getElementById("triageSearch").click();
}

