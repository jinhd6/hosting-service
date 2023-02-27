"use strict";

const main = function() {
    let fpStart = flatpickr("#connectDate", {
        enableTime: true,
        dateFormat: "Z",
        minDate: "today",
        minTime: new Date(),
        defaultHour: new Date().getHours(),
        defaultMinute: new Date().getMinutes(),
        onChange: function(selectedDates, dateStr, instance) {
            fpEnd.set("minDate", dateStr);
            const now = new Date()
            if (selectedDates[0].toDateString() === now.toDateString()) {
                fpStart.set("minTime", now);
            } else {
                fpStart.set("minTime", "00:00");
            }
        },
        altInput: true,
        altFormat: "Y년 n월 j일 K h:i:S"
    });
    let fpEnd = flatpickr("#disconnectDate", {
        enableTime: true,
        dateFormat: "Z",
        minDate: "today",
        defaultHour: new Date().getHours(),
        defaultMinute: new Date().getMinutes(),
        onChange: function(selectedDates, dateStr, instance) {
            fpStart.set("maxDate", dateStr);
        },
        altInput: true,
        altFormat: "Y년 n월 j일 K h:i:S"
    });
}

main();
