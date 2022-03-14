function ShowHideAdditionalDiv() {
    let additionalInfoCheckbox = document.getElementById("additionalInfoCheckbox");
    let additionalInfo = document.getElementById("additionalInfo");
    additionalInfo.style.display = additionalInfoCheckbox.checked === true ? "block" : "none";
}

function onlyOneTool(checkbox) {
    let checkboxes = document.getElementsByName('drawCheckbox')
    checkboxes.forEach((item) => {
        if (item !== checkbox) item.checked = false
    })
}

$('#femaleBtn').change(function () {
    $('#weeksPregnant').attr('disabled', false);

    // remove any errors
    $(this).parents(".generalInfoInput").removeClass("has-errors");
});
$('#maleBtn').change(function () {
    $('#weeksPregnant').val('');
    $('#weeksPregnant').attr('disabled', true);

    // remove any errors
    $(this).parents(".generalInfoInput").removeClass("has-errors");
});