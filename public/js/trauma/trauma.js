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


let btns = document.getElementsByClassName("sevBtn");
for (let i = 0; i < btns.length; i++) {
    btns[i].addEventListener("click", function() {
        let current = document.getElementsByClassName("sevBtn active");
        if (current.length < 1) {
            this.className += " active";
        } else if (current.length === 1 && this.value === current[0].value) {
                current[0].className = current[0].className.replace(" active", "");
        }
        else {
            current[0].className = current[0].className.replace(" active", "");
            this.className += " active";
        }
    });
}

$('#severityColor').click(function () {
    let buttonColor = document.getElementsByClassName("sevBtn active");
    if ( buttonColor.length === 0) {
        buttonColor = "transparent";
    } else {
        buttonColor = buttonColor[0].value
    }
    $("#diagramContainer").css("border-color", buttonColor);
});