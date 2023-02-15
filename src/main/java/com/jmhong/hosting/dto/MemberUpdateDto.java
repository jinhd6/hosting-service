package com.jmhong.hosting.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberUpdateDto {

    private String password;
    private String realName;
    private String phoneNumber;
    private String address;
    private String email;
}
