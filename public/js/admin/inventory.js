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
    bindEditInventoryDialog();

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

function bindEditInventoryDialog() {
    $('#addMed').click(function () {
        $.ajax({
            url: '/admin/inventory/add',
            type: 'GET',
            success: function (partialView) {
                $('#inventoryPartial').html(partialView);
                $('#editDialog').dialog("open");


                //click event on add button
                $('#addMedToList').click(function () {
                    var name = $('#nameInput').val();
                    var quantity = $('#amountInput').val();
                    //make sure both values exist
                    if (name && quantity) {
                        var medication = new Medication(name, quantity);
                        window.medicationsToAdd.push(medication);
                        //use input elements in the list so values are submitted on POST.
                        //size of medicationsToAdd array is used for identifying the
                        //array index
//                        $('#listMedication ol').append(
//                          "<li>" +
//                                "<input type='text' value='" + medication.name + "' name='medName[" + (window.medicationsToAdd.length - 1) + "]' disabled/>" +
//                              "<input type='text' value='" + medication.quantity_total + "' name='quantity[" + (window.medicationsToAdd.length - 1) + "]' disabled/>" +
//                              "</li>"
//                        );
                        $('#listMedication ol').append(
                                "<li>" +
                                "<input type='text' value='" + medication.name + "' name='name" + (window.medicationsToAdd.length - 1) + "' readonly/>" +
                                "<input type='text' value='" + medication.quantity_total + "' name='quantity" + (window.medicationsToAdd.length - 1) + "' readonly/>" +
                                "</li>"
                        );

                        $('#nameInput').val("");
                        $('#amountInput').val("");
                    }
                });

                $('#cancelAllNewMeds').click(function () {
                    $('#editDialog').dialog("close");
                });


            },
            error: function (response) {
                alert("this shouldn't happen");
            }
        });
    });
}


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