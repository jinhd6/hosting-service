package com.jmhong.hosting.dto;

import com.jmhong.hosting.domain.ItemStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemRequestDto {

    private String name;
    private Long price;
    private Long period;
    private ItemStatus status;

    public ItemRequestDto() {
    }

    public ItemRequestDto(String name, Long price, Long period, ItemStatus status) {
        this.name = name;
        this.price = price;
        this.period = period;
        this.status = status;
    }
}
