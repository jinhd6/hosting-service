package com.jmhong.hosting.service;

import com.jmhong.hosting.domain.*;
import com.jmhong.hosting.dto.OrderRequestDto;
import com.jmhong.hosting.dto.OrderSearchDto;
import com.jmhong.hosting.repository.ItemRepository;
import com.jmhong.hosting.repository.MemberRepository;
import com.jmhong.hosting.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
        Order order = new Order(member, LocalDateTime.now(), member.getRealName(), member.getPhoneNumber(),
                member.getAddress(), OrderType.NEW, OrderStatus.ORDER, 0L);
        orderRepository.save(order);

        for (int orderItemSuffix = 1; orderItemSuffix <= dto.getQuantity(); orderItemSuffix++) {
            String name = item.getName() + "_" + order.getId() + "_" + orderItemSuffix;
            createOrderItem(name, item, order);
        }
        return order;
    }

    private void createOrderItem(String name, Item item, Order order) {
        LocalDateTime activateDate = LocalDateTime.now();
        LocalDateTime expireDate = activateDate.plusDays(item.getPeriod());
        orderItemService.saveOrderItem(order, item, name, activateDate, expireDate, item.getPrice(), item.getPeriod());
    }

    @Transactional(readOnly = true)
    public List<Order> search(OrderSearchDto orderSearchDto) {
        return orderRepository.search(orderSearchDto);
    }

    @Transactional(readOnly = true)
    public List<Order> findOrders() {
        return orderRepository.findAll();
    }
}
