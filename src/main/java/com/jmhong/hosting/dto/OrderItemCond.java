package com.jmhong.hosting.dto;

import com.jmhong.hosting.domain.OrderItemStatus;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
public class OrderItemCond {

    private String orderItemName;
    private String orderCustomerName;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime startTime;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime endTime;

    private OrderItemStatus status;

    public OrderItemCond(String orderItemName, String orderCustomerName, LocalDateTime startTime, LocalDateTime endTime, OrderItemStatus status) {
        this.orderItemName = orderItemName;
        this.orderCustomerName = orderCustomerName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
    }

    public OrderItemCond() {
    }
}
