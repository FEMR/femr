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


function confirmProblem(rank){
    let card = document.getElementsByClassName("problemCard")[rank];

    card.querySelector("#probTitle").innerHTML = validatePInput(card.querySelector("#probTitleInput").value);
    card.querySelector("#pBoxStep1").innerText = card.querySelector("#pStep1").value;
    card.querySelector("#pBoxStep2").innerText = card.querySelector("#pStep2").value;

    card.querySelector("#pCardEditMode").style.visibility = "hidden";
    card.querySelector("#pCardSaved").style.visibility = "visible";
}

function validatePInput(input){
    if (input === ""){
        return "<b>DANGIT</b>";
    }
    return "<b>" + input + "</b>";
}

function editProblem(rank){
    let card = document.getElementsByClassName("problemCard")[rank];

    card.querySelector("#pCardEditMode").style.visibility = "visible";
    card.querySelector("#pCardSaved").style.visibility = "hidden";
}

function deleteProblem(rank){
    let cards = document.getElementsByClassName("problemCard");
    cards[rank].parentNode.removeChild(cards[rank]);
}

function addTarget(rank){

}

let pData = [
    {title: 'firstProblem', step1: 'description', step2: 'XX/XX/XXXX', rank: 1},
    {title: 'nextProblem', step1: 'description', step2: 'XX/XX/XXXX', rank: 2},
    {title: 'last(?)Problem', step1: 'description', step2: 'XX/XX/XXXX', rank: 3},
]

document.getElementById('addProblem').addEventListener(
    'click',
    function () {
        // let probCount = parseInt(sessionStorage.getItem("probCount"));
        // if(!isNaN(probCount)){
        //     probCount +=1;
        // } else {
        //     probCount = 0;
        // }
        let button = document.getElementById("addProblem");
        button.innerText = "TestWoah";

        let pList = document.getElementById('problemList');
        pList.innerHTML = "";

        pData.forEach((cardInfo, idx) => {
            let newDiv = document.createElement("div");
            let newProblem =    "        <div id=\"pCardSaved\">\n" +
                                "            <div class=\"sideBySideFlex\">\n" +
                                "                <b class=\"problemNumber\" >" + idx + "</b>\n" +
                                "                <h4 id=\"probTitle\" style = \"flex-grow: 1; margin-top: .17em\">\n" +
                                "                    <b>titleNOVIS</b>\n" +
                                "                </h4>\n" +
                                "                <button type=\"submit\" class=\"idSearch problemCardButton\" id=\"pCardEdit\" onclick=\"editProblem(" + idx + ")\">\n" +
                                "                    <img src=\"/assets/img/edit.png\" style=\"width:20px; height:20px;\">\n" +
                                "                </button>\n" +
                                "                <button type=\"submit\" class=\"idSearch problemCardButton\" id=\"pCardDelete\" onclick=\"deleteProblem(" + idx + ")\">\n" +
                                "                    <img src=\"/assets/img/delete.png\" style=\"width:20px; height:20px;\">\n" +
                                "                </button>\n" +
                                "            </div>\n" +
                                "            <div>\n" +
                                "                <p style=\"margin-top: 5px;color: #A8AAB9\" >Plan</p>\n" +
                                "                    <label type=\"fCheckbox\"  style=\"height: 20px\">\n" +
                                "                    <input type=\"checkbox\" step=\"any\" class=\"filled\" >\n" +
                                "                        <p id=\"pBoxStep1\" class=\"pStepLabel\">step1NOVIS</p>\n" +
                                "                </label>\n" +
                                "                    <label type=\"fCheckbox\" id=\"pCheckbox2\" style=\"height: 20px\">\n" +
                                "                    <input type=\"checkbox\" step=\"any\" class=\"filled\" >\n" +
                                "                        <p id=\"pBoxStep2\" class=\"pStepLabel\">@step2NOVIS</p>\n" +
                                "                </label>\n" +
                                "            </div>\n" +
                                "        </div>\n" +
                                "\n" +
                                "        <div id=\"pCardEditMode\">\n" +
                                "            <div class=\"sideBySideFlex\">\n" +
                                "                <b class=\"problemNumber\" >" + idx + "</b>\n" +
                                "                <input type=\"text\" class=\"age-input fInput\" id=\"probTitleInput\" style = \"flex-grow: 1;\" placeholder=\"Problem Title\">\n" +
                                "                <button type=\"submit\" class=\"idSearch problemCardButton\" id=\"pCardConfirm\" onclick=\"confirmProblem(" + idx + ")\">\n" +
                                "                    <img src=\"/assets/img/confirm.png\" style=\"width:20px; height:20px;\">\n" +
                                "                </button>\n" +
                                "                <button type=\"submit\" class=\"idSearch problemCardButton\" id=\"pCardConfirm\" onclick=\"addTarget(" + idx + ")\">\n" +
                                "                    <img src=\"/assets/img/target.png\" style=\"width:20px; height:20px;\">\n" +
                                "                </button>\n" +
                                "            </div>\n" +
                                "            <div>\n" +
                                "                <p style=\"margin-top: 5px;color: #A8AAB9\" >Plan</p>\n" +
                                "                <input type=\"text\" class=\"fInput\" id=\"pStep1\" placeholder=\"Step 1\">\n" +
                                "                <input type=\"text\"  class=\"fInput\" id=\"pStep2\" placeholder=\"Step 2\" style=\"margin-top: 5px\">\n" +
                                "            </div>\n" +
                                "        </div>\n"
            newDiv.setAttribute("id", "problemCard");
            newDiv.setAttribute("class", "problemCard");
            newDiv.innerHTML = newProblem;
            pList.append(newDiv);
        })

    },
    false
);

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