function ToggleAdditionalInfo() {
    let additionalInfoTrigger = document.getElementById("addInfoTrigger");
    additionalInfoTrigger.innerText = additionalInfo.checked === true ? "▲ Less Information" : "▼ More Information";
}

document.getElementById('logVitals').addEventListener(
    'click',
    function () {
        let setCount = parseInt(sessionStorage.getItem("setCount"));
        if(!isNaN(setCount)){
            setCount +=1;
        } else {
            setCount = 0;
        }
        sessionStorage.setItem("setCount", setCount);

        let vitals = []
        let vitalNames = ['respiratoryRate', 'heartRate', 'temperature', 'oxygenSaturation', 'heightFeet',
            'heightInches', 'weight', 'bloodPressureSystolic', 'bloodPressureDiastolic', 'glucose', 'weeksPregnant']
        vitals.push(setCount);
        vitalNames.forEach(vitalName =>{
            vitals.push(document.getElementById(vitalName).value);
            document.getElementById(vitalName).value = '';
        })
        /* vitals.push(document.getElementById(  bmi is calculated but idk how  ).value); */

        let identifier = "localVitalSet" + setCount.toString();
        sessionStorage.setItem( identifier, JSON.stringify(vitals));

        vitals = JSON.parse(sessionStorage.getItem(identifier));
        appendVitalsToTable(vitals, "vitalsTableBody");


    },
    false
);



function appendVitalsToTable(items, tableRef) {
    let tbl = document.getElementById(tableRef), // table reference
        i;

    // open loop for each row and append cell
    for (i = 0; i < tbl.rows.length; i++) {
        let item = (items[i+1] === "") ? "--" : items[i+1];
        createCell(tbl.rows[i].insertCell(tbl.rows[i].cells.length), item, 'col');
    }
}

// create DIV element and append to the table cell
function createCell(cell, text, style) {
    let div = document.createElement('div'), // create DIV element
        txt = document.createTextNode(text); // create text node
    div.appendChild(txt);                    // append text node to the DIV
    div.setAttribute('class', style);        // set DIV class attribute
    div.setAttribute('className', style);    // set DIV class attribute for IE (?!)
    cell.appendChild(div);                   // append DIV to the table cell
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