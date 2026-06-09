(function ($) {
    function updateTextContent(id, value) {
        var el = document.getElementById(id);
        if (el) {
            el.textContent = value;
        }
    }

    function updateChildTextContent(id, value) {
        var el = document.getElementById(id);
        if (el) {
            var textNode = Array.prototype.find.call(el.childNodes, function (node) {
                return node.nodeType === Node.TEXT_NODE;
            });
            if (textNode) {
                textNode.textContent = value;
            }
        }
    }

    function applyLanguage(languageCode, data) {
        if (!data || !data[languageCode]) {
            return;
        }

        localStorage.setItem('languageCode', languageCode);
        var t = data[languageCode];
        updateChildTextContent('manager_trip_message', t.manager_trip_message);
        updateTextContent('manager_trip_link', t.manager_trip_link);
        updateTextContent('manager_patients_overview', t.manager_patients_overview);
        updateTextContent('manager_date_label', t.manager_date_label);
        updateTextContent('manager_date_submit', t.manager_date_submit);
        updateTextContent('manager_open_who_report', t.manager_open_who_report);
        updateTextContent('manager_th_patient_id', t.manager_th_patient_id);
        updateTextContent('manager_th_name', t.manager_th_name);
        updateTextContent('manager_th_patient_info', t.manager_th_patient_info);
        updateTextContent('manager_th_chief_complaint', t.manager_th_chief_complaint);
        updateTextContent('manager_th_triage_checkin', t.manager_th_triage_checkin);
        updateTextContent('manager_th_medical_checkin', t.manager_th_medical_checkin);
        updateTextContent('manager_th_pharmacy_checkin', t.manager_th_pharmacy_checkin);
        updateTextContent('manager_th_total_time', t.manager_th_total_time);
        updateTextContent('manager_no_active_trip', t.manager_no_active_trip);
        updateTextContent('manager_current_trip_label', t.manager_current_trip_label);
        document.querySelectorAll('.manager-meta-gender').forEach(function(el) {
            if (t.manager_th_gender) el.textContent = t.manager_th_gender;
        });
        document.querySelectorAll('.manager-meta-age').forEach(function(el) {
            if (t.manager_th_age) el.textContent = t.manager_th_age;
        });
        document.querySelectorAll('.manager-meta-city').forEach(function(el) {
            if (t.manager_th_city) el.textContent = t.manager_th_city;
        });
    }

    function buildDtLanguage(t) {
        if (!t) return {};
        var lang = {};
        if (t.manager_dt_empty_table)  lang.emptyTable  = t.manager_dt_empty_table;
        if (t.manager_dt_info)         lang.info         = t.manager_dt_info;
        if (t.manager_dt_info_empty)   lang.infoEmpty    = t.manager_dt_info_empty;
        if (t.manager_dt_length_menu)  lang.lengthMenu   = t.manager_dt_length_menu;
        if (t.manager_dt_search)       lang.search       = t.manager_dt_search;
        if (t.manager_dt_zero_records) lang.zeroRecords  = t.manager_dt_zero_records;
        lang.paginate = {};
        if (t.manager_dt_next)     lang.paginate.next     = t.manager_dt_next;
        if (t.manager_dt_previous) lang.paginate.previous = t.manager_dt_previous;
        return lang;
    }

    function initDataTable(t) {
        var $table = $('#managersTable');
        if (!$table.length || !$.fn.DataTable) {
            return null;
        }

        return $table.DataTable({
            autoWidth: false,
            columnDefs: [
                { orderable: false, targets: [2, 3] }
            ],
            order: [[0, 'asc']],
            language: buildDtLanguage(t)
        });
    }

    function bindLayoutObservers(table) {
        if (!table) {
            return;
        }

        var raf = window.requestAnimationFrame || function (cb) { return setTimeout(cb, 16); };
        var pending = false;

        var scheduleAdjust = function () {
            if (pending) {
                return;
            }
            pending = true;
            raf(function () {
                pending = false;
                table.columns.adjust();
            });
        };

        $(window).on('resize.managerTable', scheduleAdjust);

        var sidebar = document.querySelector('.femr-sidebar');
        if (sidebar) {
            ['mouseenter', 'mouseleave', 'focusin', 'focusout'].forEach(function (eventName) {
                sidebar.addEventListener(eventName, scheduleAdjust);
            });
            sidebar.addEventListener('transitionend', function (event) {
                if (event.propertyName === 'width') {
                    scheduleAdjust();
                }
            });
        }

        scheduleAdjust();
    }

    function hydrateLanguage() {
        var config = window.managerConfig || {};
        var defaultLanguage = config.languageCode || 'en';

        if (!config.languagesUrl) {
            bindLayoutObservers(initDataTable(null));
            return;
        }

        fetch(config.languagesUrl)
            .then(function (response) { return response.json(); })
            .then(function (data) {
                var chosenLanguage = data[defaultLanguage] ? defaultLanguage : 'en';
                applyLanguage(chosenLanguage, data);
                bindLayoutObservers(initDataTable(data[chosenLanguage]));
            })
            .catch(function () {
                bindLayoutObservers(initDataTable(null));
            });
    }

    $(function () {
        hydrateLanguage();
    });
})(jQuery);
