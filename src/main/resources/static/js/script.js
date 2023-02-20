"use strict";

const main = function() {
    let fpStart = flatpickr("#connectDate", {
        enableTime: true,
        dateFormat: "Z",
        maxDate: "today",
        onChange: function(selectedDates, dateStr, instance) {
            fpEnd.set("minDate", dateStr);
        }
    });
    let fpEnd = flatpickr("#disconnectDate", {
        enableTime: true,
        dateFormat: "Z",
        maxDate: "today",
        onChange: function(selectedDates, dateStr, instance) {
            fpStart.set("maxDate", dateStr);
        }
    });
}

main();
