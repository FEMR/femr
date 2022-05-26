function ToggleAdditionalInfo() {
    let additionalInfoTrigger = document.getElementById("addInfoTrigger");
    additionalInfoTrigger.innerText = additionalInfo.checked === true ? "▲ Less Information" : "▼ More Information";
}

function getValidSessionID(key){
    let keyID = parseInt(sessionStorage.getItem(key));
    if(isNaN(keyID)){
        keyID = 0;
    }
    return keyID;
}

document.getElementById('logVitals').addEventListener(
    'click',
    function () {
        let setCount = getValidSessionID("setCount")
        setCount++;
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

/* PROBLEM CARD CODE   ( ͡╥ ͜ʖ ͡╥)   */


document.getElementById('addProblem').addEventListener(
    'click',
    function () {
        // let button = document.getElementById("addProblem");
        // button.innerText = "TestWoah";

        reloadList();
        let cardDataList = getProblemList();
        let newCard = newProblemCard({}, cardDataList.length);
        newCard.querySelector("#pCardSaved").style.visibility = "hidden"
        newCard.querySelector("#pCardEditMode").style.visibility = "visible"
        document.getElementById('problemList').append(newCard);


    },
    false
);

function getProblemList(){
    let probCount = getValidSessionID("probCount");
    let pList = [];
    if (probCount === 0){
        return pList;
    } else {
        for(let i=0; i<probCount; i++){
            let card = JSON.parse(sessionStorage.getItem("pCard"+i));
            pList.push({title: card[0], step1: card[1], step2: card[2]});
        }
    }
    return pList;
}

function reloadList(){
    let pList = document.getElementById('problemList');
    pList.innerHTML = "";

    getProblemList().forEach((cardInfo, idx) => {
        pList.append(newProblemCard(cardInfo, idx));
    })
}
window.onload = function onLoad(){ reloadList() };


function confirmProblem(rank){
    let card = document.getElementsByClassName("problemCard")[rank];

    let title = validatePInput(card.querySelector("#probTitleInput").value);
    let s1 = card.querySelector("#pStep1").value;
    let s2 = card.querySelector("#pStep2").value;

    sessionStorage.setItem("pCard" + rank, JSON.stringify([title, s1, s2]));
    let newProbCount = getValidSessionID("probCount");
    if (rank >= newProbCount){
        newProbCount++;
    }
    sessionStorage.setItem("probCount", newProbCount);
    reloadList();


    card.querySelector("#pCardEditMode").style.visibility = "hidden";
    card.querySelector("#pCardSaved").style.visibility = "visible";
}

function validatePInput(input){
    if (input === ""){
        return "ADD VALIDAtION";
    }
    return  input ;
}

function editProblem(rank){
    let card = document.getElementsByClassName("problemCard")[rank];

    card.querySelector("#pCardEditMode").style.visibility = "visible";
    card.querySelector("#pCardSaved").style.visibility = "hidden";
}



function deleteProblem(rank){
    let cards = document.getElementsByClassName("problemCard");
    cards[rank].parentNode.removeChild(cards[rank]);

    for(let i=rank; i<(getValidSessionID("probCount")-1); i++){
        sessionStorage.setItem("pCard" + i, sessionStorage.getItem("pCard" + (i+1)));
    }
    sessionStorage.setItem("probCount", getValidSessionID("probCount") - 1);
    // let cardDataList = JSON.parse(sessionStorage.getItem("cardDataList"));
    // cardDataList.splice(rank,1);
    // sessionStorage.setItem("cardDataList", JSON.stringify(cardDataList));



    reloadList();
}

function addTarget(rank){

}


function newProblemCard(cardInfo, idx){
    let newCard = document.createElement("div");
    newCard.setAttribute("id", "problemCard");
    newCard.setAttribute("class", "problemCard");

    newCard.innerHTML =  "        <div id=\"pCardSaved\">\n" +
            "            <div class=\"sideBySideFlex\">\n" +
            "                <b class=\"problemNumber\" >" + idx + "</b>\n" +
            "                <h4 id=\"probTitle\" style = \"flex-grow: 1; margin-top: .17em\">\n" +
            "                    <b>"+ cardInfo.title +"</b>\n" +
            "                </h4>\n" +
            "                <button type=\"button\" class=\"idSearch problemCardButton\" id=\"pCardEdit\" onclick=\"editProblem(" + idx + ")\">\n" +
            "                    <img src=\"/assets/img/edit.png\" style=\"width:20px; height:20px;\">\n" +
            "                </button>\n" +
            "                <button type=\"button\" class=\"idSearch problemCardButton\" id=\"pCardDelete\" onclick=\"deleteProblem(" + idx + ")\">\n" +
            "                    <img src=\"/assets/img/delete.png\" style=\"width:20px; height:20px;\">\n" +
            "                </button>\n" +
            "            </div>\n" +
            "            <div>\n" +
            "                <p style=\"margin-top: 5px;color: #A8AAB9\" >Plan</p>\n" +
            "                    <label type=\"fCheckbox\"  style=\"height: 20px\">\n" +
            "                    <input type=\"checkbox\" step=\"any\" class=\"filled\" >\n" +
            "                        <p id=\"pBoxStep1\" class=\"pStepLabel\">"+ cardInfo.step1 +"</p>\n" +
            "                </label>\n" +
            "                    <label type=\"fCheckbox\" id=\"pCheckbox2\" style=\"height: 20px\">\n" +
            "                    <input type=\"checkbox\" step=\"any\" class=\"filled\" >\n" +
            "                        <p id=\"pBoxStep2\" class=\"pStepLabel\">"+ cardInfo.step2 +"</p>\n" +
            "                </label>\n" +
            "            </div>\n" +
            "        </div>\n" +
            "\n" +
            "        <div id=\"pCardEditMode\">\n" +
            "            <div class=\"sideBySideFlex\">\n" +
            "                <b class=\"problemNumber\" >" + idx + "</b>\n" +
            "                <input type=\"text\" class=\"age-input fInput\" id=\"probTitleInput\" style = \"flex-grow: 1;\" placeholder=\"Problem Title\">\n" +
            "                <button type=\"button\" class=\"idSearch problemCardButton\" id=\"pCardConfirm\" onclick=\"confirmProblem(" + idx + ")\">\n" +
            "                    <img src=\"/assets/img/confirm.png\" style=\"width:20px; height:20px;\">\n" +
            "                </button>\n" +
            "                <button type=\"button\" class=\"idSearch problemCardButton\" id=\"pCardConfirm\" onclick=\"addTarget(" + idx + ")\">\n" +
            "                    <img src=\"/assets/img/target.png\" style=\"width:20px; height:20px;\">\n" +
            "                </button>\n" +
            "            </div>\n" +
            "            <div>\n" +
            "                <p style=\"margin-top: 5px;color: #A8AAB9\" >Plan</p>\n" +
            "                <input type=\"text\" class=\"fInput\" id=\"pStep1\" placeholder=\"Step 1\">\n" +
            "                <input type=\"text\"  class=\"fInput\" id=\"pStep2\" placeholder=\"Step 2\" style=\"margin-top: 5px\">\n" +
            "            </div>\n" +
            "        </div>\n"
    if(JSON.stringify(cardInfo) !== '{}') {
        newCard.querySelector("#probTitleInput").value = cardInfo.title;
        newCard.querySelector("#pStep1").value = cardInfo.step1;
        newCard.querySelector("#pStep2").value = cardInfo.step2;
    }
    return newCard;
}


/* end pcard code */





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