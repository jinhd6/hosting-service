package com.jmhong.hosting.repository;

import com.jmhong.hosting.domain.*;
import com.jmhong.hosting.dto.OrderCond;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class OrderRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private EntityManager em;

    @Test
    void search() {
        String realName1 = "cn1";
        String phoneNumber1 = "cpn1";
        String address1 = "ca1";
        String realName2 = "cn2";
        String phoneNumber2 = "cpn2";
        String address2 = "ca2";
        Member member1 = new Member("id1", "pw1", "a1@a.com",
                realName1, phoneNumber1, address1, MemberType.MEMBER);
        Member member2 = new Member("id2", "pw2", "a2@a.com",
                realName2, phoneNumber2, address2, MemberType.ADMIN);
        memberRepository.save(member1);
        memberRepository.save(member2);

        Order order1 = new Order(member1,
                LocalDateTime.of(2023, 3, 2, 14, 58),
                realName1, phoneNumber1, address1, OrderType.NEW, OrderStatus.ORDER, 1L);
        Order order2 = new Order(member2,
                LocalDateTime.of(2023, 3, 3, 14, 58),
                realName2, phoneNumber2, address2, OrderType.EXTEND, OrderStatus.CANCEL, 1L);
        orderRepository.save(order1);
        orderRepository.save(order2);

        em.flush();
        em.clear();

        OrderCond orderCond1 = new OrderCond(
                null, null,
                "", "", "", "",
                null, null);
        OrderCond orderCond2 = new OrderCond(
                LocalDateTime.of(2023, 3, 2, 14, 58),
                LocalDateTime.of(2023, 3, 2, 14, 58),
                "id1", "cn1", "cpn1", "ca1",
                OrderType.NEW, OrderStatus.ORDER);
        OrderCond orderCond3 = new OrderCond(
                LocalDateTime.of(2023, 3, 3, 14, 58),
                LocalDateTime.of(2023, 3, 3, 14, 58),
                "id2", "cn2", "cpn2", "ca2",
                OrderType.EXTEND, OrderStatus.CANCEL);
        OrderCond orderCond4 = new OrderCond(
                LocalDateTime.of(2023, 3, 2, 14, 58),
                LocalDateTime.of(2023, 3, 3, 14, 58),
                "d", "n", "p", "a",
                null, null);
        OrderCond orderCond5 = new OrderCond(
                LocalDateTime.of(2023, 3, 1, 14, 58),
                LocalDateTime.of(2023, 3, 2, 14, 57),
                "xx", "xx", "xx", "xx",
                null, null);

        List<Order> findOrders1 = orderRepository.search(orderCond1);
        List<Order> findOrders2 = orderRepository.search(orderCond2);
        List<Order> findOrders3 = orderRepository.search(orderCond3);
        List<Order> findOrders4 = orderRepository.search(orderCond4);
        List<Order> findOrders5 = orderRepository.search(orderCond5);

        assertEquals(2, findOrders1.size());
        assertEquals(1, findOrders2.size());
        assertEquals(1, findOrders3.size());
        assertEquals(2, findOrders4.size());
        assertEquals(0, findOrders5.size());

        assertEquals(order1.getId(), findOrders2.get(0).getId());
        assertEquals(order2.getId(), findOrders3.get(0).getId());
        assertEquals(member1.getId(), findOrders2.get(0).getMember().getId());
        assertEquals(member2.getId(), findOrders3.get(0).getMember().getId());
    }
}