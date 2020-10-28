$(document).ready(function () {

    $('#vitalTable').tableScroll({ height: 670, width: 250 });

    //sets the patient's BMI score in the patient overview everytime
    //listVitals loads.
    setBMI();
});

/**
 * Gets and sets the BMI score on the listVitals page.
 */
function setBMI() {
    var weight = recentVitals.getCurrentWeight();
    var height_in = recentVitals.getCurrentHeightInches();
    var height_ft = recentVitals.getCurrentHeightFeet();

    var totalInches = height_in + height_ft * 12;
    var bmiScore = calculateBMIScore("standard", weight, totalInches);

    // Problem finding weight and height
    if (!isFinite(bmiScore) || bmiScore === null) {
        $("#bmi").text("N/A");
    } else {
        $('#bmi').text(bmiScore);
    }
}