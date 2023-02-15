package com.jmhong.hosting.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UsageRequestDto {

    private Long memberId;
    private Long orderItemId;
    private LocalDateTime connectDate;
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
