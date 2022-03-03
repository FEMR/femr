function ShowHideAdditionalDiv() {
    let additionalInfoCheckbox = document.getElementById("additionalInfoCheckbox");
    let additionalInfo = document.getElementById("additionalInfo");
    additionalInfo.style.display = additionalInfoCheckbox.checked === true ? "block" : "none";
}

function onlyOne(checkbox) {
    let checkboxes = document.getElementsByName('drawCheckbox')
    checkboxes.forEach((item) => {
        if (item !== checkbox) item.checked = false
    })
}
