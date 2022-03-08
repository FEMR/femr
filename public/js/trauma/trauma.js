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

function onlyOneAge(checkbox) {
    let checkboxes = [...document.getElementsByClassName('ageCheckbox')]
    checkboxes.forEach((item) => {
        if (item !== checkbox) item.checked = false
    })
}

function onlyOneGender(checkbox) {
    let checkboxes = [...document.getElementsByClassName('genderCheckbox')]
    checkboxes.forEach((item) => {
        if (item !== checkbox) item.checked = false
    })
}

function checkPregnancyInput() {
    let femaleCheckbox = document.getElementById("femaleCheckbox");
    let weeksPregnant = document.getElementById("weeksPregnant");
    femaleCheckbox.checked === true ? (weeksPregnant.disabled = false) : (weeksPregnant.disabled = true);
}