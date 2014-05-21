/**
 * Created by Danny on 1/26/14.
 * Edited by Kevin
 * adds bootstrap functionality to encounter page
 */

// This is for the showEncounter.scala.html page
$(document).ready(function () {
    //controls the tabbed viewing of HPI and Treatment
    $('#encounterTabs a').click(function (e) {
        e.preventDefault()
        $(this).tab('show')

    });

    $('#encounterTabs a').click(function () {
        showTab($(this).attr('id'));
    });



});

/**
 * Generic tab showing function
 *
 * @param clickedTab tab that the user clicked
 */
function showTab(clickedTab){
    $('#tabContentWrap > .controlWrap').each(function(){
        if ($(this).is("#" + clickedTab + "Control")){
            $(this).removeClass("hidden");
        }else{
            $(this).addClass("hidden");
        }
    });
}

// implement the functions to show and hide the tabes on the encounters page
