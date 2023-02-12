package com.jmhong.hosting.dto;

import com.jmhong.hosting.domain.MemberType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberSearchDto {

    private String username;
    private String email;
    private String realName;
    private String phoneNumber;
    private String address;
    private MemberType type;

    private MemberSearchDto() {
    }

    public MemberSearchDto(String username, String email, String realName, String phoneNumber, String address, MemberType type) {
        this.username = username;
        this.email = email;
        this.realName = realName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.type = type;
    }
}
