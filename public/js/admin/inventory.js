$(document).ready(function () {
    $('#editDialog').dialog({
        dialogClass: 'editUserDialog',
        autoOpen: false,
        draggable: true,
        position: 'center',
        modal: true,
        height: 450,
        width: 500

    });

    window.medicationsToAdd = [];

    $('.remove').find('span').click(function(){
        var name = $(this).parent().parent().find('.name').text();//.find('.name').text();
        if (confirm("Are you sure you want to PERMANENTLY remove " + name + " from your inventory? No data will be kept on " + name + ".")){
//            var id = $(this).find('input').val();
            return true;
        }else{
            //do nothing
            return false;
        }

    });


});



/*
 Defines a medication, matches MedicationItem
 */
function Medication(name, quantity) {
    this.name = name;
    this.quantity_total = quantity;
}

/*
 /**
 * populates current medications as they exist on the page
 *
 function populateAllCurrentMedications(){
 var medications = $('#inventoryTable tbody > tr');
 var medication;
 $(medications).each(function(){
 var name = $(this).find('.name').text();
 var totalQuantity = $(this).find('.totalQuantity').text();
 var currentQuantity = $(this).find('.currentQuantity').text();
 medication = new Medication(name, totalQuantity, currentQuantity);
 window.allMedications.push(medication);
 });
 } */

//Medication.prototype = {
//    //add and remove both interact directly with the database
//    add: function () {
//
//    },
//    remove: function () {
//
//    }
//}