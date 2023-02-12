package com.jmhong.hosting.repository;

import com.jmhong.hosting.domain.Member;
import com.jmhong.hosting.dto.MemberSearchDto;

import java.util.List;

public interface MemberRepositoryCustom {

    List<Member> search(MemberSearchDto memberSearchDto);
}
