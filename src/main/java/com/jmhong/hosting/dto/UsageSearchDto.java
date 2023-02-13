package com.jmhong.hosting.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsageSearchDto {

    private String memberUserName;
    private String orderItemName;

    public UsageSearchDto(String memberUserName, String orderItemName) {
        this.memberUserName = memberUserName;
        this.orderItemName = orderItemName;
    }
}
