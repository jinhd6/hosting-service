package com.jmhong.hosting.service;

import com.jmhong.hosting.domain.Member;
import com.jmhong.hosting.dto.MemberSearchDto;
import com.jmhong.hosting.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional
    public Long join(Member member) {
        memberRepository.save(member);
        return member.getId();
    }

    public List<Member> search(MemberSearchDto memberSearchDto) {
        return memberRepository.search(memberSearchDto);
    }
}
