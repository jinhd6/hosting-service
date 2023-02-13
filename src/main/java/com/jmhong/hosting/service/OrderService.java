package com.jmhong.hosting.service;

import com.jmhong.hosting.domain.Order;
import com.jmhong.hosting.dto.OrderSearchDto;
import com.jmhong.hosting.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Transactional
    public Long saveOrder(Order order) {
        orderRepository.save(order);
        return order.getId();
    }

    public List<Order> search(OrderSearchDto orderSearchDto) {
        return orderRepository.search(orderSearchDto);
    }
}
