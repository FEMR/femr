var filterMenuModule = (function () {

    var activeSubMenu = null;
    var meds = null;
    var form = $("#graph-options");
    var filterValues = {

        dataset1: null,
        dataset2: null,
        graphType: null,
        startDate: null,
        endDate: null,
        groupFactor: false,
        rangeStart: null,
        rangeEnd: null
    };

    var filterFields = {
        dataset1: $("#primaryDataset"),
        dataset2: $("#secondaryDataset"),
        graphType: $("#graphType"),
        startDate: $("#startDate"),
        endDate: $("#endDate"),
        groupPrimary: $("#groupPrimary"),
        groupFactor: $("#groupPrimaryData"),
        rangeStart: $("#filterRangeStart"),
        rangeEnd: $("#filterRangeEnd"),
        medicationName: $("#medicationName"),
        MissionTripId: $("#MissionTripId")
    };

    var filterMenus = {
        errors: $("#filter-errors"),
        dataset1: $("#gdata1-menu"),
        dataset2: $("#gdata2-menu"),
        graphType: $("#gtype-menu"),
        filter: $("#gfilter-menu")
    };


    var closeSubMenu = function () {

        if (activeSubMenu == null) return;

        $(activeSubMenu).removeClass("active");
        $(activeSubMenu).children("ul.submenu").hide();

        activeSubMenu = null;
    };

    var openSubMenu = function () {

        if (activeSubMenu == null) return;

        // reset any previously shown menu
        $(".menu-item").removeClass("active").find("ul.submenu").hide();

        $(activeSubMenu).addClass("active");
        $(activeSubMenu).children("ul.submenu").show();
    };

    var clearFilterPrimary = function () {

        $(filterFields.rangeStart).val("");
        $(filterFields.rangeEnd).val("");
    };

    var clearGraphOptions = function () {

        // Clear Values
        filterValues.dataset1 = null;
        filterValues.dataset2 = null;
        filterValues.graphType = null;
        filterValues.startDate = null;
        filterValues.endDate = null;

        // Clear Form Fields
        $(filterFields.dataset1).val('');
        $(filterFields.dataset2).val('');
        $(filterFields.graphType).val('');

        // Set Date to previous month
        $(filterFields.startDate).val('');
        $(filterFields.endDate).val('');

        // Clear Visual Fields
        $(filterMenus.dataset1).find(".val").text("");
        $(filterMenus.dataset2).find(".val").text("");
        $(filterMenus.graphType).find(".val").text("");
        $(filterMenus.MissionTripId).find(".val").text("");

        // Set Default EndDate to today
        var defaultEndDate = new Date();
        if (Object.prototype.toString.call(defaultEndDate) === "[object Date]" && !isNaN(defaultEndDate.getTime())) {

            var monthNum = defaultEndDate.getUTCMonth() + 1;
            var dayNum = defaultEndDate.getUTCDate();
            if (dayNum < 10 && dayNum >= 0) dayNum = '0' + dayNum.toString();
            var yearNum = defaultEndDate.getUTCFullYear();

            var endDateInputString = yearNum + '-' + monthNum + '-' + dayNum;
            var endDateString = monthNum + '/' + dayNum + '/' + yearNum;

            // Set End Date in text and Date input item
            $(filterMenus.filter).find(".val").find(".date").find(".end").text(endDateString);
            $(filterFields.endDate).val(endDateInputString);
            filterValues.endDate = endDateString;


        }
        else {

            $(filterMenus.filter).find(".val").find(".date").find(".end").text("");
        }

        // make default startDate of 120 days ago
        var defaultStartDate = new Date(defaultEndDate.getTime() - 120 * 24 * 60 * 60 * 1000);
        // date field is in format yyyy-MM-dd --> view String like mm/dd/yyyy
        if (Object.prototype.toString.call(defaultStartDate) === "[object Date]" && !isNaN(defaultStartDate.getTime())) {

            var monthNum = defaultStartDate.getUTCMonth() + 1;
            var dayNum = defaultStartDate.getUTCDate();
            if (dayNum < 10 && dayNum >= 0) dayNum = '0' + dayNum.toString();
            var yearNum = defaultStartDate.getUTCFullYear();

            var startDateInputString = yearNum + '-' + monthNum + '-' + dayNum;
            var startDateString = monthNum + '/' + dayNum + '/' + yearNum;

            // Set Start Date in text and Date input item
            $(filterMenus.filter).find(".val").find(".date").find(".start").text(startDateString);
            $(filterFields.startDate).val(startDateInputString);
            filterValues.startDate = startDateString;


        }
        else {

            $(filterMenus.filter).find(".val").find(".date").find(".start").text("");
        }

        $(filterFields.rangeStart).val("");
        $(filterFields.rangeEnd).val("");
        $(filterFields.medicationName).val("");
        $(filterFields.groupPrimary).prop('checked', false);
        $(filterFields.groupFactor).val("10");
        $(filterFields.MissionTripId).val("");

    };

    var saveAsImage = function (scaleFactor, imageName) {

        scaleFactor = typeof scaleFactor !== 'undefined' ? scaleFactor : 1;

        saveSvgAsPng(document.getElementById("graph"), imageName, scaleFactor);

        closeSubMenu();
        return false;
    };

    var exportData = function () {

        $(form).submit();

        return false;
    };

    var showImageOptions = function () {

        $(".save-image-cont").find(".options").show();
        return false;
    };

    var hideImageOptions = function () {

        $(".save-image-cont").find(".options").hide();
        return false;
    };

    var chooseImageSize = function () {

        $(".save-image-cont").find(".options").hide();

        // get Image Size
        var imageSize = $(this).data("imagesize");
        //console.log(imageSize);

        var currWidth = $(".main").width();

        var graphType = graphLoaderModule.getGraphType();
        var imageName = graphType + "-chart-" + imageSize + ".png"

        // figure out scaleFactor
        if (imageSize == "small") {

            //(700x350)
            var scaleFactor = 700 / currWidth;
            saveAsImage(scaleFactor, imageName);
        }
        else if (imageSize == "medium") {

            // (1000x500)
            var scaleFactor = 1000 / currWidth;
            saveAsImage(scaleFactor, imageName);
        }
        // default to large
        else {

            // (1200x600)
            var scaleFactor = 1200 / currWidth;
            saveAsImage(scaleFactor, imageName);
        }

        // Save Image


        return false;
    };

    var updateAvailableFilterChoices = function () {

        // run after any filter change
        // make sure what is selected is still valid
        // enable/disable invalid choices

        // Check currently selected dataset2 validity
        // -- clear if not valid
        if (filterValues.dataset2 != null && !allowedFilterValues.isSecondaryDataAllowed(filterValues.dataset1, filterValues.dataset2)) {

            // clear Dataset2
            filterValues.dataset2 = null;
            $(filterMenus.dataset2).find(".val").text("");
            $(filterFields.dataset2).val("");
        }

        // Check currently selected graphType validity
        // - clear if not valid
        // -- is set
        // -- allowed for dataset1
        // -- dataset1 and dataset2 are set and graph is for combinable
        if (filterValues.graphType != null) {

            if (filterValues.dataset1 != null && filterValues.dataset2 != null && !allowedFilterValues.isCombinableGraph(filterValues.graphType)) {

                // clear Graph Type
                filterValues.graphType = null;
                $(filterMenus.graphType).find(".val").text("");
                $(filterFields.graphType).val("");

            }
            else if (filterValues.dataset2 == null && !allowedFilterValues.isGraphTypeAllowed(filterValues.dataset1, filterValues.graphType)) {

                // clear Graph Type
                filterValues.graphType = null;
                $(filterMenus.graphType).find(".val").text("");
                $(filterFields.graphType).val("");
            }

            if (filterValues.graphType == "line" || filterValues.graphType == "scatter") {
                $(filterFields.groupPrimary).attr("disabled", true);
            }
            else {
                $(filterFields.groupPrimary).removeAttr("disabled");
            }
        }


        // Disable Secondary Data as needed
        $(filterMenus.dataset2).find(".submenu").find("a").not(".clear").each(function () {
            if (!allowedFilterValues.isSecondaryDataAllowed(filterValues.dataset1, $(this).data("dname2"))) {
                $(this).addClass('disabled');
            }
            else {
                $(this).removeClass('disabled');
            }
        });


        // Disable Graph Types as needed
        if (filterValues.dataset2 == null) {

            $(filterMenus.graphType).find(".submenu").find("a").not(".clear").each(function () {
                if (!allowedFilterValues.isGraphTypeAllowed(filterValues.dataset1, $(this).data("gtype"))) {
                    $(this).addClass('disabled');
                }
                else {
                    $(this).removeClass('disabled');
                }
            });
        }
        else {

            // Disable All Types but Combined
            $(filterMenus.graphType).find(".submenu").find("a").not(".clear").each(function () {
                if (!allowedFilterValues.isCombinableGraph($(this).data("gtype"))) {
                    $(this).addClass('disabled');
                }
                else {
                    $(this).removeClass('disabled');
                }
            });

        }

    };

    var chooseDataSet1 = function () {

        if ($(this).hasClass('clear')) {

            // clear Dataset2
            filterValues.dataset1 = null;
            $(filterMenus.dataset1).find(".val").text("");
            $(filterFields.dataset1).val("");
        }
        else {

            var newVal = $(this).data("dname1");
            if (newVal != filterValues.dataset1) {

                // Set New Dataset 1 value
                filterValues.dataset1 = newVal;
                $(filterMenus.dataset1).find(".val").text($(this).text());
                $(filterFields.dataset1).val(filterValues.dataset1);
            }

            // set group to true when selecting age for the first time
            if (filterValues.dataset1 == "age") {

                filterValues.groupFactor = true;
                //$(filterFields.groupFactor).attr("checked",true);
                $(filterFields.groupFactor).prop('checked', true);
            }
            else {

                filterValues.groupFactor = false;
                //$(filterFields.groupFactor).attr("checked",false);
                $(filterFields.groupFactor).prop('checked', false);
            }
        }

        updateAvailableFilterChoices();
        closeSubMenu();
        return false;
    };

    var chooseDataSet2 = function () {

        // do nothing for disabled secondary types
        if ($(this).hasClass('disabled')) return false;

        if ($(this).hasClass('clear')) {

            // clear Dataset2
            filterValues.dataset2 = null;
            $(filterMenus.dataset2).find(".val").text("");
            $(filterFields.dataset2).val("");
        }
        else {
            var newVal = $(this).data("dname2");
            if (newVal != filterValues.dataset2) {

                filterValues.dataset2 = newVal;
                $(filterMenus.dataset2).find(".val").text($(this).text());
                $(filterFields.dataset2).val(filterValues.dataset2);
            }
        }

        updateAvailableFilterChoices();
        closeSubMenu();
        return false;
    };

    var chooseStartDate = function () {

        var dateString = $(filterFields.startDate).val();
        // date field is in format yyyy-MM-dd --> convert to Date object and build string like mm/dd/yyyy
        var startDate = new Date(dateString);
        if (Object.prototype.toString.call(startDate) === "[object Date]" && !isNaN(startDate.getTime())) {

            var monthNum = startDate.getUTCMonth() + 1;
            var dayNum = startDate.getUTCDate();
            if (dayNum < 10 && dayNum >= 0) dayNum = '0' + dayNum.toString();
            var yearNum = startDate.getUTCFullYear();
            var startDateString = monthNum + '/' + dayNum + '/' + yearNum;
            $(filterMenus.filter).find(".val").find(".date").find(".start").text(startDateString);
            filterValues.startDate = startDateString;
        }
        else {

            $(filterMenus.filter).find(".val").find(".date").find(".start").text("");
        }

    };

    var chooseEndDate = function () {

        var dateString = $(filterFields.endDate).val();
        // date field is in format yyyy-MM-dd --> convert to Date object and build string like mm/dd/yyyy
        var endDate = new Date(dateString);
        if (Object.prototype.toString.call(endDate) === "[object Date]" && !isNaN(endDate.getTime())) {

            var monthNum = endDate.getUTCMonth() + 1;
            var dayNum = endDate.getUTCDate();
            if (dayNum < 10 && dayNum >= 0) dayNum = '0' + dayNum.toString();
            var yearNum = endDate.getUTCFullYear();
            var endDateString = monthNum + '/' + dayNum + '/' + yearNum;
            $(filterMenus.filter).find(".val").find(".date").find(".end").text(endDateString);
            filterValues.endDate = endDateString;
        }
        else {
            $(filterMenus.filter).find(".val").find(".date").find(".end").text("");
        }
    };

    var chooseGraphType = function () {

        // do nothing for disabled secondary types
        if ($(this).hasClass('disabled')) return false;

        if ($(this).hasClass('clear')) {

            // clear Graph Type
            filterValues.graphType = null;
            $(filterMenus.graphType).find(".val").text("");
            $(filterFields.graphType).val("");
        }
        else {

            var newVal = $(this).data("gtype");
            if (newVal != filterValues.graphType) {

                filterValues.graphType = newVal;
                $(filterMenus.graphType).find(".val").text($(this).text());
                $(filterFields.graphType).val(filterValues.graphType);
            }
        }

        updateAvailableFilterChoices();
        closeSubMenu();
        return false;
    };

    var chooseGroupPrimary = function () {

        filterValues.groupFactor = $(this).prop('checked');
        return false;
    };

    var changeRangeValues = function () {

        filterValues.rangeStart = $(filterFields.rangeStart).val();
        filterValues.rangeEnd = $(filterFields.rangeEnd).val();

        //console.log("Range Changed");

        return false;
    };

    var optionLinkClick = function (evt) {

        // do nothing is tab is within submenu
        if ($(evt.target).parents('ul.submenu').length) {
            return;
        }

        activeSubMenu = $(this);
        if ($(activeSubMenu).hasClass("active")) {
            closeSubMenu();
        }
        else {
            openSubMenu();
        }
        return false;
    };

    var checkFilterValid = function () {

        var errors = [];
        //var filtersAreValid = true;

        // Dataset 1 has valid value
        if (!allowedFilterValues.isPrimaryDataValid(filterValues.dataset1)) {

            //filtersAreValid = false;
            errors.push("Choose valid Primary Dataset");
        }

        // Dataset 2 has valid value - based on dataset1
        if (filterValues.dataset2 != null) {
            if (!allowedFilterValues.isSecondaryDataAllowed(filterValues.dataset1, filterValues.dataset2)) {
                //filtersAreValid = false;
                errors.push("Choose valid Secondary Dataset");
            }
        }

        // Graph Type has valid value
        if (filterValues.dataset2 != null && !allowedFilterValues.isCombinableGraph(filterValues.graphType)) {

            //filtersAreValid = false;
            errors.push("Choose valid Graph Type");
        }
        else if (filterValues.dataset2 == null && !allowedFilterValues.isGraphTypeAllowed(filterValues.dataset1, filterValues.graphType)) {

            //filtersAreValid = false;
            errors.push("Choose valid Graph Type");
        }

        // Start Date has value
        // Start Date is before or equal to today
        if (filterValues.startDate != null) {

            var startDate = new Date(filterValues.startDate);
            if (Object.prototype.toString.call(startDate) === "[object Date]" && !isNaN(startDate.getTime())) {

                var today = new Date();
                if (startDate.getTime() > today.getTime()) {

                    errors.push("Start Date cannot be in the future")
                }
            }
            else {
                errors.push("Invalid Start Date");
            }
        }

        // End Date is after Start Date
        // End Date is 30 days or less from Start Date
        if (filterValues.endDate != null) {

            var endDate = new Date(filterValues.endDate);

            //enforce that endDate is a Date object and Time is a number
            if (Object.prototype.toString.call(endDate) === "[object Date]" && !isNaN(endDate.getTime())) {

                //get todays date
                var today = new Date();

                //get the start date as an object, why isn't this done with end Date?
                var startDate = new Date(filterValues.startDate);

                //end date is in the future
                if (endDate.getTime() > today.getTime()) {

                    errors.push("End Date cannot be in the future");
                }

                //enforce that startDate is a Date object and Time is a number
                else if (Object.prototype.toString.call(startDate) === "[object Date]" && !isNaN(startDate.getTime())) {

                    // if StartDate is more than 1 year before end date
                    //       var startTime = startDate.getTime() + (365 * 24 * 60 * 60 * 1000);
                    //console.log(startTime+" < "+endDate.getTime());

                    if (endDate.getTime() < startDate.getTime()) {

                        errors.push("End Date is before Start Date");
                    }
                }
            }
            else {
                errors.push("Invalid End Date");
            }
        }

        if (filterValues.rangeStart != null) {

            if (isNaN(+filterValues.rangeStart) || !isFinite(filterValues.rangeStart)) {

                errors.push("Range Start is not a number");
            }
        }

        if (filterValues.rangeEnd != null) {

            if (isNaN(+filterValues.rangeEnd) || !isFinite(filterValues.rangeEnd)) {

                errors.push("Range End is not a number");
            }
        }

        if (filterValues.rangeStart != null && filterValues.rangeStart != "" &&
            filterValues.rangeEnd != null && filterValues.rangeEnd != "") {

            if (parseFloat(filterValues.rangeStart) > parseFloat(filterValues.rangeEnd)) {

                errors.push("Range Start is larger than End");
            }
        }

        if (filterFields.groupPrimary && filterFields.groupFactor < 1) {

            errors.push("Group Factor is less than 1");
        }

        // clear errors
        $(filterMenus.errors).html("");
        // show error list on page
        if (errors.length > 0) {

            var errorList = "<ul>";
            for (i = 0; i < errors.length; ++i) {

                errorList += "<li>" + errors[i] + "</li>";
            }
            errorList += "</ul>";
            $(filterMenus.errors).append(errorList);
            return (false);
        }
        else {

            return (true);
        }

    };

    var getGraph = function () {

        closeSubMenu();

        // Validate before submitting
        var filtersAreValid = checkFilterValid();
        if (filtersAreValid) {
            // Get Filter values from form hidden fields
            //var graphType = $(filterValues.graphType).val();
            var postData = $("#graph-options").serialize();
            //console.log(postData);
            graphLoaderModule.loadGraph(filterValues.graphType, postData);
        }

        // stop html form post
        return false;
    };

    var registerTypeahead = function () {

        typeaheadFeature.setGlobalVariableAndInitalize("/search/typeahead/medicationsWithID", filterFields.medicationName, "medication", true, true);
    };

    var publicObject = {};
    publicObject.getPrimaryDataset = function () {
        return filterValues.dataset1;
    };
    publicObject.getSecondaryDataset = function () {
        return filterValues.dataset2;
    };
    publicObject.isPrimaryDataGrouped = function () {
        return filterValues.groupFactor;
    };
    publicObject.getRangeStart = function () {
        return filterValues.rangeStart;
    };
    publicObject.getRangeEnd = function () {
        return filterValues.rangeEnd;
    };
    publicObject.init = function () {

        // Register Actions
        //$(".menu-item").find("a.opt-link").click(optionLinkClick);
        $(".menu-item").click(optionLinkClick);
        $(filterMenus.dataset1).find(".submenu").find("a").click(chooseDataSet1);
        $(filterMenus.dataset2).find(".submenu").find("a").click(chooseDataSet2);
        $(filterMenus.graphType).find(".submenu").find("a").click(chooseGraphType);

        $(filterFields.startDate).change(chooseStartDate);
        $(filterFields.endDate).change(chooseEndDate);
        $(filterFields.groupFactor).change(chooseGroupPrimary);
        $(filterFields.rangeStart).change(changeRangeValues);
        $(filterFields.rangeEnd).change(changeRangeValues);
        $(filterFields.startDate).trigger("change");
        $(filterFields.endDate).trigger("change");

        $("#filter-primary-clear").click(clearFilterPrimary);
        $("#clear-button").click(clearGraphOptions);
        $("#submit-button").click(getGraph);

        $("#save-button").click(showImageOptions);
        $("#export-button").click(exportData);
        $(".save-image-cont").find(".options").find(".image-size-selection").click(chooseImageSize);
        $(".save-image-cont").find(".options").find(".close").click(hideImageOptions)

        // register typeahead on medication names field
        registerTypeahead();

        // stop form submission
        //$(form).attr("onsubmit", "return false;");

        updateAvailableFilterChoices();
    };

    return publicObject;

})();
