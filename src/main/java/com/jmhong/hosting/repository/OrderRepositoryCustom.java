package com.jmhong.hosting.repository;

import com.jmhong.hosting.domain.Order;
import com.jmhong.hosting.dto.OrderSearchDto;

import java.util.List;

public interface OrderRepositoryCustom {

    List<Order> search(OrderSearchDto order);
}
