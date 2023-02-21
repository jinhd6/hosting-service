package com.jmhong.hosting.repository;

import com.jmhong.hosting.domain.Member;
import com.jmhong.hosting.dto.MemberSearchDto;
import com.jmhong.hosting.domain.MemberType;
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
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private EntityManager em;

    @Test
    void search() {
        Member member1 = new Member("id1", "pw1", "a1@a.com",
                "rn1", "pn1", "adr1", MemberType.MEMBER);
        Member member2 = new Member("id2", "pw2", "a2@a.com",
                "rn2", "pn2", "adr2", MemberType.ADMIN);
        memberRepository.save(member1);
        memberRepository.save(member2);

        em.flush();
        em.clear();

        MemberSearchDto memberSearchDto1 = new MemberSearchDto("", "", "", "", "", null);
        MemberSearchDto memberSearchDto2 = new MemberSearchDto("id1", "a1@a.com", "rn1", "pn1", "adr1", MemberType.MEMBER);
        MemberSearchDto memberSearchDto3 = new MemberSearchDto("id2", "a2@a.com", "rn2", "pn2", "adr2", MemberType.ADMIN);
        MemberSearchDto memberSearchDto4 = new MemberSearchDto("d", "a", "n", "n", "d", null);
        MemberSearchDto memberSearchDto5 = new MemberSearchDto("xx", "xx", "xx", "xx", "xx", null);

        List<Member> findMembers1 = memberRepository.search(memberSearchDto1);
        List<Member> findMembers2 = memberRepository.search(memberSearchDto2);
        List<Member> findMembers3 = memberRepository.search(memberSearchDto3);
        List<Member> findMembers4 = memberRepository.search(memberSearchDto4);
        List<Member> findMembers5 = memberRepository.search(memberSearchDto5);

        assertEquals(2, findMembers1.size());
        assertEquals(1, findMembers2.size());
        assertEquals(1, findMembers3.size());
        assertEquals(2, findMembers4.size());
        assertEquals(0, findMembers5.size());

        assertEquals(member1.getId(), findMembers2.get(0).getId());
        assertEquals(member2.getId(), findMembers3.get(0).getId());
    }
}