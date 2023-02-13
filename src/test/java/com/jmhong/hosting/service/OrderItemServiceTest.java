package com.jmhong.hosting.service;

import com.jmhong.hosting.domain.*;
import com.jmhong.hosting.dto.OrderItemSearchDto;
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
class OrderItemServiceTest {

    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private ItemService itemService;

    @Test
    void saveOrderItem() {
        OrderItem orderItem1 = new OrderItem(null, null,
                "orderItem1", null, null, 111111L, 1111L);
        OrderItem orderItem2 = new OrderItem(null, null,
                "orderItem2", null, null, 222222L, 2222L);

        Long saveOrderItemId1 = orderItemService.saveOrderItem(orderItem1);
        Long saveOrderItemId2 = orderItemService.saveOrderItem(orderItem2);

        assertEquals(orderItem1.getId(), saveOrderItemId1);
        assertEquals(orderItem2.getId(), saveOrderItemId2);
    }

    @Test
    void search() {
        Order order1 = new Order(null, null,
                "cn1", "cpn1", "ca1",
                OrderType.NEW, OrderStatus.ORDER, null);
        Order order2 = new Order(null, null,
                "cn2", "cpn2", "ca2",
                OrderType.EXTEND, OrderStatus.CANCEL, null);
        orderService.saveOrder(order1);
        orderService.saveOrder(order2);
        Item item1 = new Item("item1", 11111L, 111L, ItemStatus.SALE);
        Item item2 = new Item("item2", 22222L, 222L, ItemStatus.SUSPEND);
        itemService.saveItem(item1);
        itemService.saveItem(item2);
        OrderItem orderItem1 = new OrderItem(order1, item1,
                "orderItem1", null, null, 111111L, 1111L);
        OrderItem orderItem2 = new OrderItem(order2, item2,
                "orderItem2", null, null, 222222L, 2222L);
        orderItemService.saveOrderItem(orderItem1);
        orderItemService.saveOrderItem(orderItem2);
        OrderItemSearchDto orderItemSearchDto1 = new OrderItemSearchDto(
                "orderItem1", "cn1", "item1");
        OrderItemSearchDto orderItemSearchDto2 = new OrderItemSearchDto(
                "orderItem2", "cn2", "item2");

        List<OrderItem> findOrderItems1 = orderItemService.search(orderItemSearchDto1);
        List<OrderItem> findOrderItems2 = orderItemService.search(orderItemSearchDto2);

        assertEquals(1, findOrderItems1.size());
        assertEquals(1, findOrderItems2.size());
        assertEquals(orderItem1.getId(), findOrderItems1.get(0).getId());
        assertEquals(orderItem2.getId(), findOrderItems2.get(0).getId());
    }
}