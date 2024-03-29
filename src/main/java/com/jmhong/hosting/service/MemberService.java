package com.jmhong.hosting.service;

import com.jmhong.hosting.domain.Member;
import com.jmhong.hosting.domain.MemberType;
import com.jmhong.hosting.dto.MemberCreateDto;
import com.jmhong.hosting.dto.MemberResponseDto;
import com.jmhong.hosting.dto.MemberCond;
import com.jmhong.hosting.dto.MemberUpdateDto;
import com.jmhong.hosting.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class MemberService {

    private MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member join(MemberCreateDto memberCreateDto) {
        Member member = convertToMember(memberCreateDto);
        memberRepository.save(member);
        return member;
    }

    private static Member convertToMember(MemberCreateDto dto) {
        Member member = new Member(dto.getUsername(), dto.getPassword(), dto.getEmail(),
                dto.getRealName(),dto.getPhoneNumber(), dto.getAddress(), MemberType.MEMBER);
        return member;
    }

    @Transactional(readOnly = true)
    public List<MemberResponseDto> searchMember(MemberCond memberCond) {
        List<Member> members = memberRepository.search(memberCond);
        List<MemberResponseDto> memberResponses = new ArrayList<>();
        for (Member member : members) {
            MemberResponseDto memberResponse = new MemberResponseDto(member);
            memberResponses.add(memberResponse);
        }
        return memberResponses;
    }

    public Member updateMember(Long memberId, MemberUpdateDto memberUpdateDto) {
        Member member = memberRepository.findById(memberId).orElseThrow();
        member.updateMemberInfo(memberUpdateDto.getPassword(), memberUpdateDto.getEmail());
        member.updateDeliveryInfo(
                memberUpdateDto.getRealName(), memberUpdateDto.getPhoneNumber(), memberUpdateDto.getAddress());
        return member;
    }

    @Transactional(readOnly = true)
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    @Transactional(readOnly = true)
    public MemberResponseDto searchById(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow();
        return new MemberResponseDto(member);
    }
}
