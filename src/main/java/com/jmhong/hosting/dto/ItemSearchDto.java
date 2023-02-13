package com.jmhong.hosting.dto;

import com.jmhong.hosting.domain.ItemStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemSearchDto {

    private String name;
    private ItemStatus status;

    public ItemSearchDto(String name, ItemStatus status) {
        this.name = name;
        this.status = status;
    }
}
