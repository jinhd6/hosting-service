"use strict";

const main = function() {
    const now = new Date();
    let fpStart = flatpickr("#connectDate", {
        enableTime: true,
        dateFormat: "Z",
        minDate: activateTime,
        defaultHour: now.getHours(),
        defaultMinute: now.getMinutes(),
        onChange: function(selectedDates, dateStr, instance) {
            fpEnd.set("minDate", dateStr);
            if (selectedDates[0].toDateString() === activateTime.toDateString()) {
                fpStart.set("minTime", activateTime);
            } else {
                fpStart.set("minTime", "00:00");
            }
        },
        altInput: true,
        altFormat: "Y년 n월 j일 K h:i:S",
        maxDate: expireTime
    });
    let fpEnd = flatpickr("#disconnectDate", {
        enableTime: true,
        dateFormat: "Z",
        minDate: activateTime,
        defaultHour: now.getHours(),
        defaultMinute: now.getMinutes(),
        onChange: function(selectedDates, dateStr, instance) {
            fpStart.set("maxDate", dateStr);
            if (selectedDates[0].toDateString === expireTime.toDateString()) {
                fpEnd.set("maxTime", expireTime);
            } else {
                fpEnd.set("maxTime", "");
            }
        },
        altInput: true,
        altFormat: "Y년 n월 j일 K h:i:S",
        maxDate: expireTime
    });
}

main();
