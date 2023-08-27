package com.jmhong.hosting.service;

import com.jmhong.hosting.domain.*;
import com.jmhong.hosting.dto.OrderItemCond;
import com.jmhong.hosting.repository.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

@Service
@Transactional
public class OrderItemService {

    private OrderItemRepository orderItemRepository;

    @Autowired
    public OrderItemService(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    public List<OrderItem> saveOrderItems(Order order, Item item) {
        List<OrderItem> orderItems = generateNames(order, item).stream()
                .map(name -> OrderItem.createOrderItem(order, item, name))
                .collect(Collectors.toList());
        return orderItemRepository.saveAll(orderItems);
    }

    public static List<String> generateNames(Order order, Item item) {
        return LongStream.rangeClosed(1, order.getQuantity())
                .mapToObj(suffix -> item.getName() + "_" + order.getId() + "_" + suffix)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<OrderItem> search(OrderItemCond orderItemCond) {
        return orderItemRepository.search(orderItemCond);
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
