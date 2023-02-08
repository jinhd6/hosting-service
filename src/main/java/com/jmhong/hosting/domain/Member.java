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

    @Embedded
    private OrderInfo orderInfo;

    private String username;
    private String password;
    private String email;

    @Enumerated(EnumType.STRING)
    private MemberType type;
}
