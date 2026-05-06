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
