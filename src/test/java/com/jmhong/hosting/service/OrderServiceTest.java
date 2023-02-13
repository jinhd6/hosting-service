package com.jmhong.hosting.service;

import com.jmhong.hosting.domain.*;
import com.jmhong.hosting.dto.OrderSearchDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {

    @Autowired
    private OrderService orderService;
    @Autowired
    private MemberService memberService;

    @Test
    void saveOrder() {
        Order order1 = new Order(null, null,
                "cn1", "cpn1", "ca1",
                OrderType.NEW, OrderStatus.ORDER, null);
        Order order2 = new Order(null, null,
                "cn2", "cpn2", "ca2",
                OrderType.EXTEND, OrderStatus.CANCEL, null);

        Long saveOrderId1 = orderService.saveOrder(order1);
        Long saveOrderId2 = orderService.saveOrder(order2);

        assertEquals(order1.getId(), saveOrderId1);
        assertEquals(order2.getId(), saveOrderId2);
    }

    @Test
    void search() {
        Member member1 = new Member("id1", "pw1", "email1", MemberType.MEMBER);
        Member member2 = new Member("id2", "pw2", "email2", MemberType.MEMBER);
        memberService.join(member1);
        memberService.join(member2);
        Order order1 = new Order(member1, null,
                "cn1", "cpn1", "ca1",
                OrderType.NEW, OrderStatus.ORDER, null);
        Order order2 = new Order(member2, null,
                "cn2", "cpn2", "ca2",
                OrderType.EXTEND, OrderStatus.CANCEL, null);
        orderService.saveOrder(order1);
        orderService.saveOrder(order2);
        OrderSearchDto orderSearchDto1 = new OrderSearchDto(
                "id1", "cn1", "cpn1", "ca1",
                OrderType.NEW, OrderStatus.ORDER);
        OrderSearchDto orderSearchDto2 = new OrderSearchDto(
                "id2", "cn2", "cpn2", "ca2",
                OrderType.EXTEND, OrderStatus.CANCEL);

        List<Order> findOrders1 = orderService.search(orderSearchDto1);
        List<Order> findOrders2 = orderService.search(orderSearchDto2);

        assertEquals(1, findOrders1.size());
        assertEquals(1, findOrders2.size());
        assertEquals(order1.getId(), findOrders1.get(0).getId());
        assertEquals(order2.getId(), findOrders2.get(0).getId());
    }
}
