package com.jmhong.hosting.domain;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
public class OrderItem {

    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private String name;
    private LocalDateTime activateDate;
    private LocalDateTime expireDate;

    private Long orderPrice;
    private Long orderPeriod;

    protected OrderItem() {
    }

    public OrderItem(Long id, Order order, Item item,
                     String name, LocalDateTime activateDate, LocalDateTime expireDate,
                     Long orderPrice, Long orderPeriod) {
        this.id = id;
        this.order = order;
        this.item = item;
        this.name = name;
        this.activateDate = activateDate;
        this.expireDate = expireDate;
        this.orderPrice = orderPrice;
        this.orderPeriod = orderPeriod;
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void updateExpireDate(LocalDateTime expireDate) {
        this.expireDate = expireDate;
    }
}
