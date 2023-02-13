package com.jmhong.hosting.service;

import com.jmhong.hosting.domain.OrderItem;
import com.jmhong.hosting.dto.OrderItemSearchDto;
import com.jmhong.hosting.repository.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;

    @Autowired
    public OrderItemService(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    @Transactional
    public Long saveOrderItem(OrderItem orderItem) {
        orderItemRepository.save(orderItem);
        return orderItem.getId();
    }

    public List<OrderItem> search(OrderItemSearchDto orderItemSearchDto) {
        return orderItemRepository.search(orderItemSearchDto);
    }
}
