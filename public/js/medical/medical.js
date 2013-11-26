//BMI auto- calculator

$(document).ready(function () {

  $('#resetVitalsBtn').on('click', function () {
    $('#populatedVitals').addClass('hidden');
    $('#newVitals').removeClass('hidden');
    $('#saveVitalsBtn').removeClass('hidden');
    $('#resetVitalsBtn').addClass('hidden');
  });

  $('#saveVitalsBtn').on('click', function () {
    var newVitals = {};

    var bpSystolic = $('#bpSystolic');
    var bpDiastolic = $('#bpDiastolic');
    var heartRate = $('#heartRate');
    var temperature = $('#temperature');
    var respRate = $('#respRate');
    var oxygen = $('#oxygen');
    var heightFt = $('#heightFt');
    var heightIn = $('#heightIn');
    var weight = $('#newWeight');

    if (bpSystolic.val() !== '') {
      newVitals.bpSystolic = bpSystolic.val();

      $("#triageBpSystolic").text(newVitals.bpSystolic);
      bpSystolic.val('');
    }

    if (bpDiastolic.val() !== '') {
      newVitals.bpDiastolic = bpDiastolic.val();

      $("#triageBpDiastolic").text(newVitals.bpDiastolic);
      bpDiastolic.val('');
    }

    if (heartRate.val() !== '') {
      newVitals.heartRate = heartRate.val();

      $("#triageHeartRate").text(newVitals.heartRate);
      heartRate.val('');
    }

    if (temperature.val() !== '') {
      newVitals.temperature = temperature.val();

      $("#triageTemperature").text(newVitals.temperature);
      temperature.val('');
    }

    if (oxygen.val() !== '') {
      newVitals.oxygen = oxygen.val();

      $("#triageOxygen").text(newVitals.oxygen);
      oxygen.val('');
    }

    if (respRate.val() !== '') {
      newVitals.respRate = respRate.val();

      $("#triageRespRate").text(newVitals.respRate);
      respRate.val('');
    }

    if (heightFt.val() !== '') {
      newVitals.heightFt = heightFt.val();

      $("#heightFeet").text(newVitals.heightFt);
      $("#triageHeightFt").text(newVitals.heightFt);
      heightFt.val('');
    }

    if (heightIn.val() !== '') {
      newVitals.heightIn = heightIn.val();

      $("#heightInches").text(newVitals.heightIn);
      $("#triageHeightIn").text(newVitals.heightIn);
      heightIn.val('');
    }

    if (weight.val() !== '') {
      newVitals.weight = weight.val();

      $("#weight").text(newVitals.weight);
      weight.val('');
    }

    $.ajax({
             url: '/medical/updateVitals/' + $("#patientId").val(),
             type: 'POST',
             data: newVitals,
             dataType: 'json'
           }).done(function () {
                         calculateBMI();
                         $('#populatedVitals').removeClass('hidden');
                         $('#newVitals').addClass('hidden');
                         $('#saveVitalsBtn').addClass('hidden');
                         $('#resetVitalsBtn').removeClass('hidden');
                   });
  });

  calculateBMI();

  //Unhides a prescription input box everytime
  //the + button is clicked (max of 5)
  $('#addPrescriptionButton').click(function () {

    if (typeof $("body").data("script") === "undefined") {
      $("body").data("script", 2);
    } else if ($("body").data("script") < 5) {
      $("body").data("script", $("body").data("script") + 1);
    } else {
      return;
    }
    $("#prescription" + $("body").data("script")).removeClass("hidden");
    return;
  });

  //Unhides a problem input box everytime
  //the + button is clicked (max of 5)
  $('#addProblemButton').click(function () {
    if (typeof $("body").data("prob") === "undefined") {
      $("body").data("prob", 2);
    } else if ($("body").data("prob") < 5) {
      $("body").data("prob", $("body").data("prob") + 1);
    } else {
      return;
    }
    $("#problem" + $("body").data("prob")).removeClass("hidden");
    return;
  });

  //controls the tabbed viewing of HPI and Treatment
  $('#myTab a').click(function (e) {
    e.preventDefault()
    $(this).tab('show')

  });

  $('#myTab a').click(function () {
    if ($(this).attr('id') === "hpiTab") {
      showHpi();
    } else if ($(this).attr('id') === "treatmentTab") {
      showTreatment();
    }
  });
});

function showTreatment() {
  $('#hpiControl').addClass('hidden');
  $('#treatmentControl').removeClass('hidden');
}
function showHpi() {
  $('#hpiControl').removeClass('hidden');
  $('#treatmentControl').addClass('hidden');
}

function calculateBMI() {
  if ($('#heightFeet').text() && $('#weight').text() && $('#heightInches').text()) {
    var weight_lbs = parseInt($('#weight').text());
    var height_in = parseInt($('#heightInches').text());
    var height_ft = parseInt($('#heightFeet').text());

    height_in = height_in + height_ft * 12;

    $('#bmi').text(Math.round((weight_lbs / (height_in * height_in)) * 703));
  }
}
