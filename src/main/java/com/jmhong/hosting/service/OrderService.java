package com.jmhong.hosting.service;

import com.jmhong.hosting.domain.*;
import com.jmhong.hosting.dto.OrderCond;
import com.jmhong.hosting.dto.OrderRequestDto;
import com.jmhong.hosting.repository.ItemRepository;
import com.jmhong.hosting.repository.MemberRepository;
import com.jmhong.hosting.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OrderService {

    private OrderRepository orderRepository;
    private MemberRepository memberRepository;
    private ItemRepository itemRepository;
    private OrderItemService orderItemService;

    @Autowired
    public OrderService(OrderRepository orderRepository, MemberRepository memberRepository,
                        ItemRepository itemRepository, OrderItemService orderItemService) {
        this.orderRepository = orderRepository;
        this.memberRepository = memberRepository;
        this.itemRepository = itemRepository;
        this.orderItemService = orderItemService;
    }

    public Order saveOrder(OrderRequestDto dto) {
        Member member = memberRepository.findById(dto.getMemberId()).orElseThrow();
        Item item = itemRepository.findById(dto.getItemId()).orElseThrow();
        Order order = Order.createOrder(member, item, dto.getQuantity());
        orderRepository.save(order);
        orderItemService.saveOrderItems(order, item);
        return order;
    }

    @Transactional(readOnly = true)
    public List<Order> search(OrderCond orderCond) {
        return orderRepository.search(orderCond);
    }

    @Transactional(readOnly = true)
    public List<Order> findOrders() {
        return orderRepository.findAll();
    }

    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow();
        order.cancelOrder();
    }
}
