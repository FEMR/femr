/**
 * Created by amneyiskandar on 2/24/15.
 */
var historyFieldValidator = {



};

function validate() {
    //always check vitals first
    triageFieldValidator.validatePatientVitals();
    triageFieldValidator.validatePatientInformation();
    return historyFieldValidator.isValid;
}