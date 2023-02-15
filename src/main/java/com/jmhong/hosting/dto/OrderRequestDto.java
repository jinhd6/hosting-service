package com.jmhong.hosting.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequestDto {

    private Long memberId;
    private Long itemId;
    private Long quantity;

    public OrderRequestDto() {
    }

    public OrderRequestDto(Long memberId, Long itemId, Long quantity) {
        this.memberId = memberId;
        this.itemId = itemId;
        this.quantity = quantity;
    }
}
