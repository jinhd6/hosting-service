package com.jmhong.hosting.dto;

import com.jmhong.hosting.domain.OrderItemStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemSearchDto {

    private String orderItemName;
    private String orderCustomerName;
    private String itemName;
    private OrderItemStatus status;

    public OrderItemSearchDto(String orderItemName, String orderCustomerName,
                              String itemName, OrderItemStatus status) {
        this.orderItemName = orderItemName;
        this.orderCustomerName = orderCustomerName;
        this.itemName = itemName;
        this.status = status;
    }

    public OrderItemSearchDto() {
    }
}
