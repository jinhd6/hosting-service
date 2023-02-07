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
    private Long quantity;
    private Long orderPrice;
    private LocalDateTime activateDate;
    private LocalDateTime expireDate;

    private LocalDateTime createDate;
    private LocalDateTime modifyDate;
}
