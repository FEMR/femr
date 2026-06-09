
// Called by each trips page's language fetch callback with translated DataTables strings.
// Falls back to empty language object if fetch fails (each page's .catch calls initTripsTables({})).
window.initTripsSelect2 = function(addPlaceholder, removePlaceholder) {
    if ($('#addUsersSelect2').length > 0) {
        $('#addUsersSelect2').select2({ placeholder: addPlaceholder || 'Add users here' });
    }
    if ($('#removeUsersSelect2').length > 0) {
        $('#removeUsersSelect2').select2({ placeholder: removePlaceholder || 'Remove users here' });
    }
};

window.initTripsTables = function(dtLang) {
    if (!$.fn.DataTable) return;
    var lang = dtLang || {};
    ['#tripTable', '#cityTable', '#teamTable'].forEach(function(id) {
        var $t = $(id);
        if ($t.length && !$.fn.DataTable.isDataTable(id)) {
            $t.DataTable({ language: lang });
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

    // Select2 init is handled by window.initTripsSelect2, called from edit.scala.html's fetch callback.

});