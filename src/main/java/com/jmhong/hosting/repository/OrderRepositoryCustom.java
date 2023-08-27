package com.jmhong.hosting.repository;

import com.jmhong.hosting.domain.Order;
import com.jmhong.hosting.dto.OrderCond;

import java.util.List;

public interface OrderRepositoryCustom {

    List<Order> search(OrderCond order);
}
