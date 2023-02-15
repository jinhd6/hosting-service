package com.jmhong.hosting.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberCreateDto {

    private String username;
    private String password;
    private String email;
    private String realName;
    private String phoneNumber;
    private String address;

    public MemberCreateDto() {
    }

    public MemberCreateDto(String username, String password, String email, String realName, String phoneNumber, String address) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.realName = realName;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }
}
