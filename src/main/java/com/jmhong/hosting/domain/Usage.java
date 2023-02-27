package com.jmhong.hosting.domain;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "usages")
@Getter
public class Usage {

    @Id @GeneratedValue
    @Column(name = "usage_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_item_id")
    private OrderItem orderItem;

    private LocalDateTime connectDate;
    private LocalDateTime disconnectDate;

    protected Usage() {
    }

    public Usage(OrderItem orderItem, LocalDateTime connectDate, LocalDateTime disconnectDate) {
        this.orderItem = orderItem;
        this.connectDate = connectDate;
        this.disconnectDate = disconnectDate;
    }
}
