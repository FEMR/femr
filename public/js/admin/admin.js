// Admin navigation active state handling
document.addEventListener('DOMContentLoaded', function () {
    const currentPath = window.location.pathname || '/';
    const navLinks = document.querySelectorAll('.femr-sidebar__nav-link');

    const normalisePath = function (path) {
        if (!path) {
            return '';
        }
        if (path.length > 1 && path.endsWith('/')) {
            return path.slice(0, -1);
        }
        return path;
    };

    const markActive = function (link) {
        link.classList.add('is-active');
        if (!link.hasAttribute('aria-current')) {
            link.setAttribute('aria-current', 'page');
        }
    };

    const normalizedCurrent = normalisePath(currentPath);

    navLinks.forEach(function (link) {
        const navRoot = normalisePath(link.dataset.navRoot || link.getAttribute('href'));

        if (!navRoot || navRoot.startsWith('http')) {
            return;
        }

        if (navRoot === '/admin') {
            if (normalizedCurrent === '/admin') {
                markActive(link);
            }
            return;
        }

        if (normalizedCurrent === navRoot || normalizedCurrent.startsWith(navRoot + '/')) {
            markActive(link);
        }
    });

    const accordionTriggers = document.querySelectorAll('[data-accordion-trigger]');
    accordionTriggers.forEach(function (trigger) {
        const contentId = trigger.getAttribute('aria-controls');
        const content = contentId ? document.getElementById(contentId) : trigger.nextElementSibling;

        if (!content) {
            return;
        }

        content.hidden = trigger.getAttribute('aria-expanded') !== 'true';

        trigger.addEventListener('click', function () {
            const expanded = trigger.getAttribute('aria-expanded') === 'true';
            trigger.setAttribute('aria-expanded', String(!expanded));
            content.hidden = expanded;
        });
    });

    const languageCheckboxes = document.querySelectorAll('.language-checkbox');
    if (languageCheckboxes.length > 0) {
        languageCheckboxes.forEach(function (checkbox) {
            const code = checkbox.name;
            updateScheduled[code] = checkbox.checked && !checkbox.disabled;

            checkbox.addEventListener('change', function (event) {
                if (checkbox.disabled) {
                    event.preventDefault();
                    return;
                }

                updateSchedule(code, checkbox.checked);
            });
        });

        const initButton = document.querySelector('[data-updates-action="init-languages"]');
        if (initButton) {
            initButton.addEventListener('click', function (event) {
                event.preventDefault();
                initLanguages();
            });
        }

        const downloadButton = document.querySelector('[data-updates-action="download-packages"]');
        if (downloadButton) {
            downloadButton.addEventListener('click', function (event) {
                event.preventDefault();
                downloadPackages();
            });
        }
    }
});

//updates list logic
const updateScheduled = {}

function updateSchedule(code, nextValue){
    const shouldSchedule = typeof nextValue === 'boolean' ? nextValue : !updateScheduled[code];
    updateScheduled[code] = shouldSchedule;
    $.ajax({
        type: 'get',
        url: '/admin/updates/scheduleUpdate',
        data: {code: code, update: shouldSchedule},
        failure: function(){console.log("Error Occurred");}
    }).done(function(){window.location.reload()});
}

function downloadPackages(){
    const updates = Object.keys(updateScheduled).filter(function (lang) {
        return updateScheduled[lang];
    });
    const loader = document.querySelector('[data-updates-loader]');
    if(updates.length > 0){
        if (loader) {
            loader.hidden = false;
        }
        $.ajax({
            type: 'get',
            url: '/admin/updates/downloadPackages',
            data: {code: JSON.stringify(updates)},
            failure: function() {console.log("Error Occurred");}
        }).done(function() {
            console.log("All packages downloaded")
            if (loader) {
                loader.hidden = true;
            }
            window.location.reload();
        });
    }
}

function initLanguages(){
    $.ajax({
        type: 'get',
        url: '/admin/updates/initLanguages',
        failure: function() {console.log("Error Occurred");}
    }).done(function(){window.location.reload()});
}


