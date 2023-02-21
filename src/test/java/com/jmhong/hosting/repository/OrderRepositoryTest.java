package com.jmhong.hosting.repository;

import com.jmhong.hosting.domain.*;
import com.jmhong.hosting.dto.OrderSearchDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
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

        Order order1 = new Order(member1, null, realName1, phoneNumber1, address1,
                OrderType.NEW, OrderStatus.ORDER, null);
        Order order2 = new Order(member2, null, realName2, phoneNumber2, address2,
                OrderType.EXTEND, OrderStatus.CANCEL, null);
        orderRepository.save(order1);
        orderRepository.save(order2);

        em.flush();
        em.clear();

        OrderSearchDto orderSearchDto1 = new OrderSearchDto(
                "", "", "", "",
                null, null);
        OrderSearchDto orderSearchDto2 = new OrderSearchDto(
                "id1", "cn1", "cpn1", "ca1",
                OrderType.NEW, OrderStatus.ORDER);
        OrderSearchDto orderSearchDto3 = new OrderSearchDto(
                "id2", "cn2", "cpn2", "ca2",
                OrderType.EXTEND, OrderStatus.CANCEL);
        OrderSearchDto orderSearchDto4 = new OrderSearchDto(
                "d", "n", "p", "a",
                null, null);
        OrderSearchDto orderSearchDto5 = new OrderSearchDto(
                "xx", "xx", "xx", "xx",
                null, null);

        List<Order> findOrders1 = orderRepository.search(orderSearchDto1);
        List<Order> findOrders2 = orderRepository.search(orderSearchDto2);
        List<Order> findOrders3 = orderRepository.search(orderSearchDto3);
        List<Order> findOrders4 = orderRepository.search(orderSearchDto4);
        List<Order> findOrders5 = orderRepository.search(orderSearchDto5);

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