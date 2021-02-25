var typeaheadData = [];

$(document).ready(function () {
    $('.hamburger').click(function () {
        var navMenu = $('.navigationItemsWrap');
        if ($(navMenu).css("display") == "none") {
            //$('.navigationItemsWrap').css("display", "block");
            $(navMenu).slideDown();
        } else {
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

    $('#searchBtn').click(function () {
        var searchValue = $('#nameOrIdSearchForm').val().trim();
        var isValid = true;

        if (!searchValue) {
            $('#nameOrIdSearchForm').css('border', '1px solid red');
            return false;
        } else {

            $.ajax({
                url: '/search/check/' + searchValue,
                type: 'GET',
                dataType: 'text',
                async: false,
                success: function (test) {
                    if (test === "false") {
                        isValid = false;
                        $('#nameOrIdSearchForm').css('border', '1px solid red');
                        $('#nameOrIdSearchForm').val("");
                        $('#nameOrIdSearchForm').attr("placeholder", "Invalid Patient");
                    }
                },
                error: function () {
                    isValid = false;
                }
            });

        }
        return isValid;
    });

    /* Patient Search typeahead on the homepage*/
    if ($("#patientSearchContainer").length > 0) {

        var patient_data = [];

        // Get Patients from server
        $.getJSON("/search/typeahead/patients", function (data) {

            patient_data = data;

            var patients = new Bloodhound({

                datumTokenizer: function (d) {

                    // break apart first/last name into separate words
                    var words = Bloodhound.tokenizers.whitespace(d.id + " " + d.firstName + " " + d.lastName + " " + d.phoneNumber);

                    // make all possible substring words
                    // Original Word: Name
                    // Add  ame, me, e to the list also
                    $.each(words, function (k, v) {
                        var i = 0;
                        while ((i + 1) < v.length) {
                            words.push(v.substr(i, v.length));
                            i++;
                        }
                    });

                    return words;
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
                    suggestion: Handlebars.compile('<p class="patientResult"><a href="/triage/{{id}}">' +
                        '<img class="photo" src="{{photo}}" height="80" width="80">' +
                        '<span class="name">({{id}}) {{firstName}} {{lastName}} {{phoneNumber}}</span>' +
                        '<span class="age">{{age}}</span>' +
                        '</a></p>')
                }
            });

            // Reenable search input field
            $("input.patientSearch").removeClass("loading")
                .removeAttr("disabled")
                .attr("placeholder", "ID, Name, or Phone #");

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

    substringMatcher: function (strs) {

        return function findMatches(q, cb) {
            var matches, substrRegex;

            // an array that will be populated with substring matches
            matches = [];

            // regex used to determine if a string contains the substring `q`
            substrRegex = new RegExp(q, 'i');

            // iterate through the pool of strings and for any string that
            // contains the substring `q`, add it to the `matches` array
            $.each(strs, function (i, str) {
                if (substrRegex.test(str)) {
                    // the typeahead jQuery plugin expects suggestions to a
                    // JavaScript object, refer to typeahead docs for more info
                    matches.push({value: str});
                }
            });

            cb(matches);
        };
    },
    /**
     * Sets a global variable for the typeahead data. Allows for later initalization via intializeTypeAhead() below.
     * This is useful when you don't need to initalize an input box with typeahead until the user performs an action.
     * This may cause problems in the future if data sets become too large for loading and intializeTypeAhead() is called
     * before they load.
     *
     * @param post_URL url to retrieve the data from the server via json
     */
    setGlobalVariable: function (post_URL) {

        $.getJSON(post_URL, function (data) {

            typeaheadData = data
        });
    },
    /**
     * This combines setGlobalVariable and initalizeTypeAhead.
     */
    setGlobalVariableAndInitalize: function (post_URL, element, name, hint, highlight){
        $.getJSON(post_URL, function (data) {

            typeaheadData = data
        }).done(function () {

            typeaheadFeature.initalizeTypeAhead(element, name, hint, highlight);
        });
    },
    /**
     * Uses the global data object to create typeahead functionality on an element
     */
    initalizeTypeAhead: function (element, name, hint, highlight, customMatcher, customSelect) {
        var matcher = customMatcher || typeaheadFeature.substringMatcher;
        var select = customSelect || function() {};

        //the error happens in here when initating typeahead VVVVV
        //need to restructure the medicine JSON from the server to match
        //the json from diagnoses
        $(element).typeahead({
                hint: hint,
                highlight: highlight
            },
            {
                name: name,
                source: matcher(typeaheadData)
            }).on('typeahead:selected', select);

    },
    destroyTypeAhead: function (elementSelector) {
        $(elementSelector).typeahead('destroy');
    }
};

/**
 * Calculates a user's BMI score.
 *
 * @param system either "metric" or "standard".
 * @param weight the weight of the patient in kg or lbs
 * @param height the height of the patient in m or inches (note: NOT cm)
 * @returns *the BMI score of the patient or null if you did something wrong.
 */
function calculateBMIScore(system, weight, height) {

    if (!system || !weight || !height){
        return null;
    }

    var bmi = null;

    if (system === "metric") {

        bmi = Math.round((weight / (height * height)))
    } else if (system === "standard") {

        bmi = Math.round((weight / (height * height)) * 703);
    }

    if(bmi != null && (bmi < 5 || bmi > 150)) {
        bmi = null;
    }

    return bmi;
}

/**
 * checks if a value is numeric
 * @param n value to check
 * @returns boolean true if numeric, false if not
 */
function isNumeric(n) {
    return !isNaN(parseFloat(n)) && isFinite(n);
}


//This script will run on all the pages in which femr.js is loaded onto
//It forces a page refresh if the user is inactive
var hv = $('#h_v').val();

var timeOutTime = hv;
var t;
if(timeOutTime!=null) {
    window.onload = ResetTime;
// DOM Events
    document.onmousemove = ResetTime;
    document.onkeypress = ResetTime;
    document.ontouchend = ResetTime;
}
function logout() {
    window.location.assign(window.location.href);
}

function ResetTime() {
    clearTimeout(t);
    t = setTimeout(logout, timeOutTime);
    // 1000 milisec = 1 sec
}