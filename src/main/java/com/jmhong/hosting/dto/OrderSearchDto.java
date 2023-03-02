package com.jmhong.hosting.dto;

import com.jmhong.hosting.domain.OrderStatus;
import com.jmhong.hosting.domain.OrderType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
public class OrderSearchDto {

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime startTime;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime endTime;

    private String memberUsername;
    private String customerName;
    private String customerPhoneNumber;
    private String customerAddress;
    private OrderType type;
    private OrderStatus status;

    public OrderSearchDto(LocalDateTime startTime, LocalDateTime endTime, String memberUsername,
                          String customerName, String customerPhoneNumber, String customerAddress,
                          OrderType type, OrderStatus status) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.memberUsername = memberUsername;
        this.customerName = customerName;
        this.customerPhoneNumber = customerPhoneNumber;
        this.customerAddress = customerAddress;
        this.type = type;
        this.status = status;
    }

    public OrderSearchDto() {
    }
}
