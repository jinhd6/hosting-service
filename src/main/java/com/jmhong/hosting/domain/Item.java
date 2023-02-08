package com.jmhong.hosting.domain;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
public class Item {

    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private Long price;
    private Long period;

    @Enumerated(EnumType.STRING)
    private ItemStatus status;

    protected Item() {
    }

    public Item(Long id, String name, Long price, Long period, ItemStatus status) {
        this.name = name;
        this.price = price;
        this.period = period;
        this.status = status;
    }

    public void updateItemInfo(String name, Long price, Long period, ItemStatus status) {
        this.name = name;
        this.price = price;
        this.period = period;
        this.status = status;
    }
}
