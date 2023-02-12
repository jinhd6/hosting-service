package com.jmhong.hosting.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemSearchDto {

    private String orderItemName;
    private String orderCustomerName;
    private String itemName;

    private OrderItemSearchDto() {
    }

    public OrderItemSearchDto(String orderItemName, String orderCustomerName, String itemName) {
        this.orderItemName = orderItemName;
        this.orderCustomerName = orderCustomerName;
        this.itemName = itemName;
    }
}
