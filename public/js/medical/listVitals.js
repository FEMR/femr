$(document).ready(function () {
    $('#vitalTable').tableScroll({ height: 670, width: 250 });
    calculateBMI();

});


function calculateBMI() {
    var $weights = $('#weight td');
    var weight_lbs = null;
    var $heights = $('#height td');
    var height_in = null;
    var height_ft = null;
    $($weights.get().reverse()).each(function () {
        if ($(this).html() !== null && $(this).html() !== '' && typeof ( $(this).html() ) !== 'undefined') {
            weight_lbs = xarseInt($(this).html());
            return false;
        }
    });
    $($heights.get().reverse()).each(function () {
        if (height_ft === null || height_ft === '' || isNaN(height_ft)) {
            height_ft = parseInt($(this).html().split("'")[ 0 ].trim());
        }
        if (height_in === null || height_in === '' || isNaN(height_in)) {
            height_in = parseInt($(this).html().split("'")[ 1 ].trim());
        }
    });

    var bmi = Math.round(( weight_lbs / ( ( height_ft * 12 + height_in ) * ( height_ft * 12 + height_in ) ) ) * 703);

    if (!isFinite(bmi) || bmi === '' || bmi === null){
        $('#bmi').text("N/A");
    }else{
        $('#bmi').text(bmi);
    }

}