package com.jmhong.hosting.dto;

import com.jmhong.hosting.domain.Member;
import com.jmhong.hosting.domain.MemberType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberResponseDto {

    private Long id;
    private String username;
    private String realName;
    private String phoneNumber;
    private String address;
    private String email;
    private MemberType type;

    public MemberResponseDto(Member member) {
        id = member.getId();
        username = member.getUsername();
        realName = member.getRealName();
        phoneNumber = member.getPhoneNumber();
        address = member.getAddress();
        email = member.getEmail();
        type = member.getType();
    }
}
