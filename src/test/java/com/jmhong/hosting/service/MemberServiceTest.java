package com.jmhong.hosting.service;

import com.jmhong.hosting.domain.Member;
import com.jmhong.hosting.domain.MemberType;
import com.jmhong.hosting.dto.MemberSearchDto;
import com.jmhong.hosting.repository.MemberRepository;
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
class MemberServiceTest {

    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberRepository memberRepository;

    @Test
    void join() {
        Member member1 = new Member("id1", "pw1", "email1", MemberType.MEMBER);
        Member member2 = new Member("id2", "pw2", "email2", MemberType.MEMBER);

        Long joinMemberId1 = memberService.join(member1);
        Long joinMemberId2 = memberService.join(member2);

        assertEquals(member1.getId(), joinMemberId1);
        assertEquals(member2.getId(), joinMemberId2);
    }

    @Test
    void searchByCondition() {
        Member member1 = new Member("id1", "pw1", "email1", MemberType.MEMBER);
        Member member2 = new Member("id2", "pw2", "email2", MemberType.ADMIN);
        member1.updateDeliveryInfo("realName1", "phoneNumber1", "address1");
        member2.updateDeliveryInfo("realName2", "phoneNumber2", "address2");
        memberService.join(member1);
        memberService.join(member2);
        MemberSearchDto memberSearchDto1 = new MemberSearchDto("id1", "email1",
                "realName1", "phoneNumber1", "address1", MemberType.MEMBER);
        MemberSearchDto memberSearchDto2 = new MemberSearchDto("id2", "email2",
                "realName2", "phoneNumber2", "address2", MemberType.ADMIN);

        List<Member> findMembers1 = memberService.search(memberSearchDto1);
        List<Member> findMembers2 = memberService.search(memberSearchDto2);

        assertEquals(1, findMembers1.size());
        assertEquals(1, findMembers2.size());
        assertEquals(member1.getId(), findMembers1.get(0).getId());
        assertEquals(member2.getId(), findMembers2.get(0).getId());
    }
}