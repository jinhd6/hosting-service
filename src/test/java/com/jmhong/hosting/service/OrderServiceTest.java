package com.jmhong.hosting.service;

import com.jmhong.hosting.domain.*;
import com.jmhong.hosting.dto.OrderRequestDto;
import com.jmhong.hosting.dto.OrderSearchDto;
import com.jmhong.hosting.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {

    @PersistenceContext EntityManager em;
    @Autowired private OrderService orderService;
    @Autowired private OrderRepository orderRepository;

    @Test
    void saveOrder() {
        // Given
        Member member = createMember("id1", "pw1", "id1@email.com",
                "이름1", "010-0000-0001", "주소1", MemberType.MEMBER);
        Item item = createItem("item1", 11111L, 111L, ItemStatus.SALE);
        OrderRequestDto orderRequestDto = new OrderRequestDto(member.getId(), item.getId(), 1L);

        // When
        Order order = orderService.saveOrder(orderRequestDto);

        // Then
        assertEquals(member, order.getMember());
        assertEquals("이름1", order.getCustomerName());
        assertEquals("010-0000-0001", order.getCustomerPhoneNumber());
        assertEquals("주소1", order.getCustomerAddress());
        assertEquals(OrderType.NEW, order.getType());
        assertEquals(OrderStatus.ORDER, order.getStatus());
        assertEquals(0L, order.getExtendPeriod());
    }

    @Test
    void search() {
        // Given
        Member member1 = createMember("id1", "pw1", "id1@email.com",
                "이름1", "010-0000-0001", "주소1", MemberType.MEMBER);
        Member member2 = createMember("id2", "pw2", "id2@email.com",
                "이름2", "010-0000-0002", "주소2", MemberType.ADMIN);
        Order order1 = createOrder(member1, LocalDateTime.now(),
                "이름1", "010-0000-0001", "주소1",
                OrderType.NEW, OrderStatus.ORDER, 0L);
        Order order2 = createOrder(member2, LocalDateTime.now(),
                "이름2", "010-0000-0002", "주소2",
                OrderType.EXTEND, OrderStatus.CANCEL, 0L);
        OrderSearchDto orderSearchDto = new OrderSearchDto("id", "이름",
                "010-0000", "주소", null, null);

        // When
        List<Order> findOrders = orderService.search(orderSearchDto);

        // Then
        assertEquals(2, findOrders.size());
    }

    private Member createMember(String username, String password, String email, String realName, String phoneNumber,
                                String address, MemberType type) {
        Member member = new Member(username, password, email, type);
        member.updateDeliveryInfo(realName, phoneNumber, address);
        em.persist(member);
        return member;
    }

    private Item createItem(String name, Long price, Long period, ItemStatus status) {
        Item item = new Item(name, price, period, status);
        em.persist(item);
        return item;
    }

    private Order createOrder(Member member, LocalDateTime orderDate, String customerName, String customerPhoneNumber,
                              String customerAddress, OrderType type, OrderStatus status, Long extendPeriod) {
        Order order = new Order(member, orderDate, customerName, customerPhoneNumber, customerAddress,
                type, status, extendPeriod);
        em.persist(order);
        return order;
    }
}
