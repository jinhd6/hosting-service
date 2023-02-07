package com.jmhong.hosting.domain;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Getter
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Embedded
    private OrderInfo orderInfo;

    private LocalDateTime orderDate;
    private Long period;

    @Enumerated(EnumType.STRING)
    private OrderType type;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private LocalDateTime createDate;
    private LocalDateTime modifyDate;
}
