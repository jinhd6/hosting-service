package com.jmhong.hosting.repository;

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

import javax.persistence.EntityManager;

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
    private EntityManager em;

    @Test
    void search() {
        Member member1 = new Member("id1", "pw1", "a1@a.com", MemberType.MEMBER);
        Member member2 = new Member("id2", "pw2", "a2@a.com", MemberType.ADMIN);
        member1.updateDeliveryInfo("rn1", "pn1", "adr1");
        member2.updateDeliveryInfo("rn2", "pn2", "adr2");
        memberRepository.save(member1);
        memberRepository.save(member2);

        OrderItem orderItem1 = new OrderItem(null, null, "oi1", null, null, 111111L, 1111L);
        OrderItem orderItem2 = new OrderItem(null, null, "oi2", null, null, 222222L, 2222L);
        orderItemRepository.save(orderItem1);
        orderItemRepository.save(orderItem2);

        Usage usage1 = new Usage(member1, orderItem1, null, null);
        Usage usage2 = new Usage(member2, orderItem2, null, null);
        usageRepository.save(usage1);
        usageRepository.save(usage2);

        em.flush();
        em.clear();

        UsageSearchDto usageSearchDto1 = new UsageSearchDto("", "");
        UsageSearchDto usageSearchDto2 = new UsageSearchDto("id1", "oi1");
        UsageSearchDto usageSearchDto3 = new UsageSearchDto("id2", "oi2");
        UsageSearchDto usageSearchDto4 = new UsageSearchDto("d", "i");
        UsageSearchDto usageSearchDto5 = new UsageSearchDto("xx", "xx");

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
        assertEquals(member1.getId(), findUsage2.get(0).getMember().getId());
        assertEquals(member2.getId(), findUsage3.get(0).getMember().getId());
        assertEquals(orderItem1.getId(), findUsage2.get(0).getOrderItem().getId());
        assertEquals(orderItem2.getId(), findUsage3.get(0).getOrderItem().getId());
    }
}