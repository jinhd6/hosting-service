package com.jmhong.hosting.service;

import com.jmhong.hosting.domain.Member;
import com.jmhong.hosting.domain.MemberType;
import com.jmhong.hosting.dto.MemberCreateDto;
import com.jmhong.hosting.dto.MemberResponseDto;
import com.jmhong.hosting.dto.MemberSearchDto;
import com.jmhong.hosting.dto.MemberUpdateDto;
import com.jmhong.hosting.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class MemberServiceTest {

    @PersistenceContext EntityManager em;
    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    void join() {
        // Given
        MemberCreateDto memberCreateDto = new MemberCreateDto("id1", "pw1", "id1@email.com",
                "이름1", "010-0000-0001", "주소1");

        // When
        Member member = memberService.join(memberCreateDto);

        // Then
        assertEquals("id1", member.getUsername());
        assertEquals("pw1", member.getPassword());
        assertEquals("id1@email.com", member.getEmail());
        assertEquals("이름1", member.getRealName());
        assertEquals("010-0000-0001", member.getPhoneNumber());
        assertEquals("주소1", member.getAddress());
    }

    @Test
    void searchMember() {
        // Given
        Member member1 = createMember("id1", "pw1", "id1@email.com",
                "이름1", "010-0000-0001", "주소1", MemberType.MEMBER);
        Member member2 = createMember("id2", "pw2", "id2@email.com",
                "이름2", "010-0000-0002", "주소2", MemberType.ADMIN);
        MemberSearchDto memberSearchDto = new MemberSearchDto("id", "email.com",
                "이름", "010", "주소", null);

        // When
        List<MemberResponseDto> memberResponses = memberService.searchMember(memberSearchDto);

        // Then
        assertEquals(2, memberResponses.size());
    }

    @Test
    void updateMember() {
        // Given
        Member member = createMember("id1", "pw1", "id1@email.com",
                "이름1", "010-0000-0001", "주소1", MemberType.MEMBER);
        MemberUpdateDto memberUpdateDto = new MemberUpdateDto();
        memberUpdateDto.setPassword("pw2");
        memberUpdateDto.setEmail("id2@email.com");
        memberUpdateDto.setRealName("이름2");
        memberUpdateDto.setPhoneNumber("010-0000-0002");
        memberUpdateDto.setAddress("주소2");

        // When
        Member updateMember = memberService.updateMember(member.getId(), memberUpdateDto);

        // Then
        assertEquals("id1", updateMember.getUsername());
        assertEquals("pw2", updateMember.getPassword());
        assertEquals("id2@email.com", updateMember.getEmail());
        assertEquals("이름2", updateMember.getRealName());
        assertEquals("010-0000-0002", updateMember.getPhoneNumber());
        assertEquals("주소2", updateMember.getAddress());
        assertEquals(MemberType.MEMBER, updateMember.getType());
    }

    private Member createMember(String username, String password, String email, String realName, String phoneNumber,
                                String address, MemberType type) {
        Member member = new Member(username, password, email, realName, phoneNumber, address, type);
        em.persist(member);
        return member;
    }
}