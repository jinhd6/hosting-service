package com.jmhong.hosting.domain;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String username;
    private String password;
    private String email;

    private String realName;
    private String phoneNumber;
    private String address;

    @Enumerated(EnumType.STRING)
    private MemberType type;
}
