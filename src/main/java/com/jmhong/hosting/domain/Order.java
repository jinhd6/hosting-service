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

    private LocalDateTime orderDate;

    private String customerName;
    private String customerPhoneNumber;
    private String customerAddress;

    @Enumerated(EnumType.STRING)
    private OrderType type;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private Long extendPeriod;

    protected Order() {
    }

    public Order(Long id, Member member, LocalDateTime orderDate,
                 String customerName, String customerPhoneNumber, String customerAddress,
                 OrderType type, OrderStatus status, Long extendPeriod) {
        this.id = id;
        this.member = member;
        this.orderDate = orderDate;
        this.customerName = customerName;
        this.customerPhoneNumber = customerPhoneNumber;
        this.customerAddress = customerAddress;
        this.type = type;
        this.status = status;
        this.extendPeriod = extendPeriod;
    }

    public void updateStatus(OrderStatus status) {
        this.status = status;
    }
}
