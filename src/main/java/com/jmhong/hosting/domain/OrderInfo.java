package com.jmhong.hosting.domain;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public class OrderInfo {

    private String realName;
    private String phoneNumber;
    private String address;

    protected OrderInfo() {
    }

    public OrderInfo(String realName, String phoneNumber, String address) {
        this.realName = realName;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }
}
