package com.jmhong.hosting.service;

import com.jmhong.hosting.domain.*;
import com.jmhong.hosting.dto.OrderItemSearchDto;
import com.jmhong.hosting.repository.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class OrderItemService {

    private OrderItemRepository orderItemRepository;

    @Autowired
    public OrderItemService(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    public Long saveOrderItem(Order order, Item item, String name,
                              LocalDateTime activateDate, LocalDateTime expireDate, Long price, Long period) {
        OrderItem orderItem = new OrderItem(order, item, name,
                activateDate, expireDate, price, period, OrderItemStatus.ACTIVE);
        orderItemRepository.save(orderItem);
        return orderItem.getId();
    }

    @Transactional(readOnly = true)
    public List<OrderItem> search(OrderItemSearchDto orderItemSearchDto) {
        return orderItemRepository.search(orderItemSearchDto);
    }

    @Transactional(readOnly = true)
    public List<OrderItem> findOrderItems() {
        return orderItemRepository.findAll();
    }

    @Transactional(readOnly = true)
    public OrderItem findById(Long orderItemId) {
        return orderItemRepository.findById(orderItemId).orElseThrow();
    }
}
