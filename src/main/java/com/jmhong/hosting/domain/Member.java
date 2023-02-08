package com.jmhong.hosting.domain;

import lombok.Getter;

import javax.persistence.*;

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

    protected Member() {
    }

    public Member(String username, String password, String email, MemberType type) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.type = type;
    }

    public void updateMemberInfo(String password, String email) {
        this.password = password;
        this.email = email;
    }

    public void updateDeliveryInfo(String realName, String phoneNumber, String address) {
        this.realName = realName;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }
}
