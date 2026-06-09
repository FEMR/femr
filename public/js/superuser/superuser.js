function getAdminLanguageData() {
    var url = window.femrLanguageUrl || "/assets/json/languages.json";
    var userLanguage = window.femrLanguageCode;
    var storedLanguage = localStorage.getItem("languageCode");

    return $.getJSON(url).then(function(data) {
        var language = data[userLanguage] ? userLanguage : (data[storedLanguage] ? storedLanguage : "en");
        localStorage.setItem("languageCode", language);
        return $.extend({}, data.en || {}, data[language] || {});
    }, function() {
        return {};
    });
}

function dataTableLanguage(strings) {
    return {
        sLengthMenu: (strings.datatable_show || "Show") + " _MENU_ " + (strings.datatable_entries || "entries"),
        sSearch: (strings.datatable_search || "Search") + ":",
        sInfo: strings.datatable_info || "Showing _START_ to _END_ of _TOTAL_ entries",
        sInfoEmpty: strings.datatable_info_empty || "Showing 0 to 0 of 0 entries",
        sInfoFiltered: strings.datatable_info_filtered || "(filtered from _MAX_ total entries)",
        sEmptyTable: strings.datatable_empty_table || "No data available in table",
        sZeroRecords: strings.datatable_zero_records || "No matching records found",
        oPaginate: {
            sPrevious: strings.datatable_previous || "Previous",
            sNext: strings.datatable_next || "Next"
        }
    };
}

window.initTripsSelect2 = function(addPlaceholder, removePlaceholder) {
    var $addUsers = $('#addUsersSelect2');
    var $removeUsers = $('#removeUsersSelect2');

    if ($addUsers.length > 0 && !$addUsers.data('select2')) {
        $addUsers.select2({ placeholder: addPlaceholder || 'Add users here' });
    }
    if ($removeUsers.length > 0 && !$removeUsers.data('select2')) {
        $removeUsers.select2({ placeholder: removePlaceholder || 'Remove users here' });
    }
};

window.initTripsTables = function(dtLang) {
    if (!$.fn.DataTable) {
        return;
    }

    ['#tripTable', '#cityTable', '#teamTable'].forEach(function(tableId) {
        var $table = $(tableId);
        if ($table.length > 0 && !$.fn.DataTable.isDataTable(tableId)) {
            $table.DataTable({ language: dtLang || {} });
        }
    });
};

$(document).ready(function(){
    $("[name='newTripCity']").change(function(){

        $("[name='newTripCountry']").val($(this).find(':selected').attr('country-name'));
    });

    var today = new Date().toISOString().split('T')[0];
    $('#newTripStartDate').attr('min', today);

    $('#newTripStartDate').change(function() {
        var startDate = $(this).val();
        $('#newTripEndDate').attr('min', startDate);
        if ($('#newTripEndDate').val() && $('#newTripEndDate').val() < startDate) {
            $('#newTripEndDate').val('');
        }
    });

    getAdminLanguageData().then(function(strings) {
        window.initTripsTables(dataTableLanguage(strings));
        window.initTripsSelect2(
            strings.trips_select2_add_users || "Add users here",
            strings.trips_select2_remove_users || "Remove users here"
        );
    });

});
