package com.jmhong.hosting.service;

import com.jmhong.hosting.domain.Member;
import com.jmhong.hosting.domain.MemberType;
import com.jmhong.hosting.domain.OrderItem;
import com.jmhong.hosting.domain.Usage;
import com.jmhong.hosting.dto.UsageSearchDto;
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
class UsageServiceTest {

    @Autowired
    private UsageService usageService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private OrderItemService orderItemService;

    @Test
    void saveUsage() {
        Usage usage1 = new Usage(null, null, null, null);
        Usage usage2 = new Usage(null, null, null, null);

        Long saveUsageId1 = usageService.saveUsage(usage1);
        Long saveUsageId2 = usageService.saveUsage(usage2);

        assertEquals(usage1.getId(), saveUsageId1);
        assertEquals(usage2.getId(), saveUsageId2);
    }

    @Test
    void search() {
        Member member1 = new Member("id1", "pw1", "email1", MemberType.MEMBER);
        Member member2 = new Member("id2", "pw2", "email2", MemberType.ADMIN);
        memberService.join(member1);
        memberService.join(member2);
        OrderItem orderItem1 = new OrderItem(null, null,
                "orderItem1", null, null, 111111L, 1111L);
        OrderItem orderItem2 = new OrderItem(null, null,
                "orderItem2", null, null, 222222L, 2222L);
        orderItemService.saveOrderItem(orderItem1);
        orderItemService.saveOrderItem(orderItem2);
        Usage usage1 = new Usage(member1, orderItem1, null, null);
        Usage usage2 = new Usage(member2, orderItem2, null, null);
        usageService.saveUsage(usage1);
        usageService.saveUsage(usage2);
        UsageSearchDto usageSearchDto1 = new UsageSearchDto("id1", "orderItem1");
        UsageSearchDto usageSearchDto2 = new UsageSearchDto("id2", "orderItem2");

        List<Usage> findUsages1 = usageService.search(usageSearchDto1);
        List<Usage> findUsages2 = usageService.search(usageSearchDto2);

        assertEquals(1, findUsages1.size());
        assertEquals(1, findUsages2.size());
        assertEquals(usage1.getId(), findUsages1.get(0).getId());
        assertEquals(usage2.getId(), findUsages2.get(0).getId());
    }
}