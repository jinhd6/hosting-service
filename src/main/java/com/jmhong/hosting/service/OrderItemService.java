package com.jmhong.hosting.service;

import com.jmhong.hosting.domain.*;
import com.jmhong.hosting.dto.OrderItemSearchDto;
import com.jmhong.hosting.repository.MemberRepository;
import com.jmhong.hosting.repository.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderItemService {

    private OrderItemRepository orderItemRepository;
    private MemberRepository memberRepository;

    @Autowired
    public OrderItemService(OrderItemRepository orderItemRepository, MemberRepository memberRepository) {
        this.orderItemRepository = orderItemRepository;
        this.memberRepository = memberRepository;
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
    public List<OrderItem> findActiveOrderItems(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow();
        List<Order> orders = member.getOrders();
        List<OrderItem> orderItems = orders.stream()
                .flatMap(o -> o.getOrderItems().stream())
                .filter(oi -> oi.getStatus() == OrderItemStatus.ACTIVE)
                .collect(Collectors.toList());
        return orderItems;
    }
}
