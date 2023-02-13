package com.jmhong.hosting.dto;

import com.jmhong.hosting.domain.OrderStatus;
import com.jmhong.hosting.domain.OrderType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderSearchDto {

    private String memberUsername;
    private String customerName;
    private String customerPhoneNumber;
    private String customerAddress;
    private OrderType type;
    private OrderStatus status;

    public OrderSearchDto(String memberUsername, String customerName, String customerPhoneNumber, String customerAddress, OrderType type, OrderStatus status) {
        this.memberUsername = memberUsername;
        this.customerName = customerName;
        this.customerPhoneNumber = customerPhoneNumber;
        this.customerAddress = customerAddress;
        this.type = type;
        this.status = status;
    }
}
