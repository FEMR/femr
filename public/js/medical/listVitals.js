$(document).ready(function () {
    $('#vitalTable').tableScroll({ height: 670, width: 250 });
    calculateBMI();

});


function calculateBMI() {
    var weight = null;
    var height_in = null;
    var height_ft = null;

   
    // Search vitals from most recent to least for one containing a valid weight
    $($("#weight td").get().reverse()).each(function() {
        var tryParse = parseFloat($(this).attr("data-weight"));
        if (!isNaN(tryParse)) {
            weight = tryParse;

    $($weights.get().reverse()).each(function () {
        if ($(this).html() !== null && $(this).html() !== '' && typeof ( $(this).html() ) !== 'undefined') {
            weight_lbs = xarseInt($(this).html());

            return false;
        }
    });

    // Search vitals from most recent to least for one containing a valid height
    $($("#height td").get().reverse()).each(function() {
        var tryParseFeet = parseFloat($(this).attr("data-feet"));
        var tryParseInches = parseFloat($(this).attr("data-inches"));
        if (!isNaN(tryParseFeet) && !isNaN(tryParseInches)) {
            height_ft = tryParseFeet;
            height_in = tryParseInches;
            return false;
        }
    });

    // Problem finding weight and height
    if (height_ft === null || height_in === null || weight === null) {
        $("#bmi").text("N/A");
        return;
    }

    // Calculate total inches by combinning feet and inches
    var totalInches = height_in + height_ft * 12;

    // BMI for Metric - Alaa Serhan
    var bmi = Math.round((weight / (totalInches * totalInches)) * 703);
    if (!isFinite(bmi) || bmi === '' || bmi === null){
       $('#bmi').text("N/A");
    }else{
        $('#bmi').text(bmi);
    }

}