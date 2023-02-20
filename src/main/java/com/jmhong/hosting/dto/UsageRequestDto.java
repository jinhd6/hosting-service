package com.jmhong.hosting.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
public class UsageRequestDto {

    private Long memberId;
    private Long orderItemId;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime connectDate;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime disconnectDate;

    public UsageRequestDto() {
    }

    public UsageRequestDto(Long memberId, Long orderItemId, LocalDateTime connectDate, LocalDateTime disconnectDate) {
        this.memberId = memberId;
        this.orderItemId = orderItemId;
        this.connectDate = connectDate;
        this.disconnectDate = disconnectDate;
    }
}
