function ToggleAdditionalInfo() {
    let additionalInfoTrigger = document.getElementById("addInfoTrigger");
    additionalInfoTrigger.innerText = additionalInfo.checked === true ? "▲ Less Information" : "▼ More Information";
}

function onlyOneTool(checkbox) {
    let checkboxes = document.getElementsByName('drawCheckbox')
    checkboxes.forEach((item) => {
        if (item !== checkbox) item.checked = false
    })
}

function downloadURI(uri, name) {
    var link = document.createElement('a');
    link.download = name;
    link.href = uri;
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);
    delete link;
}

document.getElementById('save').addEventListener(
    'click',
    function () {
        var dataURL = layer.toDataURL();
        downloadURI(dataURL, 'patient.png');
    },
    false
);

document.getElementById('eraseAll').addEventListener(
    'click',
    function () {
        layer.destroyChildren();
    },
    false
);

document.getElementById('undoButton').addEventListener(
    'click',
    function () {
        let children = layer.getChildren()
        if (children.length !== 0) {
            let removingLine = children.slice(-1)[0]
            removingLine.remove()
        }
    },
    false
);

$('#femaleBtn').change(function () {
    $('#weeksPregnant').attr('disabled', false);
    changeBackground("femaleBtn");
    // remove any errors
    $(this).parents(".generalInfoInput").removeClass("has-errors");
});
$('#maleBtn').change(function () {
    $('#weeksPregnant').val('');
    $('#weeksPregnant').attr('disabled', true);
    changeBackground("maleBtn");
    // remove any errors
    $(this).parents(".generalInfoInput").removeClass("has-errors");
});
$('#weeksPregnant').change(function () {
    if (document.querySelector("#weeksPregnant").value > 0)
        changeBackground("weeksPregnant");
    else
        changeBackground("femaleBtn");
});
