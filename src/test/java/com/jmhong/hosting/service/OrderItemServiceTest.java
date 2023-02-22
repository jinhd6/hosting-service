package com.jmhong.hosting.service;

import com.jmhong.hosting.domain.*;
import com.jmhong.hosting.dto.OrderItemSearchDto;
import com.jmhong.hosting.repository.OrderItemRepository;
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
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class OrderItemServiceTest {

    @PersistenceContext EntityManager em;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private ItemService itemService;
    @Autowired
    private OrderItemRepository orderItemRepository;

    @Test
    void saveOrderItem() {
        // Given
        Long price = 11111L;
        Long period = 111L;
        LocalDateTime activateDate = LocalDateTime.now();
        LocalDateTime expireDate = activateDate.plusDays(period);
        Member member = createMember("id1", "pw1", "id1@email.com", "이름1",
                "010-0000-0001", "주소1", MemberType.MEMBER);
        Item item = createItem("item1", price, period, ItemStatus.SALE);
        Order order = createOrder(member, LocalDateTime.now(), "이름1", "010-0000-0001",
                "주소1", OrderType.NEW, OrderStatus.ORDER, 0L);

        Long orderItemId = orderItemService.saveOrderItem(order, item, "orderItem1",
                activateDate, expireDate, price, period);

        OrderItem orderItem = orderItemRepository.findById(orderItemId).orElseThrow(NoSuchElementException::new);
        assertEquals(order, orderItem.getOrder());
        assertEquals(item, orderItem.getItem());
        assertEquals("orderItem1", orderItem.getName());
        assertEquals(activateDate, orderItem.getActivateDate());
        assertEquals(expireDate, orderItem.getExpireDate());
        assertEquals(11111L, orderItem.getOrderPrice());
        assertEquals(111L, orderItem.getOrderPeriod());
        assertEquals(OrderItemStatus.ACTIVE, orderItem.getStatus());
    }

    @Test
    void search() {
        // Given
        Long price1 = 11111L;
        Long period1 = 111L;
        LocalDateTime activateDate1 = LocalDateTime.now();
        LocalDateTime expireDate1 = activateDate1.plusDays(period1);
        Long price2 = 22222L;
        Long period2 = 222L;
        LocalDateTime activateDate2 = LocalDateTime.now();
        LocalDateTime expireDate2 = activateDate2.plusDays(period2);
        Member member1 = createMember("id1", "pw1", "id1@email.com", "이름1",
                "010-0000-0001", "주소1", MemberType.MEMBER);
        Member member2 = createMember("id2", "pw2", "id2@email.com", "이름2",
                "010-0000-0002", "주소2", MemberType.ADMIN);
        Item item1 = createItem("item1", price1, period1, ItemStatus.SALE);
        Item item2 = createItem("item2", price2, period2, ItemStatus.SUSPEND);
        Order order1 = createOrder(member1, LocalDateTime.now(), "이름1", "010-0000-0001",
                "주소1", OrderType.NEW, OrderStatus.ORDER, 0L);
        Order order2 = createOrder(member2, LocalDateTime.now(), "이름2", "010-0000-0002",
                "주소2", OrderType.NEW, OrderStatus.ORDER, 0L);
        OrderItem orderItem1 = createOrderItem(order1, item1, "orderItem1", activateDate1, expireDate1,
                price1, period1, OrderItemStatus.ACTIVE);
        OrderItem orderItem2 = createOrderItem(order2, item2, "orderItem2", activateDate2, expireDate2,
                price2, period2, OrderItemStatus.EXPIRE);
        OrderItemSearchDto orderItemSearchDto = new OrderItemSearchDto(
                "orderItem", "이름", "item", null);

        // When
        List<OrderItem> findOrderItems = orderItemService.search(orderItemSearchDto);

        // Then
        assertEquals(2, findOrderItems.size());
    }

    private Member createMember(String username, String password, String email, String realName, String phoneNumber,
                                String address, MemberType type) {
        Member member = new Member(username, password, email, realName, phoneNumber, address, type);
        em.persist(member);
        return member;
    }

    private Order createOrder(Member member, LocalDateTime orderDate, String customerName, String customerPhoneNumber,
                              String customerAddress, OrderType type, OrderStatus status, Long extendPeriod) {
        Order order = new Order(member, orderDate, customerName, customerPhoneNumber, customerAddress,
                type, status, extendPeriod);
        em.persist(order);
        return order;
    }

    private Item createItem(String name, Long price, Long period, ItemStatus status) {
        Item item = new Item(name, price, period, status);
        em.persist(item);
        return item;
    }

    private OrderItem createOrderItem(Order order, Item item, String name,
                                      LocalDateTime activateDate, LocalDateTime expireDate,
                                      Long orderPrice, Long orderPeriod, OrderItemStatus status) {
        OrderItem orderItem = new OrderItem(order, item, name,
                activateDate, expireDate, orderPrice, orderPeriod, status);
        em.persist(orderItem);
        return orderItem;
    }
}