package com.jmhong.hosting.repository;

import com.jmhong.hosting.domain.OrderItem;
import com.jmhong.hosting.dto.OrderItemCond;

import java.util.List;

public interface OrderItemRepositoryCustom {

    List<OrderItem> search(OrderItemCond orderItemCond);
}
