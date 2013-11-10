$('#newPatientBtn').click(function(){
    window.location= "/triage";
});

function setSearchResults(idx){
    document.getElementById("id").value = idx;
    document.getElementById("triageSearch").click();
}

