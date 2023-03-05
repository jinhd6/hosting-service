package com.jmhong.hosting.repository;

import com.jmhong.hosting.domain.*;
import com.jmhong.hosting.dto.UsageSearchDto;
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
class UsageRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private UsageRepository usageRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private EntityManager em;

    @Test
    void search() {
        String realName1 = "rn1";
        String realName2 = "rn2";
        String phoneNumber1 = "pn1";
        String phoneNumber2 = "pn2";
        String address1 = "adr1";
        String address2 = "adr2";
        Member member1 = new Member("id1", "pw1", "a1@a.com",
                realName1, phoneNumber1, address1, MemberType.MEMBER);
        Member member2 = new Member("id2", "pw2", "a2@a.com",
                realName2, phoneNumber2, address2, MemberType.ADMIN);
        memberRepository.save(member1);
        memberRepository.save(member2);

        Order order1 = new Order(member1, LocalDateTime.now(),
                realName1, phoneNumber1, address1,OrderType.NEW, OrderStatus.ORDER, 1L);
        Order order2 = new Order(member2, LocalDateTime.now(),
                realName2, phoneNumber2, address2, OrderType.NEW, OrderStatus.ORDER, 1L);
        orderRepository.save(order1);
        orderRepository.save(order2);

        OrderItem orderItem1 = new OrderItem(order1, null, "oi1",
                null, null, 111111L, 1111L, OrderItemStatus.ACTIVE);
        OrderItem orderItem2 = new OrderItem(order2, null, "oi2",
                null, null, 222222L, 2222L, OrderItemStatus.EXPIRE);
        orderItemRepository.save(orderItem1);
        orderItemRepository.save(orderItem2);

        Usage usage1 = new Usage(
                orderItem1,
                LocalDateTime.of(2023, 3, 2, 11, 41),
                LocalDateTime.of(2023, 3, 2, 23, 41));
        Usage usage2 = new Usage(
                orderItem2,
                LocalDateTime.of(2023, 3, 3, 11, 41),
                LocalDateTime.of(2023, 3, 3, 23, 41));
        usageRepository.save(usage1);
        usageRepository.save(usage2);

        em.flush();
        em.clear();

        UsageSearchDto usageSearchDto1 = new UsageSearchDto(
                "", "", null, null);
        UsageSearchDto usageSearchDto2 = new UsageSearchDto(
                "id1", "oi1",
                LocalDateTime.of(2023, 3, 2, 11, 41),
                LocalDateTime.of(2023, 3, 2, 23, 41));
        UsageSearchDto usageSearchDto3 = new UsageSearchDto(
                "id2", "oi2",
                LocalDateTime.of(2023, 3, 3, 11, 41),
                LocalDateTime.of(2023, 3, 3, 23, 41));
        UsageSearchDto usageSearchDto4 = new UsageSearchDto(
                "d", "i",
                LocalDateTime.of(2023, 3, 2, 11, 41),
                LocalDateTime.of(2023, 3, 3, 23, 41));
        UsageSearchDto usageSearchDto5 = new UsageSearchDto(
                "xx", "xx",
                LocalDateTime.of(2023, 3, 1, 11, 41),
                LocalDateTime.of(2023, 3, 2, 11, 40));

        List<Usage> findUsage1 = usageRepository.search(usageSearchDto1);
        List<Usage> findUsage2 = usageRepository.search(usageSearchDto2);
        List<Usage> findUsage3 = usageRepository.search(usageSearchDto3);
        List<Usage> findUsage4 = usageRepository.search(usageSearchDto4);
        List<Usage> findUsage5 = usageRepository.search(usageSearchDto5);

        assertEquals(2, findUsage1.size());
        assertEquals(1, findUsage2.size());
        assertEquals(1, findUsage3.size());
        assertEquals(2, findUsage4.size());
        assertEquals(0, findUsage5.size());

        assertEquals(usage1.getId(), findUsage2.get(0).getId());
        assertEquals(usage2.getId(), findUsage3.get(0).getId());
        assertEquals(orderItem1.getId(), findUsage2.get(0).getOrderItem().getId());
        assertEquals(orderItem2.getId(), findUsage3.get(0).getOrderItem().getId());
    }
}