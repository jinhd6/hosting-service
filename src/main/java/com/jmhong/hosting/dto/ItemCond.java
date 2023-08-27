package com.jmhong.hosting.dto;

import com.jmhong.hosting.domain.ItemStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemCond {

    private String name;
    private ItemStatus status;
    private String minPrice;
    private String maxPrice;
    private String minPeriod;
    private String maxPeriod;

    public ItemCond(String name, ItemStatus status, String minPrice, String maxPrice, String minPeriod, String maxPeriod) {
        this.name = name;
        this.status = status;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.minPeriod = minPeriod;
        this.maxPeriod = maxPeriod;
    }

    public ItemCond() {
    }
}
