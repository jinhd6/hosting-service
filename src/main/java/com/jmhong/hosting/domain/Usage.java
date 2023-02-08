package com.jmhong.hosting.domain;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
public class Usage {

    @Id @GeneratedValue
    @Column(name = "usage_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_item_id")
    private OrderItem orderItem;

    private LocalDateTime connectDate;
    private LocalDateTime disconnectDate;

    protected Usage() {
    }

    public Usage(Member member, OrderItem orderItem, LocalDateTime connectDate, LocalDateTime disconnectDate) {
        this.member = member;
        this.orderItem = orderItem;
        this.connectDate = connectDate;
        this.disconnectDate = disconnectDate;
    }
}
