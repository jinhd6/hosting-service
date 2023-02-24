package com.jmhong.hosting.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsageSearchDto {

    private String memberUsername;
    private String orderItemName;

    public UsageSearchDto(String memberUsername, String orderItemName) {
        this.memberUsername = memberUsername;
        this.orderItemName = orderItemName;
    }

    public UsageSearchDto() {
    }
}
