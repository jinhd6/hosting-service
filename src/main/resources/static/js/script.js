const getTimezoneSign = function(timezoneOffset) {
    if (timezoneOffset === 0) {
        return "";
    } else if (timezoneOffset > 0) {
        return "\u2212";
    } else if (timezoneOffset < 0) {
        return "+";
    }
}

const getTimezoneDesignator = function(timezoneOffset) {
    if (timezoneOffset === 0) {
        return "Z";
    } else {
        const timezoneSign = getTimezoneSign(timezoneOffset);
        const timezoneHours = Math.abs(timezoneOffset / 60).toString().padStart(2, "0");
        const timezoneMinutes = Math.abs(timezoneOffset % 60).toString().padStart(2, "0");
        return timezoneSign + timezoneHours + ":" + timezoneMinutes;
    }
}

const fitStartTime = function(startTime) {
    if (startTime !== null) {
        startTime = new Date(Math.ceil(startTime / 1000) * 1000);
    }
    return startTime;
}

const fitEndTime = function(endTime) {
    if (endTime !== null) {
        endTime = new Date(Math.floor(endTime / 1000) * 1000);
    }
    return endTime;
}

const makeDualTimePicker = function(startSelector, endSelector, startTime, endTime) {
    startTime = fitStartTime(startTime);
    endTime = fitEndTime(endTime);
    const now = new Date();
    const timezoneOffset = new Date().getTimezoneOffset();
    const isoDatetimeFormat = "Y-m-dTH:i:S.000" + getTimezoneDesignator(timezoneOffset);
    let fpStart = flatpickr(startSelector, {
        enableTime: true,
        dateFormat: isoDatetimeFormat,
        minDate: startTime,
        defaultHour: now.getHours(),
        defaultMinute: now.getMinutes(),
        onChange: function(selectedDates, dateStr, instance) {
            fpEnd.set("minDate", dateStr);
            if (selectedDates[0].toDateString() === startTime.toDateString()) {
                fpStart.set("minTime", startTime);
            } else {
                fpStart.set("minTime", "00:00");
            }
        },
        altInput: true,
        altFormat: "Y년 n월 j일 K h:i:S",
        maxDate: endTime
    });
    let fpEnd = flatpickr(endSelector, {
        enableTime: true,
        dateFormat: isoDatetimeFormat,
        minDate: startTime,
        defaultHour: now.getHours(),
        defaultMinute: now.getMinutes(),
        onChange: function(selectedDates, dateStr, instance) {
            fpStart.set("maxDate", dateStr);
            if (selectedDates[0].toDateString === endTime.toDateString()) {
                fpEnd.set("maxTime", endTime);
            } else {
                fpEnd.set("maxTime", "23:59");
            }
        },
        altInput: true,
        altFormat: "Y년 n월 j일 K h:i:S",
        maxDate: endTime
    });
}
