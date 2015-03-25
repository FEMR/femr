$(document).ready(function () {
    $('#vitalTable').tableScroll({ height: 670, width: 250 });
    calculateBMI();

});


function calculateBMI() {
    var $weight;
    var weight = null
    var weight_lbs = null;
    var $height;
    var feet = null;
    var inches = null;
    var height_in = null;
    var height_ft = null;


    $weight = $("#weight td:last-child");
    weight = $weight.attr("data-weight");
    if (weight !== null && weight !== '' && typeof ( weight ) !== 'undefined') {
        weight_lbs = parseFloat(weight);
    }

    $height = $("#height td:last-child");
    feet = $height.attr("data-feet");
    inches = $height.attr("data-inches");
    if (feet !== null && feet !== '' && typeof ( feet ) !== 'undefined') {
        height_ft = parseFloat(feet);
    }
    if (inches !== null && inches !== '' && typeof ( inches ) !== 'undefined') {
        height_in = parseFloat(inches);
    }

    var totalInches = height_in + height_ft * 12;

    // BMI for Metric - Alaa Serhan
    var bmi = Math.round((weight_lbs / (totalInches * totalInches)) * 703);
    if (!isFinite(bmi) || bmi === '' || bmi === null){
       $('#bmi').text("N/A");
    }else{
        $('#bmi').text(bmi);
    }

}