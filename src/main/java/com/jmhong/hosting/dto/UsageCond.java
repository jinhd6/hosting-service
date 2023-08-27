package com.jmhong.hosting.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
public class UsageCond {

    private String memberUsername;
    private String orderItemName;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime startTime;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime endTime;

    public UsageCond(String memberUsername, String orderItemName, LocalDateTime startTime, LocalDateTime endTime) {
        this.memberUsername = memberUsername;
        this.orderItemName = orderItemName;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public UsageCond() {
    }
}
