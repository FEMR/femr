$(document).on('blur', 'input, textarea', function () {
    //this is a hacky fix for the navigation bar dropping on iPads. After the keyboard
    //appears and disappears, the navigation bar would fall to the center of the screen and
    //fix itself after scrolling.
    setTimeout(function () {
        window.scrollTo(document.body.scrollLeft, document.body.scrollTop);
    }, 0);
});

$(document).ready(function () {
    $('.hamburger').click(function(){
        var navMenu = $('.navigationItemsWrap');
        if ($(navMenu).css("display") == "none"){
            //$('.navigationItemsWrap').css("display", "block");
            $(navMenu).slideDown();
        }else{
            $(navMenu).slideUp();
        }

    });
  var $id = $('#id');
  $id.change(function () {
    var idString = $id.val();
    var intRegex = /^\d+$/;
    if (intRegex.test(idString)) {
      $('#id').css('border', '');
    } else {
      $id.val('');
      $id.css('border-color', 'red');
      $id.attr('placeholder', 'Invalid Id');
    }
  });

  $('.idSearch').click(function () {
    var idString = $id.val();
    var intRegex = /^\d+$/;
    if (intRegex.test(idString)) {
      $('#id').css('border', '');
    } else {
      $id.val('');
      $id.css('border-color', 'red');
      $id.attr('placeholder', 'Invalid Id');
      return false;
    }
  });

    $('#searchBtn').click(function(){
        var searchValue = $('#nameOrIdSearchForm').val().trim();
        var isValid = true;

        if (!searchValue){
            $('#nameOrIdSearchForm').css('border', '1px solid red');
            return false;
        }else{

            $.ajax({
                url: '/search/check/' + searchValue,
                type: 'GET',
                dataType: 'text',
                async: false,
                success: function (test) {
                    if (test === "false"){
                        isValid = false;
                        $('#nameOrIdSearchForm').css('border', '1px solid red');
                        $('#nameOrIdSearchForm').val("");
                        $('#nameOrIdSearchForm').attr("placeholder", "Invalid Patient");
                    }
                },
                error: function (response) {
                    isValid = false;
                }
            });

        }
        return isValid;
    });

    /* Search typeahead */
    if( $("#patientSearchContainer").length > 0 ) {

        var patient_data = [];

        // Get Patients from server
        $.getJSON("/search/typeahead/patients", function (data) {

            patient_data = data;

            console.log(patient_data);

            var patients = new Bloodhound({
                datumTokenizer: function (d) {
                    // tokenize the fields that will need to be searched
                    return Bloodhound.tokenizers.whitespace(d.firstName, d.lastName);
                },
                queryTokenizer: Bloodhound.tokenizers.whitespace,
                local: patient_data,
                limit: 30
            });
            patients.initialize();


            var typeahead_options = {

                highlight: true

            };

            //initalize typeahead
            $("#patientSearchContainer").find(".patientSearch").typeahead(typeahead_options, {
                name: 'patients',
                displayKey: 'firstName',
                source: patients.ttAdapter(),
                matcher: function (item) {
                    if (item.toLowerCase().indexOf(this.query.trim().toLowerCase()) != -1) {
                        return true;
                    }
                },
                templates: {
                    empty: [
                        '<div class="emptyMessage">',
                        'No matching patients found',
                        '</div>'
                    ].join('\n'),
                    suggestion: Handlebars.compile('<p class="patientResult"><a href="/triage/{{id}}">'+
                            '<img class="photo" src="{{photo}}" height="80" width="80">' +
                            '<span class="name">({{id}}) {{firstName}} {{lastName}}</span>' +
                            '<span class="age">{{age}}</span>' +
                            '</a></p>')
                }
            });
        });

    }


});

/*
 * must be in one of the following formats,  excludes 1.
 * 111.11
 * 1
 * 0.1
 * 1.1
 */
function decimalCheck(wonkyDeci) {
  var regexDecimal = /^\d+(\.\d{1,2})?$/;
  return regexDecimal.test(wonkyDeci);
}
/*
 * positive integer numbers
 * excludes 1.
 */
function integerCheck(wonkyInt) {
  var regexInt = /^\d+$/;
  return regexInt.test(wonkyInt);
}

/*
 * Checks if value of input is equal to NaN
 */
function randomString(strVal) {
  return isNaN(strVal);
}

/*
 * Typeahead initalizer
 */
var typeaheadFeature = {
    initalizeTypeAhead: function(elementSelector, name, data){
        $(elementSelector).typeahead({
            name: name,
            local: data
        });
    },
    destroyTypeAhead: function(elementSelector){
        $(elementSelector).typeahead('destroy');
    }
};

