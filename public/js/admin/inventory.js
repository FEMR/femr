$(document).ready(function () {



});



/*
 Defines a medication, matches MedicationItem
 */
function Medication(name, quantity) {
    this.name = name;
    this.quantity_total = quantity;
}


//Medication.prototype = {
//    //add and remove both interact directly with the database
//    add: function () {
//
//    },
//    remove: function () {
//
//    }
//}