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
        updateChildTextContent('manager_trip_message', data[languageCode].manager_trip_message);
        updateTextContent('manager_trip_link', data[languageCode].manager_trip_link);
        updateTextContent('manager_patients_overview', data[languageCode].manager_patients_overview);
        updateTextContent('manager_date_label', data[languageCode].manager_date_label);
        updateTextContent('manager_date_submit', data[languageCode].manager_date_submit);
        updateTextContent('manager_open_who_report', data[languageCode].manager_open_who_report);
        updateTextContent('manager_th_patient_id', data[languageCode].manager_th_patient_id);
        updateTextContent('manager_th_name', data[languageCode].manager_th_name);
        updateTextContent('manager_th_patient_info', data[languageCode].manager_th_patient_info);
        updateTextContent('manager_th_chief_complaint', data[languageCode].manager_th_chief_complaint);
        updateTextContent('manager_th_triage_checkin', data[languageCode].manager_th_triage_checkin);
        updateTextContent('manager_th_medical_checkin', data[languageCode].manager_th_medical_checkin);
        updateTextContent('manager_th_pharmacy_checkin', data[languageCode].manager_th_pharmacy_checkin);
        updateTextContent('manager_th_total_time', data[languageCode].manager_th_total_time);
        updateTextContent('manager_no_active_trip', data[languageCode].manager_no_active_trip);
        document.querySelectorAll('.manager-meta-gender').forEach(function(el) {
            if (data[languageCode].manager_th_gender) el.textContent = data[languageCode].manager_th_gender;
        });
        document.querySelectorAll('.manager-meta-age').forEach(function(el) {
            if (data[languageCode].manager_th_age) el.textContent = data[languageCode].manager_th_age;
        });
        document.querySelectorAll('.manager-meta-city').forEach(function(el) {
            if (data[languageCode].manager_th_city) el.textContent = data[languageCode].manager_th_city;
        });
    }

    function hydrateLanguage() {
        var config = window.managerConfig || {};
        var defaultLanguage = config.languageCode || 'en';

        if (!config.languagesUrl) {
            applyLanguage(defaultLanguage, {});
            return;
        }

        fetch(config.languagesUrl)
            .then(function (response) { return response.json(); })
            .then(function (data) {
                var chosenLanguage = data[defaultLanguage] ? defaultLanguage : 'en';
                applyLanguage(chosenLanguage, data);
            })
            .catch(function () {
                applyLanguage(defaultLanguage, {});
            });
    }

    function initDataTable() {
        var $table = $('#managersTable');
        if (!$table.length || !$table.DataTable) {
            return null;
        }

        return $table.DataTable({
            autoWidth: false,
            columnDefs: [
                { orderable: false, targets: [2, 3] }
            ],
            order: [[0, 'asc']]
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

    $(function () {
        hydrateLanguage();
        var dataTable = initDataTable();
        bindLayoutObservers(dataTable);
    });
})(jQuery);
