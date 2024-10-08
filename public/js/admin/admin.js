var coll = document.getElementsByClassName("collapsible");
var i;

for (i = 0; i < coll.length; i++) {
    coll[i].addEventListener("click", function() {
        this.classList.toggle("active");
        var content = this.nextElementSibling;
        if (content.style.maxHeight) {
            content.style.maxHeight = null;
        } else {
            content.style.maxHeight = content.scrollHeight + "px";
        }
    });
}

//updates list logic
const updateScheduled = {}
window.addEventListener("load", function(){
    const langs = document.getElementsByClassName("language-checkbox");
    for(const lang of langs){
        updateScheduled[lang.name] = lang.checked && !lang.disabled;
    }
})

function updateSchedule(code){
    updateScheduled[code] = !updateScheduled[code];
    $.ajax({
        type: 'get',
        url: '/admin/updates/scheduleUpdate',
        data: {code: code, update: updateScheduled[code]},
        failure: function(){console.log("Error Occurred");}
    }).done(function(){location.reload()});
}

function downloadPackages(){
    const updates = []
    const loader = document.getElementById("loading");
    for(const lang in updateScheduled){
        if(updateScheduled[lang]){
            updates.push(lang);
        }
    }
    if(updates.length > 0){
        loader.hidden = false;
        $.ajax({
            type: 'get',
            url: '/admin/updates/downloadPackages',
            data: {code: JSON.stringify(updates)},
            failure: function() {console.log("Error Occurred");}
        }).done(function() {
            console.log("All packages downloaded")
            loader.hidden = true;
            location.reload();
        });
    }
}

function initLanguages(){
    $.ajax({
        type: 'get',
        url: '/admin/updates/initLanguages',
        failure: function() {console.log("Error Occurred");}
    }).done(function(){location.reload()});
}


