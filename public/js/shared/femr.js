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

        var states = ['Alabama', 'Alaska', 'Arizona', 'Arkansas', 'California',
            'Colorado', 'Connecticut', 'Delaware', 'Florida', 'Georgia', 'Hawaii',
            'Idaho', 'Illinois', 'Indiana', 'Iowa', 'Kansas', 'Kentucky', 'Louisiana',
            'Maine', 'Maryland', 'Massachusetts', 'Michigan', 'Minnesota',
            'Mississippi', 'Missouri', 'Montana', 'Nebraska', 'Nevada', 'New Hampshire',
            'New Jersey', 'New Mexico', 'New York', 'North Carolina', 'North Dakota',
            'Ohio', 'Oklahoma', 'Oregon', 'Pennsylvania', 'Rhode Island',
            'South Carolina', 'South Dakota', 'Tennessee', 'Texas', 'Utah', 'Vermont',
            'Virginia', 'Washington', 'West Virginia', 'Wisconsin', 'Wyoming'
        ];

        // Get Patients from server later
        var data = [
            {
                patientId: 1,
                firstName: "Test",
                lastName: "Patient",
                photo: "/photo/patient/1?showDefault=false"
            },
            {
                patientId: 2,
                firstName: "Test 2",
                lastName: "Patient 2",
                photo: "/photo/patient/1?showDefault=false"
            }
        ];


        var patients = new Bloodhound({
            datumTokenizer: function (d) {
                return Bloodhound.tokenizers.whitespace(d.firstName);
            },
            queryTokenizer: Bloodhound.tokenizers.whitespace,
            prefetch: data, //'../data/films/post_1960.json',
            remote: data //'../data/films/queries/%QUERY.json'
            /*
            remote: {
                url: 'Search.aspx/searchStaffByName',
                replace: function (url, query) {
                    searchQuery = query;
                    return url;
                },
                ajax: {
                    beforeSend: function (jqXhr, settings) {
                        settings.data = JSON.stringify({
                            q: searchQuery
                        });
                        jqXhr.setRequestHeader('Content-Type', 'application/json; charset=UTF-8');
                    },
                    dataFilter: function (data, type) {
                        if (type === "json") {
                            data = $.parseJSON(data);
                            if (typeof data.d === 'object' && data.d.Count > 0) {
                                return data.d.Records;
                            }
                        }
                    },
                    type: 'POST'
                }
            }
             */
        });
        patients.initialize();

        // <img src="/photo/patient/1?showDefault=false" height="90" width="90">

        //initalize typeahead
        $("#patientSearchContainer .patientSearch").typeahead(null, {
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
                    '<div class="empty-message">',
                    'unable to find any Patients that match the current query',
                    '</div>'
                ].join('\n'),
                suggestion: Handlebars.compile('<p>template: <strong>{{firstName}} {{lastName}}</strong> – ' +
                    '<img src="{{photo}}" height="20" width="20"></p>')
            }
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

