package com.jmhong.hosting.repository;

import com.jmhong.hosting.domain.*;
import com.jmhong.hosting.dto.OrderItemSearchDto;
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
public class OrderItemRepositoryTest {

    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private EntityManager em;

    @Test
    void search() {
        Order order1 = new Order(null, null,
                "cn1", "cpn1", "ca1",
                OrderType.NEW, OrderStatus.ORDER);
        Order order2 = new Order(null, null,
                "cn2", "cpn2", "ca2",
                OrderType.EXTEND, OrderStatus.CANCEL);
        orderRepository.save(order1);
        orderRepository.save(order2);

        Item item1 = new Item("item1", 11111L, 111L, ItemStatus.SALE);
        Item item2 = new Item("item2", 22222L, 222L, ItemStatus.SUSPEND);
        itemRepository.save(item1);
        itemRepository.save(item2);

        OrderItem orderItem1 = new OrderItem(order1, item1, "oi1", null, null, 111111L, 1111L, OrderItemStatus.ACTIVE);
        OrderItem orderItem2 = new OrderItem(order2, item2, "oi2", null, null, 222222L, 2222L, OrderItemStatus.EXPIRE);
        orderItemRepository.save(orderItem1);
        orderItemRepository.save(orderItem2);

        em.flush();
        em.clear();

        OrderItemSearchDto orderItemSearchDto1 = new OrderItemSearchDto("", "", "", null);
        OrderItemSearchDto orderItemSearchDto2 = new OrderItemSearchDto("oi1", "cn1", "item1", OrderItemStatus.ACTIVE);
        OrderItemSearchDto orderItemSearchDto3 = new OrderItemSearchDto("oi2", "cn2", "item2", OrderItemStatus.EXPIRE);
        OrderItemSearchDto orderItemSearchDto4 = new OrderItemSearchDto("i", "n", "tem", null);
        OrderItemSearchDto orderItemSearchDto5 = new OrderItemSearchDto("xx", "xx", "xx", null);

        List<OrderItem> findOrderItem1 = orderItemRepository.search(orderItemSearchDto1);
        List<OrderItem> findOrderItem2 = orderItemRepository.search(orderItemSearchDto2);
        List<OrderItem> findOrderItem3 = orderItemRepository.search(orderItemSearchDto3);
        List<OrderItem> findOrderItem4 = orderItemRepository.search(orderItemSearchDto4);
        List<OrderItem> findOrderItem5 = orderItemRepository.search(orderItemSearchDto5);

        assertEquals(2, findOrderItem1.size());
        assertEquals(1, findOrderItem2.size());
        assertEquals(1, findOrderItem3.size());
        assertEquals(2, findOrderItem4.size());
        assertEquals(0, findOrderItem5.size());

        assertEquals(orderItem1.getId(), findOrderItem2.get(0).getId());
        assertEquals(orderItem2.getId(), findOrderItem3.get(0).getId());
        assertEquals(order1.getId(), findOrderItem2.get(0).getOrder().getId());
        assertEquals(order2.getId(), findOrderItem3.get(0).getOrder().getId());
        assertEquals(item1.getId(), findOrderItem2.get(0).getItem().getId());
        assertEquals(item2.getId(), findOrderItem3.get(0).getItem().getId());
    }
}