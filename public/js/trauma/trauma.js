function ShowHideDiv() {
    let additionalInfoDropdown = document.getElementById("additionalInfoDropdown");
    let additionalInfo = document.getElementById("additionalInfo");
    additionalInfo.style.display = additionalInfoDropdown.value == "Y" ? "block" : "none";
}