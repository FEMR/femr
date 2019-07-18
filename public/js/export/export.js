$(document).ready(function(){

    var selectAllCheckbox = $("#export-form").find("input[type='checkbox']").not("[name='missionTripIds']").first();
    var missionTripCheckboxes = $("#export-form").find("input[name='missionTripIds[]']");
    $(selectAllCheckbox).click(function(){
        $(missionTripCheckboxes).prop('checked', $(this).prop('checked'));
    });

    // $("#submit-button").click(function(){
    //    $(this).prop('disabled', true);
    //    return true;
    // });
});