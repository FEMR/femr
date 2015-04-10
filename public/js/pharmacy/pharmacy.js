$(document).ready(function () {
    $('.replaceBtn').click(replaceClick);


    $(".prescriptionName input").combobox({
        select: function(event, ui) {
            $(this).val(ui.item.id);
        },
        source:  function(request, response) {
            $.ajax({
                url: "/medical/getMedication?term=" + request.term,
                type: "GET",
                dataType: "json",
                success: function (data) {
                    response($.map(data.page_data, function (item) {
                        return {
                            label: item.name + " (" + item.form + ")",
                            value: item.name + " (" + item.form + ")",
                            name: item.name,
                            id: item.id
                        }
                    }));
                }
            })
        }
    });

    $(".prescriptionAdministrationDays > input").on("keyup change",
        calculateTotalPrescriptionAmount
    );
    $(".prescriptionAdministrationName > select").on("change",
        calculateTotalPrescriptionAmount
    );
});

function calculateTotalPrescriptionAmount() {
    var $prescriptionRow = $(this).closest(".prescriptionRow");
    var $amountInput = $prescriptionRow.find(".prescriptionAmount input");
    var modifier = parseFloat($prescriptionRow.find(".prescriptionAdministrationName select option:selected").attr("data-modifier"));
    var days = parseFloat($prescriptionRow.find(".prescriptionAdministrationDays input").val());

    $amountInput.val(Math.ceil(modifier * days));
}


function replaceClick() {
    var $container = $(this).closest("li");
    var $replacementDiv = $container.find(".replacement");
    if ($replacementDiv.hasClass("hidden"))
        $replacementDiv.removeClass("hidden")
    else {
        $replacementDiv.addClass("hidden");
        // Reset input's to defaults
        $replacementDiv.find("input").val("");
        $replacementDiv.find(".prescriptionAmount input").val(0);
    }
}

